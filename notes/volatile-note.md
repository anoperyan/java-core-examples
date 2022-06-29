# Volatile笔记

## 谈谈对volatile的理解

Java规范保证了所有声明为`volatile`的变量的读和写的原子性， 但是volatile关键字并不保证对变量运算的原子性。

## 问题

### 对于使用volatile引用额对象，其字段是否也是多线程可见的？

## 参考

1. [
   Java - Volatile reference object and its member fields visibility](https://www.logicbig.com/tutorials/core-java-tutorial/java-multi-threading/volatile-ref-object.html)