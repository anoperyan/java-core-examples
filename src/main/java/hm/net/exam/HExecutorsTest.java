package hm.net.exam;

import hm.net.java.util.concurrent.HExecutorService;
import hm.net.java.util.concurrent.HExecutors;

import java.util.concurrent.*;

/**
 * @author Yan Jiahong
 * Created on 2022/6/21
 */
public class HExecutorsTest {
    private final BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>();
    private final ThreadPoolExecutor executor = new ThreadPoolExecutor(
            3,
            6,
            60, TimeUnit.SECONDS,
            queue);

    public void start() {
        executor.submit(() -> {
            System.out.println("hello");
        });
        System.out.println("ok");
    }

    public static void main(String[] args) {
        new HExecutorsTest().start();
    }
}
















