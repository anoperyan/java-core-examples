package hm.net.java.util.concurrent;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 参考{@link java.util.concurrent.Semaphore}
 * <p>
 * 信号量封装限制了对池的访问的同步，与维护迟本身一致性所需的任何同步分开。
 *
 * @author Yan Jiahong
 * Created on 2022/6/22
 */
public class HSemaphore {

    private final Sync sync;

    /**
     * 信号量的同步的实现，使用AQS的state字段表示许可证数量，子类分为公平和不公平版本。
     */
    abstract static class Sync extends AbstractQueuedSynchronizer {
        /**
         * @param permits 当前信号量的许可证总数。
         */
        Sync(int permits) {
            setState(permits);
        }

        final int getPermits() {
            return getState();
        }
    }

    static class NonFairSync extends Sync {
        /**
         * @param permits 当前信号量的许可证总数。
         */
        NonFairSync(int permits) {
            super(permits);
        }
    }

    public HSemaphore(int permits) {
        this.sync = new NonFairSync(permits);
    }

    /**
     * 从信号量获取一个许可，阻塞直到有一个可用的，或者这个线程被中断。
     *
     * @throws InterruptedException 当前线程被中断
     */
    public void acquire() throws InterruptedException {
        sync.acquireSharedInterruptibly(1);
    }

    /**
     * 释放一个许可，让其返回到信号量中
     */
    public void release() {
        sync.releaseShared(1);
    }
}
