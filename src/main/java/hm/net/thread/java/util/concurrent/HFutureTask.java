package hm.net.thread.java.util.concurrent;

/**
 * 一个可取消的异步计算。
 *
 * @author Yan Jiahong
 * Created on 2022/6/21
 */
public class HFutureTask<V> implements HRunnableFuture<V> {

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

}
