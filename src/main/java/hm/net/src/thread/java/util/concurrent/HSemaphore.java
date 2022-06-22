package hm.net.src.thread.java.util.concurrent;

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
}
