package hm.net.exam;

import hm.net.java.util.concurrent.HExecutorService;
import hm.net.java.util.concurrent.HExecutors;

import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author Yan Jiahong
 * Created on 2022/6/21
 */
public class HExecutorsTest {
    volatile Object[] arr = new Object[]{
            new Object(), new Object(), new Object(), null
    };

    void start() throws InterruptedException {
        Thread t = new Thread(() -> {
            Object[] arr2 = arr;
            arr2[3] = new Object();
            System.out.printf("arr2[3] =%s\r\n", arr2[3]);
        });
        t.start();
        t.join();
        System.out.printf("arr[3] =%s\r\n", arr[3]);
    }

    public static void main(String[] args) throws Exception {
        System.out.println(16 - (16 >>>2));

        //HExecutorService executor = HExecutors.newFixedThreadPool(10);
        //executor.submit(() -> {
        //    System.out.println("我来自HExecutor，哈哈哈！");
        //});
        //ThreadPoolExecutor tpe = Executors.newFixedThreadPool(5);
    }
}
















