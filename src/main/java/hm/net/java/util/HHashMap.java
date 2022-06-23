package hm.net.java.util;

import java.io.Serializable;
import java.util.*;

/**
 * 参考 {@link HashMap}
 *
 * @author Yan Jiahong
 * Created on 2022/6/20
 */
public class HHashMap<K, V> extends AbstractMap<K, V>
        implements Map<K, V>, Cloneable, Serializable {
    private static final long serialVersionUID = 362498820763181265L;

    /**
     * 默认初始化长度，必须是2的指数倍。
     **/
    static final int DEFAULT_INITIAL_CAPACITY = 1 << 4;

    /**
     * HashMap的哈希表的默认装载因子
     */
    static final float DEFAULT_LOAD_FACTOR = 0.75f;

    /**
     * 最大容量，如果一个构造函数隐式制定了一个更高的值，则使用该值。
     * 必须是一个2的指数倍，且小于1 << 30。
     */
    static final int MAXIMUM_CAPACITY = 1 << 30;

    static class Node<K, V> implements Map.Entry<K, V> {
        final int hash;
        final K key;
        V value;
        Node<K, V> next;

        public Node(int hash, K key, V value, Node<K, V> next) {
            this.hash = hash;
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public V setValue(V newVal) {
            V oldVal = value;
            value = newVal;
            return oldVal;
        }

        @Override
        public int hashCode() {
            return Objects.hash(key) ^ Objects.hashCode(value);
        }
    }

    /**
     * HashMap中的哈希表（HashTable）的装载因子。
     */
    private float loadFactor;

    /**
     * 不需要被序列化的属性
     */
    transient Node<K, V>[] table;

    /**
     * 下一个调整哈希表大小的阈值，threshold = capacity * loadFactors
     */
    int threshold;

    transient int modCount;

    transient int size;

    public HHashMap() {
        this.loadFactor = DEFAULT_LOAD_FACTOR;
    }

    @Override
    public String toString() {
        return "";
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return false;
    }

    public boolean containsKey(Object key) {
        return false;
    }

    public boolean containsValue(Object value) {
        return false;
    }

    public V put(K key, V value) {
        return putVal(hash(key), key, value, false, true);
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return new HashSet<>();
    }

    /**
     * @param hash
     * @param key
     * @param value
     * @param onlyIfAbsent
     * @param evict
     * @return
     */
    public V putVal(int hash, K key, V value, boolean onlyIfAbsent, boolean evict) {
        // 当前的hashtable
        Node<K, V>[] tab;
        // 上一个节点
        Node<K, V> p;
        // n是当前hashtable的长度, i是当前key在hashtable中的槽位
        int n, i;
        if ((tab = table) == null || (n = tab.length) == 0)
            n = (tab = resize()).length;
        if ((p = tab[i = (n - 1) & hash]) == null) {
            // 当前槽位还没有任何数据占用！
            tab[i] = newNode(hash, key, value, null);
        } else {
            // 当前位置已经有至少一个条目占用，需要使用链表堆叠
            Node<K, V> e;
            K k;
            if (p.hash == hash &&
                    ((k = p.key) == key || (key != null && key.equals(k))))
                // 两者hashCode相同，并且key相等（int等值时）或者key值一样（字符串时），则直接将旧值替换为新值
                e = p;
            else {
                for (int binCount = 0; ; ++binCount) {
                    if ((e = p.next) == null) {
                        p.next = newNode(hash, key, value, null);
                        break;
                    }
                    if (e.hash == hash &&
                            ((k = e.key) == key || (key != null && key.equals(k))))
                        break;
                    p = e;
                }
            }
            if (e != null) {
                V oldValue = e.value;
                if (!onlyIfAbsent || oldValue == null)
                    e.value = value;
                return oldValue;
            }
        }
        ++modCount;
        if (++size > threshold) {
            resize();
        }
        return null;
    }

    Node<K, V> newNode(int hash, K key, V val, Node<K, V> next) {
        return new Node<>(hash, key, val, next);
    }

    final Node<K, V>[] resize() {
        Node<K, V>[] oldTab = table;
        // ---------------------- 开始计算新容量，新调整大小的阈值 ----------------------
        // 获取老的哈希表的长度
        int oldCap = (null == oldTab) ? 0 : table.length;
        // 记录老的调整长度的阈值
        int oldThr = threshold;
        // 新的长度和新的阈值
        int newCap = 0, newThr = 0;
        if (oldCap > 0) {
            // 如果大于0
            if (oldCap > MAXIMUM_CAPACITY) {
                // 如果容量已经超出了最大容量，则直接将下一个调整阈值设置为MAX_INTEGER
                threshold = Integer.MAX_VALUE;
                return oldTab;

                // 调整容量为当前的两倍
            } else if ((newCap = oldCap << 1) < MAXIMUM_CAPACITY &&
                    oldCap >= DEFAULT_INITIAL_CAPACITY) {
                // 容量调整之后，新容量比最大容量要小，且旧的容量比初始化容量要大！
                newThr = oldThr << 1;
            }
        } else if (oldThr > 0) {
            // 容量不大于0，但是老的调整阈值大于0
            newCap = oldThr;
        } else {
            // oldCap == 0 && oldThr == 0;
            // 老的容量为0且老的调整长度阈值为0
            newCap = DEFAULT_INITIAL_CAPACITY;
            newThr = (int) (DEFAULT_INITIAL_CAPACITY * DEFAULT_LOAD_FACTOR);
        }
        if (newThr == 0) {
            float ft = (float) newCap * loadFactor;
            newThr = newCap < MAXIMUM_CAPACITY && ft < MAXIMUM_CAPACITY ?
                    (int) ft : Integer.MAX_VALUE;
        }
        threshold = oldThr;
        // ---------------------- 结束计算新容量，新调整大小的阈值 ----------------------

        // 开始扩容
        @SuppressWarnings({"unchecked"})
        Node<K, V>[] newTab = (Node<K, V>[]) new Node[newCap];
        // 直接将HashMap的table的值赋为新建的哈希表
        table = newTab;
        // 开始转移数据
        if (oldTab != null) {
            for (int i = 0; i < oldCap; i++) {
                Node<K, V> e;
                if ((e = oldTab[i]) != null) {
                    oldTab[i] = null;
                    // 如果当前数组位置的链表节点仅为一个。
                    // HashMap的内部数据结构为：数组+纵向链表/红黑树
                    if (e.next == null) {
                        // 此时该节点只有一个元素，直接在数组内部找到hash位置并赋值！
                        newTab[e.hash & (newCap - 1)] = e;
                    } else {
                        // 当前节点的额数据超过了一个，使用的链表来维护
                        // 因为当前的数组容量发生了变化，所以之前在同一个节点内的entry，现在可能不在同一个hash位置，
                        // 所以需要对这些entry重新计算hash点位！

                        /*
                         * 如果entry在新的数组中的下标没有变化，则称该链表为低位链表。
                         * 否则，称之为高位链表。
                         */
                        Node<K, V> loHead = null, loTail = null;
                        Node<K, V> hiHead = null, hiTail = null;
                        Node<K, V> next;
                        do {
                            // 开始逐个安排节点的新的位置
                            // 获取到下一个节点
                            next = e.next;
                            if ((e.hash & oldCap) == 0) {
                                // 这个元素还在老位置，比如原来在数组中下标为6，现在仍然为6.
                                if (loTail == null)
                                    // 如果低位链表为空，则将e赋值给低位链表的头部
                                    loHead = e;
                                else
                                    // 如果低位链表不为空，则将e复制给低位链表的下一个
                                    loTail.next = e;
                                // 重新获取链表的尾部节点
                                loTail = e;
                            } else {
                                // 在新数组中，这个entry的数组下标与在旧数组中的下标不相等。
                                if (hiTail == null)
                                    hiHead = e;
                                else
                                    hiTail.next = e;
                                hiTail = e;
                            }
                        } while ((e = next) != null);
                        if (loTail != null) {
                            loTail.next = null;
                            newTab[i] = loHead;
                        }
                        if (hiTail != null) {
                            hiTail.next = null;
                            newTab[i + oldCap] = hiHead;
                        }
                    }
                }
            }
        }
        return newTab;
    }

    public V get(Object key) {
        Node<K, V> e;
        return (e = getNode(hash(key), key)) == null ? null : e.value;
        //return null;
    }

    final Node<K, V> getNode(int hash, Object key) {
        Node<K, V>[] tab;
        Node<K, V> first, e;
        int n;
        K k;

        if ((tab = table) != null
                && (n = tab.length) > 0
                && (first = tab[(n - 1) & hash]) != null) {
            //if (first.hash == hash &&
            //        ((k = first.key) == key || k.equals(key)))
        }
        return null;
    }


    static final int hash(Object key) {
        int h;
        /*
         * 散列表通常的散列方式是：hashCode对散列表长度取余。
         * 然而因为散列表，最长仅为2^16位长度，所以，如果使用取模的方法来获得散列位置，那么这个hashCode高于16位的数据永远不会参与到“散列活动”中。
         * 将hashCode和hashCode右移16位的数据“异或”操作，是将hashCode的高位也用于参加散列，使得散列更加均匀。
         * */
        return (key == null) ? 0 : (h = key.hashCode()) ^ (h >>> 16);
    }

    public static void main(String[] args) {
        int start = 2656826;
        for (int i = 0; i < 32; i++) {
            int idx = (start + i) & 32;
            System.out.println(idx);
        }
    }
}
