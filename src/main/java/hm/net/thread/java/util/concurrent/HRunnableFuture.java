package hm.net.thread.java.util.concurrent;

/**
 * @author Yan Jiahong
 * Created on 2022/6/21
 */
public interface HRunnableFuture<V> extends Runnable, HFuture<V> {

    @Override
    void run();
}
