package hm.net.thread.java.util.concurrent;

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
    public static HExecutorService newFixedThreadPool(int nThreads) {

    }

    public

    /**
     * 将一个Runnable转换为一个Callable
     *
     * @param task   Runnable任务
     * @param result 返回值
     * @param <T>    值类型
     * @return 被转换为Callable的Runnable
     */
    public static <T> HCallable<T> callable(HRunnable task, T result) {
        if (null == task)
            throw new NullPointerException();
        return new RunnableAdapter<T>(task, result);
    }

    /**
     * Runnable的适配器，将一个Runnable转换为一个Callable。
     *
     * @param <T> 被适配为Callable的Runnable
     */
    static final class RunnableAdapter<T> implements HCallable<T> {
        final HRunnable runnable;
        final T result;

        public RunnableAdapter(HRunnable runnable, T result) {
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
