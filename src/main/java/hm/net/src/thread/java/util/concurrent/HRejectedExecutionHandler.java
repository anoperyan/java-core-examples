package hm.net.src.thread.java.util.concurrent;

/**
 * {@link HThreadPoolExecutor}无法执行人的任务的处理程序
 *
 * @author Yan Jiahong
 * Created on 2022/6/21
 */
public interface HRejectedExecutionHandler {
    void reject(Runnable r, HThreadPoolExecutor executor);
}
