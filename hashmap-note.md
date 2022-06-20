# HashMap笔记

## key的hashCode计算公式

计算key的hashCode（同时高位参与散列）

```java 
// 获取系统hashCode
int h = key.hashCode;
// 与高位进行异或预算，获取hashCode
int hashCode = h ^ ( h >>> 16);
```

## hashtable槽位计算公式

```java 
// hashCode: 通过键的hashCode计算公式算得的hashCode。
// tab.length(): 当前hashtable的总长度。

int idx = key.hashCode & (tab.length() - 1); 
```

## hashtable扩容后条目槽位计算

当hashtable扩容时候，需要重新对每个条目重新分配哈希表槽位，此时，

1. 条目的槽位就在原来位置上。
2. 要么就在原来位置+旧hashtable的容量的位置上（因为新容量总是就容量的2倍）。 情况统计：

|条件|位置|说明|
|----|----|----|
|hashCode & oldCap == 0|不变|说明当前hashCode小于oldCap|
|hashCode & oldCap != 0|当前位置+oldCap|说明当前hashCode小于oldCap, 列表是成倍扩容的|
 