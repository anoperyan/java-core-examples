# ConcurrentHashMap

## 笔记日志

### 2022.06.28

在initTable时候也要考虑并发问题，因为initTable在put中调用。

## 谈谈对ConcurrentHashMap的理解

> 围绕基本结构，基本功能，高并发操作下的计数

1. ConcurrentHashMap的基本结构是数组+单向+链表+红黑树实现的，ConcurrentHashMap使用数组实现了Hash表， 而哈希表采用的是链式寻址方式来解决哈希冲突。
   同时，当某个哈希槽位的链表冲突比较多的时候，会导致插入或者查询性能降低，所以，JDK1.8中引入了红黑树机制，当哈希表长度大于64，且哈希槽中链表长度大于等于8时候，
   单向链表就会转化为红黑树，随着ConcurrentHashMap的动态扩容，当单向链表长度小于8的时候，红黑树就会退化为一个单向链表。
2. ConcurrentHashMap本质上还是一个HashMap，因此ConcurrentHashMap提供了与HashMap一样的基本功能。
   但是，ConcurrentHashMap提供了并发线程安全的实现，实现方法是对于每个哈希槽位中的单向链表或者红黑树的第一个节点加锁。
   另外，ConcurrentHashMap在扩容时候也实现了并发扩容，就是对原始数组的扩容进行了分片，每一个线程都负责一个分片。
3. ConcurrentHashMap有一个Size方法来获取总的元素个数，而在多线程环境中，保证原子性的前提下，去做累加的性能是非常低的， 所以，在ConcurrentHashM中，当竞争度低的时候使用CAS方式去累加

## 常见问题

### 多线程单例模式中的双检锁是什么，使用双检锁有什么好处？

双检锁是指在未开始同步代码块之前，先检查是否为空，如果为空才进入同步块， 进入同步块之后，再次检查单例是否为空，如果为空才创建对象。

第一个检查是线程安全的，这样做的好处是提升性能，如果在第一次检查时候已经创建，则不必在同步锁上等待。

第二次检查的目的是防止在当线程A在第一次检查通过和同步开始之前已经有另外线程将单例创建完成，

### 在ConcurrentHashMap#initTable方法中为什么两次检查是否为空

```java 
// 为什么要检查两次
(tab = table) == null || tab.length == 0
```

外面一次检查是在线程不安全的情况下粗略检查，有可能当线程A在通过第一次检查之后和获取同步锁之前已经有另外的线程B将表初始化了。

### ConcurrentHashMap的put过程是怎么样的？

### ConcurrentHashMap的get过程是怎么样的？

### ConcurrentHashMap是如何扩容的？

### ConcurrentHashMap是如何保证线程安全的？

主要是使用了cas+volatile+synchronized来保证线程安全。

### ConcurrentHashMap是如何实现高效并发的？

### ConcurrentHashMap的1.7和1.8有什么区别？

通过对每个哈希槽的头节点来实现小粒度的锁，使得每次加锁的时候粒度仅限于单个哈希槽位

## 参考

[ConcurrentHashMap Source code in jdk1.7](https://github.com/openjdk-mirror/jdk7u-jdk/blob/master/src/share/classes/java/util/concurrent/ConcurrentHashMap.java)