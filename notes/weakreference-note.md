# Java中的四种引用

## 四种引用

### 强引用（String Reference）：

当一个对象有强引用，则垃圾回收期不会回收它，当内存空间不足时， 虚拟机则宁可抛出OutOfMemory异常，也不回收对象。 如果强引用不使用时候，使用strongReference = null来使得可以被GC回收。

### 软引用（Soft Reference）：

当内存充足时候，这些对象就不会被回收，当内存不充足时，会被回收。

### 弱引用（Weak Reference）：

当一个对象只被一个弱引用引用时候，在下一次GC时候则会被回收，而不管内存时候充足。 当然，由于垃圾回收线程是一个优先级很低的线程，所以不一定很快就被回收。

### 虚引用（Phantom Reference）：

主要用来跟踪对象被垃圾回收的活动，虚引用本身不会决定其生命周期，就是形同虚设一样。

## 引用使用：

### 跟踪被回收时刻

对于软引用，弱引用，可以使用ReferenceQueue来检查被引用的对象时候被回收，当被引用的对象被回收时候， 垃圾回收器将把这个软引用（或者弱引用）加入到这个队列中。

### 四种引用比较

|引用类型|回收时间|使用场景|生存时间|
|----|----|----|----|
|强引用|从来不会|对象的一般状态|JVM停止运行时候终止|
|软引用|内存不足时|缓存等不重要但是存在比不存在更好的场景|内存不足时终止|
|弱引用|一下次垃圾回收时|具有主从依附的关系里面的从对象|一下次垃圾回收时候|
|虚引用|正常垃圾回收时候|跟踪垃圾回收|正常垃圾回收时候|









