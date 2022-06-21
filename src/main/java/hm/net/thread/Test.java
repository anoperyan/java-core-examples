package hm.net.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Yan Jiahong
 * Created on 2022/6/21
 */
public class Test {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(10);
        executor.submit(() -> {
            System.out.println("hello world");
            System.out.println("hello world");
        });
    }
}
