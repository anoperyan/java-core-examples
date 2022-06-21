package hm.net.thread.java.util.concurrent;

/**
 * 被拒绝执行异常。
 * 当{@link HmExecutor}无法接受任务执行时候抛出该异常。
 *
 * @author Yan Jiahong
 * Created on 2022/6/21
 */
public class HRejectedExecutionException extends RuntimeException {
    public HRejectedExecutionException(String message) {
        super(message);
    }
}
