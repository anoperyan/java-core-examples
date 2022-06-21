package hm.net.thread.java.util.concurrent;

/**
 * 执行提交的Runnable任务的对象。该对象提供了一种将任务提交与每个任务如何运行机制解耦的方法，
 * 包括线程的使用，调度等细节。
 * 通常使用Executor，而不是显式创建线程。
 * Executor也不是严格的要求线程执行必须是异步的，你也可以如下的立即执行提交的任务
 *
 * <pre> {@code
 * class DirectExecutor implements Executor {
 *   public void execute(Runnable r) {
 *     r.run();
 *   }
 * }}</pre>
 *
 * @author Yan Jiahong
 * Created on 2022/6/21
 */
public interface HmExecutor {

    /**
     * 在将来的额某个时间执行给定的指令
     *
     * @param command runnable任务
     * @throws HmRejectedExecutionException 当这个任务无法被接受执行
     * @throws NullPointerException         如果执行指令为空。
     */
    void execute(HRunnable command);
}
















