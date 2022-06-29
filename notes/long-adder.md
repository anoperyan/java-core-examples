# LongAdder

## 谈谈对LongAdder的理解

LongAdder这类原子变量的目标是在目标数据高度竞争情况下使用

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