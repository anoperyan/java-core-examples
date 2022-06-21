package hm.net.thread.java.util.concurrent;

/**
 * @author Yan Jiahong
 * Created on 2022/6/21
 */
public interface HCallable<V> {
    V call() throws Exception;
}
