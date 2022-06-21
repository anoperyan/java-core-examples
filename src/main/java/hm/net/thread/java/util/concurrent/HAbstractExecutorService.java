package hm.net.thread.java.util.concurrent;

import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;

/**
 * 提供{@link HExecutorService}执行方法的默认实现。
 * 此类使用newTaskFor返回的RunnableFuture实现了submit，invokeAny,invokeAll方法。
 *
 * @author Yan Jiahong
 * Created on 2022/6/21
 */
public abstract class HAbstractExecutorService implements HExecutorService {
    protected <T> RunnableFuture<T> newTaskFor(HRunnable runnable, T value) {
        return new HFutureTask<>(runnable, value);
    }

    @Override
    public HFuture<?> submit(Runnable task) {
        if (null == task) throw new NullPointerException();
        return null;
    }
}
