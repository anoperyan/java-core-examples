Thread

## 谈谈对ThreadLocal理解

ThreadLocal通常用来持有希望将状态与线程相关联的私有静态字段，比如用户名，Token，事务ID等。 ThreadLocal提供了线程局部变量，
变量是通过get，set方法访问，每个线程都有他自己的，独立初始化的ThreadLocal副本。 他的基本原理是，在每个Thread中都有一个以ThreadLocal变量为key的Map，

当在代码中调用ThreadLocal.set方法时候，threadLocal会调用Thread.currentThread来获取到当前线程，然后再操作当前线程的ThreadLocalMap。
Get方法时候也是先查询出当前线程，然后再从当前线程的threadLocalMap中以threadLocal变量为key来查询值。

ThreadLocal使用不当会造成内存泄漏，比如调用threadLocal的set而不调用remove时候，若该线程长期生存的话，则会造成内存泄漏。

## 常见问题

### 什么是内存泄漏

当一个对象不再被使用，当时他占有的内存却无法被回收重新分配，这就叫做内存泄漏。

### ThreadLocal会造成内存泄漏么？

### ThreadLocal中的Map中的Entry的Key为什么要使用弱引用？

用于当ThreadLocal被置为null时候，当前entry可以被回收。 ThreadLocal中的数据以Map形式存储，其中的Key就是当前的额ThreadLocal对象，
value则是用户要设置的数据，其Map设置了如果key为空，则删除entry的机制，结合 弱引用，则可以实现在下一次get时候自动清除已经过期的数据。

### 使用ThreadLocal应该注意什么，如何防止内存泄漏？

特别是在使用线程池的线程中使用ThreadLocal时候，在不使用数据时候，应该使用remove，防止数据堆积。

