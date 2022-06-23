package hm.net.exam;

import hm.net.java.util.concurrent.HExecutorService;
import hm.net.java.util.concurrent.HExecutors;

/**
 * @author Yan Jiahong
 * Created on 2022/6/21
 */
public class HExecutorsTest {
    public static void main(String[] args) {
        HExecutorService executor = HExecutors.newFixedThreadPool(10);
        executor.submit(() -> {
            System.out.println("我来自HExecutor，哈哈哈！");
        });
    }
}