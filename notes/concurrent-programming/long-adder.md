# LongAdder

## 谈谈对LongAdder的理解

LongAdder是一个比AtomicLong并发性能更好的多线程环境下的计数器， 其主要功能和AtomicLong一样，都是在多线程环境下进行原子计数。

LongAdder使用了CAS+Cell列表方式，在竞争较小时直接采用CAS方式增加计数， 在竞争度比较大（就是CAS一次无法成功识货），将多个线程的计数存放在一个列表里面，减少并发竞争。

在使用sum方法读取计数总和时候，将CAS累加数加上列表中技计数的累加。

## 核心代码解析

```java 
public void add(long x) {
    Cell[] as; 
    long b, v; 
    int m; 
    Cell a;
    
    /*
     如果cells不为null，说明cells已经存在（说明当前竞争强度已经较大），
     则不需要再尝试casBase了。
    */
    if ((as = cells) != null || !casBase(b = base, b + x)) {
        // 能够进入到if内部，说明竞争强度较大，需要操作cells
        
        // 是否是无竞争的
        boolean uncontended = true;
        // 如果cell为空或者cell的长度为0
        if (as == null ||  // cells为空
            (m = as.length - 1) < 0 ||  // cells长度为0
            (a = as[getProbe() & m]) == null || // cells的槽位内数据为空
            !(uncontended = a.cas(v = a.value, v + x))) // CAS修改槽位内的数据失败
            longAccumulate(x, null, uncontended);
    }
}
```

Striped64.class

```java 
final void longAccumulate(long x, LongBinaryOperator fn,
                          boolean wasUncontended) {
    int h;
    if ((h = getProbe()) == 0) {
        ThreadLocalRandom.current(); // force initialization
        h = getProbe();
        wasUncontended = true;
    }
    boolean collide = false;                // True if last slot nonempty
    for (;;) {
        Cell[] as; Cell a; int n; long v;
        if ((as = cells) != null && (n = as.length) > 0) {
            if ((a = as[(n - 1) & h]) == null) {
                if (cellsBusy == 0) {       // Try to attach new Cell
                    Cell r = new Cell(x);   // Optimistically create
                    if (cellsBusy == 0 && casCellsBusy()) {
                        boolean created = false;
                        try {               // Recheck under lock
                            Cell[] rs; int m, j;
                            if ((rs = cells) != null &&
                                (m = rs.length) > 0 &&
                                rs[j = (m - 1) & h] == null) {
                                rs[j] = r;
                                created = true;
                            }
                        } finally {
                            cellsBusy = 0;
                        }
                        if (created)
                            break;
                        continue;           // Slot is now non-empty
                    }
                }
                collide = false;
            }
            else if (!wasUncontended)       // CAS already known to fail
                wasUncontended = true;      // Continue after rehash
            else if (a.cas(v = a.value, ((fn == null) ? v + x :
                                         fn.applyAsLong(v, x))))
                break;
            else if (n >= NCPU || cells != as)
                collide = false;            // At max size or stale
            else if (!collide)
                collide = true;
            else if (cellsBusy == 0 && casCellsBusy()) {
                try {
                    if (cells == as) {      // Expand table unless stale
                        Cell[] rs = new Cell[n << 1];
                        for (int i = 0; i < n; ++i)
                            rs[i] = as[i];
                        cells = rs;
                    }
                } finally {
                    cellsBusy = 0;
                }
                collide = false;
                continue;                   // Retry with expanded table
            }
            h = advanceProbe(h);
        }
        else if (cellsBusy == 0 && cells == as && casCellsBusy()) {
            boolean init = false;
            try {                           // Initialize table
                if (cells == as) {
                    Cell[] rs = new Cell[2];
                    rs[h & 1] = new Cell(x);
                    cells = rs;
                    init = true;
                }
            } finally {
                cellsBusy = 0;
            }
            if (init)
                break;
        }
        else if (casBase(v = base, ((fn == null) ? v + x :
                                    fn.applyAsLong(v, x))))
            break;                          // Fall back on using base
    }
}
```