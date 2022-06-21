package hm.net.thread.java.util.concurrent;

import java.security.AccessControlContext;
import java.security.AccessController;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author Yan Jiahong
 * Created on 2022/6/21
 */
public class HThreadPoolExecutor extends HAbstractExecutorService {
    private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));
    private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int CAPACITY = (1 << COUNT_BITS) - 1;

    // 运行状态保存在高位
    private static final int RUNNING = -1 << COUNT_BITS;
    private static final int SHUTDOWN = 0;
    private static final int STOP = 1 << COUNT_BITS;
    private static final int TIDYING = 2 << COUNT_BITS;
    private static final int TERMINATED = 3 << COUNT_BITS;

    // 打包和解包ctl

    /**
     * 从打包的ctl参数中获取运行状态
     *
     * @param c ctl打包参数
     * @return 运行状态代码
     */
    private static int runStateOf(int c) {
        return c & ~CAPACITY;
    }

    /**
     * 从打包参数中获取到工作线程数量
     *
     * @param c 被打包带额ctl参数
     * @return 当前执行器工作线程数量
     */
    private static int workerCountOf(int c) {
        return c & CAPACITY;
    }

    /**
     * 将运行状态和工作机容量（workerCount）打包为一个ctl参数
     *
     * @param rs 当前执行器状态
     * @param wc 当前的线程数量
     * @return 已经打包的ctl参数值
     */
    private static int ctlOf(int rs, int wc) {
        return rs | wc;
    }

    /*
    COUNT_BITS = 000 00000000000000000000000011101
    CAPACITY   = 000 11111111111111111111111111111

    RUNNING    = 111 00000000000000000000000000000
    SHUTDOWN   = 000 00000000000000000000000000000
    STOP       = 001 00000000000000000000000000000
    TIDYING    = 010 00000000000000000000000000000
    TERMINATED = 011 00000000000000000000000000000
     */
    public static void printState() {
        System.out.printf("COUNT_BITS = %32s\r\n", zeroPaddingBinaryString(COUNT_BITS));
        System.out.printf("CAPACITY   = %32s\r\n\r\n", zeroPaddingBinaryString(CAPACITY));
        System.out.printf("RUNNING    = %32s\r\n", zeroPaddingBinaryString(RUNNING));
        System.out.printf("SHUTDOWN   = %32s\r\n", zeroPaddingBinaryString(SHUTDOWN));
        System.out.printf("STOP       = %32s\r\n", zeroPaddingBinaryString(STOP));
        System.out.printf("TIDYING    = %32s\r\n", zeroPaddingBinaryString(TIDYING));
        System.out.printf("TERMINATED = %32s\r\n", zeroPaddingBinaryString(TERMINATED));
    }

    private static String zeroPaddingBinaryString(int val) {
        String binText = Integer.toBinaryString(val);
        int paddingCount = 32 - binText.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < paddingCount; i++) {
            sb.append("0");
        }
        String text = sb + binText;
        return text.substring(0, 3) + " " + text.substring(3);
    }

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
     * 尝试进行CAS增加一个workCount数量
     *
     * @param except 期望的数量
     * @return 自增成功返回true， 否则返回false
     */
    public boolean compareAndIncrementWorkCount(int except) {
        return ctl.compareAndSet(except, except + 1);
    }

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
        this(corePoolSize, maximumPoolSize, keepAliveTime, timeUnit, workQueue,
                HExecutors.defaultThreadFactory(), defaultRejectedHandler);
    }

    public HThreadPoolExecutor(int corePoolSize,
                               int maximumPoolSize,
                               long keepAliveTime, TimeUnit timeUnit,
                               BlockingQueue<Runnable> workQueue,
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
        this.corePoolSize = corePoolSize;
        this.maximumPoolSize = maximumPoolSize;
        this.workQueue = workQueue;
        this.threadFactory = threadFactory;
        this.handler = handler;
    }

    @Override
    public void shutdown() {

    }

    @Override
    public void shutdownNow() {

    }

    /**
     * 在未来的某个时刻执行给定的任务。这个任务可能在某个新的线程中执行或者在一个已经存在的池化的线程中执行。
     * 如果这个任务无法提交执行，因为执行器已经被关闭或者任务已经达到的执行器的最大容量，任务将会被
     * {@link HRejectedExecutionHandler}处理
     *
     * @param command runnable任务
     */
    @Override
    public void execute(Runnable command) {
        if (null == command)
            throw new NullPointerException();
        /*
         * 分为三步处理：
         * 如果当前运行的线程数小于核心线程数，则重新启动一个新线程，
         * 同时把这个任务当做是hi新线程的第一个任务、
         *
         * */
        int c = ctl.get();
        if (workerCountOf(c) < corePoolSize) {

        }
    }

    /**
     * @param firstTask
     * @param core
     * @return
     */
    private boolean addWorker(Runnable firstTask, boolean core) {
        retry:
        for (; ; ) {
            int c = ctl.get();
            int rs = runStateOf(c);
            if (rs >= SHUTDOWN &&
                    !(rs == SHUTDOWN
                            && firstTask == null &&
                            !workQueue.isEmpty()))
                return false;
            for (; ; ) {
                int wc = workerCountOf(c);
                if (wc >= CAPACITY ||
                        wc >= (core ? corePoolSize : maximumPoolSize))
                    return false;
                if (compareAndIncrementWorkCount(c))
                    break retry;
                c = ctl.get();
                if (runStateOf(c) != rs)
                    continue retry;
            }
        }
        boolean workerStarted = false;
        boolean workerAdded = false;
        return false;
    }

    private final class Worker extends AbstractQueuedSynchronizer implements Runnable {
        /**
         * 当前worker工作的线程，如果线程工厂失败，则为null
         */
        final Thread thread;

        /**
         * 需要运行的初始化任务，可能为空
         */
        Runnable firstTask;

        /**
         * 每个线程的任务计数器
         */
        volatile long completedTasks;

        Worker(Runnable firstTask) {
            setState(-1);
            this.firstTask = firstTask;
            this.thread = getThreadFactory().newThread(this);
        }

        @Override
        public void run() {

        }

        public void lock() {
            acquire(1);
        }
    }

    /**
     * @param w
     */
    final void runWorker(Worker w) {
        // 工作线程
        Thread wt = Thread.currentThread();
        Runnable task = w.firstTask;
        w.firstTask = null;
        //w.unlock();
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

    /**
     * 返回用于创建对象的线程工厂
     *
     * @return 线程工厂
     */
    public HThreadFactory getThreadFactory() {
        return threadFactory;
    }
}
