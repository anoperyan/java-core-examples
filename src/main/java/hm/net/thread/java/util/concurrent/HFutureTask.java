package hm.net.thread.java.util.concurrent;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * 一个可取消的异步计算。
 *
 * @author Yan Jiahong
 * Created on 2022/6/21
 */
public class HFutureTask<V> implements RunnableFuture<V> {

    private volatile int state;
    private static final int NEW = 0;

    private HCallable<V> callable;

    public HFutureTask(HRunnable runnable, V result) {
        this.callable = HExecutors.callable(runnable, result);
        this.state = NEW;
    }

    @Override
    public void run() {

    }

    @Override
    public boolean cancel(boolean mayInterruptIfRunning) {
        return false;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public V get() throws InterruptedException, ExecutionException {
        return null;
    }

    @Override
    public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
        return null;
    }
}
