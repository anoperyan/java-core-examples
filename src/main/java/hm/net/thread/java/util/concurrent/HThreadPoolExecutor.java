package hm.net.thread.java.util.concurrent;

import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * @author Yan Jiahong
 * Created on 2022/6/21
 */
public class HThreadPoolExecutor extends HAbstractExecutorService {

    /**
     * 通过初始化参数和默认的线程工厂和拒绝执行处理器创建一个新的{@link HThreadPoolExecutor}
     *
     * @param corePoolSize    保持在线程池中的线程数，即便这些线程是空闲的
     * @param maximumPoolSize 在线程池中允许的最大的线程数
     * @param keepAliveTime   当线程数超过的core线程数，允许空闲线程等待新任务的最长时间
     * @param timeUnit        等待时间单位
     * @param workQueue       用于在任务执行之前保存任务的队列，这个队列仅用于保存
     *                        使用{@link #execute(HRunnable)}方法提交的{@link HRunnable}
     */
    public HThreadPoolExecutor(int corePoolSize,
                               int maximumPoolSize,
                               long keepAliveTime,
                               TimeUnit timeUnit,
                               BlockingQueue<HRunnable> workQueue) {

    }

    public HThreadPoolExecutor() {

    }

    @Override
    public void shutdown() {

    }

    @Override
    public void shutdownNow() {

    }

    @Override
    public void execute(HRunnable command) {

    }
}
