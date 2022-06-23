package hm.net.exam.aqs;

import java.util.concurrent.locks.LockSupport;

/**
 * @author Yan Jiahong
 * Created on 2022/6/22
 */
public class BooleanLatchExample {
    private final BooleanLatch latch = new BooleanLatch();

    public void start() throws InterruptedException {
        System.out.println("start 。。。");
        new Thread(() -> {
            try {
                Thread.sleep(3000);
                System.out.println("work start...");
                LockSupport.park();
                System.out.println("work done, latch should down");
                latch.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
        latch.await();
        System.out.println("all work done!");
    }

    public static void main(String[] args) throws InterruptedException {
        new BooleanLatchExample().start();
    }
}
