package hm.net.java.util.concurrent.locks;

import sun.misc.Unsafe;

/**
 * 请参考{@link java.util.concurrent.locks.AbstractQueuedSynchronizer}
 * <p>
 * 提供了一个框架用来实现依赖于先进先出等待队列的阻塞锁和相关同步器（信号量，事件等）。
 * 此类旨在为大多数依赖单个原子整数值来表示同步状态的同步器提供有用的基础。
 *
 * @author Yan Jiahong
 * Created on 2022/6/22
 */
public abstract class HAbstractQueueSynchronizer
        extends HAbstractOwnableSynchronizer {

    private static final Unsafe unsafe = Unsafe.getUnsafe();
    private static final long tailOffset;
    private static final long headOffset;

    static {
        try {
            headOffset = unsafe.objectFieldOffset(
                    HAbstractQueueSynchronizer.class.getField("head"));
            tailOffset = unsafe.objectFieldOffset(
                    HAbstractQueueSynchronizer.class.getField("tail"));
        } catch (NoSuchFieldException e) {
            throw new Error(e);
        }
    }


    static final class Node {
        static final Node SHARED = new Node();

        volatile Thread thread;

        volatile Node prev;

        volatile Node next;

        Node nextWaiter;

        Node() {
        }

        Node(Thread thread, Node node) {
            this.thread = thread;
            this.nextWaiter = node;
        }

        final Node predecessor() throws NullPointerException {
            Node p = prev;
            if (p == null)
                throw new NullPointerException();
            else
                return p;
        }
    }

    private transient volatile Node head;
    private transient volatile Node tail;

    private final boolean compareAndSetHead(Node head) {
        return unsafe.compareAndSwapObject(this, headOffset, null, head);
    }

    private final boolean compareAndSetTail(Node expect, Node update) {
        return unsafe.compareAndSwapObject(this, tailOffset, expect, update);
    }

    /**
     * 子类必须实现的方法
     *
     * @return 如果小0，则需要等待，如果大于0，则不需要等待
     */
    public int tryAcquireShared(int arg) {
        throw new UnsupportedOperationException();
    }

    /**
     * 子类必须实现的方法
     */
    public boolean tryReleaseShared(int arg) {
        throw new UnsupportedOperationException();
    }

    public final void acquireShared(int arg) {
        if (tryAcquireShared(arg) < 0)
            doAcquireShared(arg);
    }

    private Node enq(final Node node) {
        for (; ; ) {
            Node t = tail;
            if (t == null) {
                if (compareAndSetHead(new Node()))
                    tail = head;
            } else {
                node.prev = t;
                if (compareAndSetTail(t, node)) {
                    t.next = node;
                    return t;
                }
            }
        }
    }

    private Node addWaiter(Node nd) {
        Node node = new Node(Thread.currentThread(), nd);
        Node pred = tail;
        if (pred != null) {
            node.prev = pred;
            if (compareAndSetTail(pred, node)) {
                pred.next = node;
                return node;
            }
        }
        enq(node);
        return node;
    }

    private void doAcquireShared(int arg) {
        // 添加一个共性等待节点（原子操作）
        final Node node = addWaiter(Node.SHARED);
        boolean fail = true;
        try {
            boolean interrupted = false;
            for (; ; ) {
                // 获取当前节点的上一个节点
                final Node p = node.predecessor();
                /*
                如果我（当前线程）的上一个节点是头部节点，我应该去再次尝试获取锁，
                因为当前只有我一个在等待了。
                */
                if (p == head) {
                    // 检查我现在是否可以通过，大于0：直接通过，小于0：需要等待
                    int r = tryAcquireShared(arg);
                    if (r > 0) {
                        // 当前可以直接通过，重新设置head，并且传播通过

                    }
                }
            }
        } finally {

        }
    }

    private void setHeadAndPropagate(Node node, int propagate) {
        Node h = head;
        setHead(node);

    }

    private void setHead(Node node) {
        head = node;
        node.thread = null;
        node.next = null;
    }
}












