package hm.net.src.thread.java.util.concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Yan Jiahong
 * Created on 2022/6/21
 */
public class HExecutors {
    /**
     * 创建一个线程，该线程池重用在共享无界队列上运行的固定数量的线程。
     *
     * @param nThreads 池中线程的数量
     * @return 新创建的线程池
     * @throws IllegalArgumentException 如果nThreads <= 0
     */
    public static HExecutorService newFixedThreadPool(int nThreads){
        return new HThreadPoolExecutor(nThreads,
                nThreads,
                0L,
                TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
    }

    /**
     * 默认线程工厂
     */
    public static class HDefaultThreadFactory implements HThreadFactory {
        private static final AtomicInteger poolNumber = new AtomicInteger(1);
        private final ThreadGroup threadGroup;
        private final AtomicInteger threadNumber = new AtomicInteger(1);
        private final String namePrefix;

        public HDefaultThreadFactory() {
            SecurityManager sm = System.getSecurityManager();
            threadGroup = (null != sm) ? sm.getThreadGroup() : Thread.currentThread().getThreadGroup();
            namePrefix = "pool-"
                    + poolNumber.getAndIncrement()
                    + "-thread-";
        }

        @Override
        public Thread newThread(Runnable r) {
            return new Thread(threadGroup, r,
                    namePrefix + threadNumber.getAndIncrement(),
                    0);
        }
    }

    /**
     * 将一个Runnable转换为一个Callable
     *
     * @param task   Runnable任务
     * @param result 返回值
     * @param <T>    值类型
     * @return 被转换为Callable的Runnable
     */
    public static <T> Callable<T> callable(Runnable task, T result) {
        if (null == task)
            throw new NullPointerException();
        return new RunnableAdapter<T>(task, result);
    }

    /**
     * 返回一个用于创建新线程的线程工厂，这个工厂创建的线程
     *
     * @return 一个线程工厂
     */
    public static HDefaultThreadFactory defaultThreadFactory() {
        return new HDefaultThreadFactory();
    }

    /**
     * Runnable的适配器，将一个Runnable转换为一个Callable。
     *
     * @param <T> 被适配为Callable的Runnable
     */
    static final class RunnableAdapter<T> implements Callable<T> {
        final Runnable runnable;
        final T result;

        public RunnableAdapter(Runnable runnable, T result) {
            this.runnable = runnable;
            this.result = result;
        }

        @Override
        public T call() {
            runnable.run();
            return result;
        }
    }
}
