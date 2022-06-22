package hm.net.src.thread.example.hsemaphore;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 参考{@link java.util.concurrent.Semaphore}
 * <p>
 * 信号量封装限制了对池的访问的同步，与维护迟本身一致性所需的任何同步分开。
 *
 * @author Yan Jiahong
 * Created on 2022/6/22
 */
public class MySemaphore {

    private final Sync sync;

    /**
     * 信号量的同步的实现，使用AQS的state字段表示许可证数量，子类分为公平和不公平版本。
     */
    static class Sync extends AbstractQueuedSynchronizer {
        /**
         * @param permits 当前信号量的许可证总数。
         */
        Sync(int permits) {
            setState(permits);
        }

        @Override
        protected boolean tryAcquire(int permits) {

        }

        @Override
        protected boolean tryRelease(int permits) {
            setState();
        }
    }

    public MySemaphore(int permits) {
        this.sync = new Sync(permits);
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
