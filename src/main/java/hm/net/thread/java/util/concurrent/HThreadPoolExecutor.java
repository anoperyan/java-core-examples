package hm.net.thread.java.util.concurrent;

import java.security.AccessControlContext;
import java.security.AccessController;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author Yan Jiahong
 * Created on 2022/6/21
 */
public class HThreadPoolExecutor extends HAbstractExecutorService {


    private final AccessControlContext acc;

    /**
     * 核心线程数是保持工作线程（worker）存活的最小线程。
     */
    private volatile int corePoolSize;

    /**
     * 最大线程池线程数，
     */
    private volatile int maximumPoolSize;

    /**
     * 默认的拒绝执行策略
     */
    private static final HRejectedExecutionHandler defaultRejectedHandler = new HAbortPolicy();

    /**
     * 这个队列用于保存任务和移交给工作线程。
     */
    private final BlockingQueue<Runnable> workQueue;

    /**
     * 以纳秒为单位的空闲线程等待工作的超时时间。
     */
    private volatile long keepAliveTime;

    /**
     * 新线程的工厂，所有的线程都是通过这个工厂创建。
     */
    private volatile HThreadFactory threadFactory;

    /**
     * 当任务饱和或者关闭时候调用该处理程序
     */
    private volatile HRejectedExecutionHandler handler;

    /**
     * 通过初始化参数和默认的线程工厂和拒绝执行处理器创建一个新的{@link HThreadPoolExecutor}
     *
     * @param corePoolSize    保持在线程池中的线程数，即便这些线程是空闲的
     * @param maximumPoolSize 在线程池中允许的最大的线程数
     * @param keepAliveTime   当线程数超过的core线程数，允许空闲线程等待新任务的最长时间
     * @param timeUnit        等待时间单位
     * @param workQueue       用于在任务执行之前保存任务的队列，这个队列仅用于保存
     *                        使用{@link #execute(Runnable)}方法提交的{@link Runnable}
     */
    public HThreadPoolExecutor(int corePoolSize,
                               int maximumPoolSize,
                               long keepAliveTime,
                               TimeUnit timeUnit,
                               BlockingQueue<Runnable> workQueue) {
        this(corePoolSize, maximumPoolSize, keepAliveTime, timeUnit,
                HExecutors.defaultThreadFactory(), defaultRejectedHandler);
    }

    public HThreadPoolExecutor(int corePoolSize,
                               int maximumPoolSize,
                               long keepAliveTime, TimeUnit timeUnit,
                               HThreadFactory threadFactory,
                               HRejectedExecutionHandler handler) {
        if (corePoolSize < 0 ||
                maximumPoolSize <= 0 ||
                maximumPoolSize < corePoolSize ||
                keepAliveTime < 0)
            throw new IllegalArgumentException();
        if (null == timeUnit || null == threadFactory || null == handler)
            throw new NullPointerException();
        this.acc = System.getSecurityManager() == null ?
                null : AccessController.getContext();


    }

    @Override
    public void shutdown() {

    }

    @Override
    public void shutdownNow() {

    }

    @Override
    public void execute(Runnable command) {
    }

    public static class HAbortPolicy implements HRejectedExecutionHandler {
        public HAbortPolicy() {
        }

        @Override
        public void reject(Runnable r, HThreadPoolExecutor executor) {
            throw new HRejectedExecutionException("Task " + r.toString()
                    + " rejected from " + executor.toString());
        }
    }
}
