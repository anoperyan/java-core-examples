package hm.net.thread.java.util.concurrent;

/**
 * 按需创建线程的对象。
 *
 * @author Yan Jiahong
 * Created on 2022/6/21
 */
public interface HThreadFactory {
    Thread newThread(HRunnable r);
}
