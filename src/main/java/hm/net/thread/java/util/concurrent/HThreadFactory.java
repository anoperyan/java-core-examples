package hm.net.thread.java.util.concurrent;

/**
 * 按需创建线程的对象。使用线程工厂消除了对new Thread的硬调用，使得应用程序能够使用特殊的线程子类，优先级等。
 *
 * @author Yan Jiahong
 * Created on 2022/6/21
 */
public interface HThreadFactory {
    Thread newThread(Runnable r);
}
