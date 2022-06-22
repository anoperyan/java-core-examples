package hm.net.src.thread.java.util.concurrent.locks;

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

        @Override
        protected int tryAcquireShared(int acquires) {
            return getState() == 0 ? 1 : -1;
        }

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
