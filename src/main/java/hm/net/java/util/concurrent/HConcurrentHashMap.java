//package hm.net.java.util.concurrent;
//
//import org.checkerframework.checker.units.qual.A;
//
//import javax.swing.tree.TreeNode;
//import java.io.Serializable;
//import java.util.*;
//import java.util.concurrent.ConcurrentHashMap;
//import java.util.concurrent.ConcurrentMap;
//import java.util.function.BiConsumer;
//import java.util.function.BiFunction;
//import java.util.function.Function;
//
///**
// * 参考 {@link java.util.concurrent.ConcurrentHashMap}
// *
// * @author Yan Jiahong
// * Created on 2022/6/23
// */
//public class HConcurrentHashMap<K, V> extends AbstractMap<K, V>
//        implements ConcurrentMap<K, V>, Serializable {
//    private static final long serialVersionUID = 7249069246763182397L;
//
//    // 标识节点需要前进寻址
//    static final int MOVED = -1;
//    // 普通节点最大可用的哈希值
//    static final int HASH_BITS = 0x7fffffff;
//
//    /**
//     * 默认初始化容量，必须是2的指数倍，至少为1，最大不能超过最大值
//     */
//    static final int DEFAULT_CAPACITY = 16;
//
//    static class Node<K, V> implements Map.Entry<K, V> {
//        final int hash;
//        final K key;
//        volatile V val;
//        volatile Node<K, V> next;
//
//        public Node(int hash, K key, V val, Node<K, V> next) {
//            this.hash = hash;
//            this.key = key;
//            this.val = val;
//            this.next = next;
//        }
//
//        @Override
//        public K getKey() {
//            return key;
//        }
//
//        @Override
//        public V getValue() {
//            return val;
//        }
//
//        @Override
//        public int hashCode() {
//            return key.hashCode() ^ val.hashCode();
//        }
//
//        @Override
//        public V setValue(V value) {
//            throw new UnsupportedOperationException();
//        }
//
//        @Override
//        public String toString() {
//            return key + "=" + val;
//        }
//
//        public final boolean equals(Object o) {
//            Object k, v, u;
//            Map.Entry<?, ?> e;
//            return ((o instanceof Map.Entry) &&
//                    (k = (e = (Map.Entry<?, ?>) o).getKey()) != null &&
//                    (v = e.getValue()) != null &&
//                    (k == key || k.equals(key)) &&
//                    (v == (u = val) || v.equals(u)));
//        }
//
//        Node<K, V> find(int h, Object k) {
//            Node<K, V> e = this;
//            if (k != null) {
//                do {
//                    K ek;
//                    if (h == k.hashCode() &&
//                            (((ek = e.key) == k) || ek.equals(k))) {
//                        return e;
//                    }
//                } while ((e = e.next) != null);
//            }
//            return null;
//        }
//    }
//
//    /*---------------------字段-----------------------*/
//    transient volatile Node<K, V>[] table;
//
//    /**
//     * 表初始化和调整结构大小的控制变量。
//     * sizeCtl = -1 : 表示表需要初始化
//     * sizeCtl > 0  : 表示表需要调整大小（同时sizeCtl表示下一次调整表大小的阈值）
//     */
//    private transient volatile int sizeCtl;
//
//    /*---------------------红黑树-----------------------*/
//    static final class TreeBin<K, V> extends Node<K, V> {
//        TreeNode<K, V> root;
//        volatile TreeNode
//
//        public TreeBin(int hash, K key, V val, Node<K, V> next) {
//            super(hash, key, val, next);
//        }
//    }
//
//    /*---------------------二叉树节点-----------------------*/
//    static final class TreeNode<K, V> extends Node<K, V> {
//        TreeNode<K, V> parent;
//        TreeNode<K, V> left;
//        TreeNode<K, V> right;
//        TreeNode<K, V> pre;
//        boolean red;                   // 当前节点是否为红色节点
//
//        public TreeNode(int hash, K key, V val, Node<K, V> next, TreeNode<K, V> parent) {
//            super(hash, key, val, next);
//            this.parent = parent;
//        }
//
//        /**
//         * 查找红黑树中的某个节点
//         *
//         * @param h 节点的哈希值
//         * @param k 节点的key
//         * @return 如果没有找到则返回null
//         */
//        Node<K, V> find(int h, Object k) {
//
//        }
//
//        /**
//         * 根据给定的key返回从给定的根节点开始的TreeNode
//         *
//         * @param h  目标节点的哈希码
//         * @param k  目标节点的key
//         * @param kc 目标
//         * @return 根据给定的根节点开始查找目标节点
//         */
//        final TreeNode<K, V> findTreeNode(int h, Object k, Class<?> kc) {
//            if (k != null) {
//                // 将根节点设置为当前节点
//                TreeNode<K, V> p = this;
//                do {
//                    // 父节点的哈希值
//                    int ph;
//                    int dir;
//                    // 父节点的key
//                    K pk;
//                    TreeNode<K, V> q;
//                    // 父节点的左边节点
//                    TreeNode<K, V> pl = p.left;
//                    // 父节点的额右边节点
//                    TreeNode<K, V> pr = p.right;
//                    if ((ph = p.hash) > h)
//                        p = pl;
//                    else if (ph < h)
//                        p = pr;
//                    else if ((pk = p.key) == k || k.equals(pk))
//                        return p;
//                    else if (pl == null)
//                        p = pr;
//                    else if (pr == null)
//                        p = pl;
//                    else if ((q = pr.findTreeNode(h, k, kc)) != null)
//                        return q;
//                    else
//                        p = pl;
//                } while (p != null);
//            }
//            return null;
//        }
//    }
//
//    /*---------------------计数器支持-----------------------*/
//
//
//    // Unsafe机制
//    private static final sun.misc.Unsafe U;
//    private static final long SIZECTL;
//    private static final long ABASE;
//    private static final long ASHIFT;
//
//    static {
//        try {
//            U = sun.misc.Unsafe.getUnsafe();
//            Class<?> k = HConcurrentHashMap.class;
//            SIZECTL = U.objectFieldOffset(k.getDeclaredField("sizeCtl"));
//            Class<?> ak = Node[].class;
//            ABASE = U.arrayBaseOffset(ak);
//            int scale = U.arrayIndexScale(ak);
//            if ((scale & (scale - 1)) != 0)
//                throw new Error("data type scale not a power of two");
//            ASHIFT = 31 - Integer.numberOfLeadingZeros(scale);
//        } catch (Exception e) {
//            throw new Error(e);
//        }
//    }
//
//    @Override
//    public int size() {
//        return 0;
//    }
//
//    @Override
//    public boolean isEmpty() {
//        return false;
//    }
//
//    @Override
//    public boolean containsKey(Object key) {
//        return false;
//    }
//
//    @Override
//    public boolean containsValue(Object value) {
//        return false;
//    }
//
//    @Override
//    public V get(Object key) {
//        return null;
//    }
//
//    @Override
//    public V put(K key, V value) {
//        return null;
//    }
//
//    final V putVal(K key, V value, boolean onlyIfAbsent) {
//        if (key == null || value == null) throw new NullPointerException();
//        int hash = spread(key.hashCode());
//        // 记录当前链表或者红黑树长度
//        int binCount = 0;
//        for (Node<K, V>[] tab = table; ; ) {
//            // hash槽位的第一个节点
//            Node<K, V> f;
//            // 哈希表的长度
//            int n;
//            // 当前要插入数据的哈希槽位的下标
//            int i;
//            // 当前槽位首个节点的哈希值
//            int fh;
//            if (tab == null || (n = tab.length) == 0)
//                // 当前表为空，需要创建表
//                tab = initTable();
//            else if ((f = tabAt(tab, i = (n - 1) & hash)) == null) { // 如果当前位置为空，则去创建一个新的节点
//                // 当前位置为空，可以插入
//                if (casTabAt(tab, i, null, new Node<>(hash, key, value, null)))
//                    // 如果此次新节点创建成功，直接返回
//                    break;
//            } else if ((fh = f.hash) == MOVED) {
//                // todo 需要扩容
//            } else {
//                // 不需要扩容，需要将数据插入到其中
//                V oldVal = null;
//                synchronized (f) {
//                    if (tabAt(tab, i) == f) { // 双检查
//                        if (fh >= 0) { // 检查当前是否节点不在系统操作中
//                            binCount = 1;
//                            for (Node<K, V> e = f; ; ++binCount) {
//                                K ek;
//                                if (e.hash == hash &&
//                                        ((ek = e.key) == key || key.equals(ek))) {
//                                    // 如果hash值相等，且key值相等，获取到oldVal
//                                    oldVal = e.val;
//                                    if (!onlyIfAbsent)
//                                        // 如果不是只是在空值时候插入，则替换
//                                        e.val = value;
//                                    break;
//                                }
//                                Node<K, V> pred = e;
//                                if ((e = e.next) != null) {
//                                    pred.next = new Node<K, V>(hash, key, value, null);
//                                    break;
//                                }
//                            }
//                        }
//                    }
//                }
//            }
//        }
//        return null;
//    }
//
//    /**
//     * 因为该方法会在{@link #putVal(Object, Object, boolean)}中调用，
//     * 而putVal又可能会被并发调用，所以此处也要考虑并发问题。
//     *
//     * @return 已经初始化的节点表
//     */
//    private final Node<K, V>[] initTable() {
//        Node<K, V>[] tab;
//        // size control
//        int sc;
//        // 采用自旋方式进行并发竞争修改权
//        while ((tab = table) == null || tab.length == 0) {
//            if ((sc = sizeCtl) < 0)
//                // 说明当前是正在初始化中，放弃CPU使用权，进行自旋等待
//                Thread.yield();
//            else if (U.compareAndSwapInt(this, SIZECTL, sc, -1)) { // 将SIZECTL设置为-1，标识当前ConcurrentHashMap正在初始化
//                try {
//                    // 这里使用了双检锁，来提高性能
//                    if ((tab = table) == null || tab.length == 0) {
//                        // todo 为什么会判断sc>0,sc不是会是-1么？
//                        int n = (sc > 0) ? sc : DEFAULT_CAPACITY;
//                        Node<K, V>[] nt = (Node<K, V>[]) new Node<?, ?>[n];
//                        table = nt;
//                        sc = n - (n >>> 2);
//                    }
//                } finally {
//                    sizeCtl = sc;
//                }
//                break;
//            }
//        }
//        return tab;
//    }
//
//    public final void addCount(long x, int check) {
//
//    }
//
//    static final <K, V> Node<K, V> tabAt(Node<K, V>[] tab, int i) {
//        return (Node<K, V>) U.getObjectVolatile(tab, ((long) i << ASHIFT) + ABASE);
//    }
//
//    static final <K, V> boolean casTabAt(Node<K, V>[] tab, int i, Node<K, V> c, Node<K, V> v) {
//        return U.compareAndSwapObject(tab, ((long) 1 << ASHIFT) + ABASE, c, v);
//    }
//
//    static final int spread(int h) {
//        return (h ^ (h >> 16)) & HASH_BITS;
//    }
//
//    @Override
//    public V remove(Object key) {
//        return null;
//    }
//
//    @Override
//    public void putAll(Map<? extends K, ? extends V> m) {
//
//    }
//
//    @Override
//    public void clear() {
//
//    }
//
//    @Override
//    public Set<K> keySet() {
//        return null;
//    }
//
//    @Override
//    public Collection<V> values() {
//        return null;
//    }
//
//    @Override
//    public Set<Entry<K, V>> entrySet() {
//        return null;
//    }
//
//    @Override
//    public V putIfAbsent(K key, V value) {
//        return null;
//    }
//
//    @Override
//    public boolean remove(Object key, Object value) {
//        return false;
//    }
//
//    @Override
//    public boolean replace(K key, V oldValue, V newValue) {
//        return false;
//    }
//
//    @Override
//    public V replace(K key, V value) {
//        return null;
//    }
//
//    public static void main(String[] args) {
//        Object[] arr = new Object[]{
//                new Object(), new Object(), new Object()
//        };
//        Object[] arr2 = arr;
//    }
//}
