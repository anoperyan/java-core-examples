package hm.net.java.util.concurrent.locks;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * 参考{@link java.util.concurrent.CountDownLatch}
 * <p>
 * 一种同步辅助，允许一个或者多个线程等待，直到在其他线程中执行的一组操作完成。
 * 两种典型应用场景：
 * 1. 事件（组）B必须等待事件（组）A完成之后才能开始，比如汽车必须等到变成绿灯才能继续前进。
 * 2. 某件事情可以拆分成N分，但是必须等到所有的子事件完成之后，整个事件才能算完成。
 *
 * @author Yan Jiahong
 * Created on 2022/6/22
 */
public class HCountDownLatch {
    private final static class Sync extends AbstractQueuedSynchronizer {
        Sync(int count) {
            setState(count);
        }

        int getCount() {
            return getState();
        }

        /**
         * 返回一个大于0的数，意味着当前线程可以直接继续执行。
         * 返回一个小于0的数，意味着当前线程需要等待。
         *
         * @return 如果当前线程可以继续执行，则返回1，反之，如果当前线程需要等待，则返回-1
         */
        @Override
        protected int tryAcquireShared(int acquires) {
            // 如果当前的信号量容量为0，则
            return getState() == 0 ? 1 : -1;
        }

        /**
         * 尝试释放一个锁，通过CAS修改state数值。
         * 如果当前锁的数量已经降低到0个，则返回true，意味着需要
         * @return
         */
        @Override
        protected boolean tryReleaseShared(int releases) {
            for (; ; ) {
                int c = getState();
                if (c == 0) {
                    return false;
                }
                int nextc = c - 1;
                if (compareAndSetState(c, nextc))
                    return nextc == 0;
            }
        }
    }

    public HCountDownLatch(int count) {
        if (count < 0) throw new IllegalArgumentException("count < 0");

    }
}
