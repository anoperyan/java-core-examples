# Java semaphore笔记

## FAQ

Q: 为什么semaphore中的同步器是一个共享锁，而不是独占锁?

A: 占锁是指锁只能同时被一个线程所占有，很显然，一般情况下，Semaphore是会同时被多个线程占有。