package hm.net.java.util.concurrent;

import java.util.concurrent.Future;

/**
 * 参考{@link java.util.concurrent.ExecutorService}
 * <p>
 * 一个提供管理终止和生成一个为追踪一个或者多个异步任务过程的Future的方法的{@link HmExecutor}。
 * 一个HmExecutorService可以被关闭，这将导致他拒绝新的任务。
 * 它提供了两个不同的方法去关闭一个{@link HExecutorService}，{@link HExecutorService#shutdown()}
 * 将允许之前提交的任务在终止之前执行，然而@{@link HExecutorService#shutdownNow()}阻止等待的任务开始并且会
 * 尝试停止挡墙正在执行的任务。在终止时候，一个executor没有正在执行的任务，没有等待执行的任务，没有新任务可以被提交。
 * 应该关闭未使用的HmExecutorService以允许它资源回收。
 * <p>
 * submit方法通过创建和返回一个可以用来取消执行和等待完成的Future继承自基础方法 {@link HmExecutor#execute(Runnable)}。
 *
 * @author Yan Jiahong
 * Created on 2022/6/21
 */
public interface HExecutorService extends HmExecutor {
    void shutdown();

    void shutdownNow();

    Future<?> submit(Runnable task);
}









