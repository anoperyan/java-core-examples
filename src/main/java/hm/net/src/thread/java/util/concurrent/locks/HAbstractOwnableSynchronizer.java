package hm.net.src.thread.java.util.concurrent.locks;

/**
 * 一个同步器可能被一个线程独占。
 * 这个类为创建可能需要所有权概念的锁和相关同步器提供了基础。
 * 这个类本身不管理或者使用相关信息，然而，
 * 子类和工具可能使用相关的已经保存的值来帮助控制和监视访问并提供诊断。
 *
 * @author Yan Jiahong
 * Created on 2022/6/22
 */
public abstract class HAbstractOwnableSynchronizer {

    protected HAbstractOwnableSynchronizer() {
    }

    /**
     * 独占模式同步的当前拥有者（线程）
     */
    private transient Thread exclusiveOwnerThread;

    protected final void setExclusiveOwnerThread(Thread thread) {
        exclusiveOwnerThread = thread;
    }

    protected final Thread getExclusiveOwnerThread() {
        return exclusiveOwnerThread;
    }
}
















