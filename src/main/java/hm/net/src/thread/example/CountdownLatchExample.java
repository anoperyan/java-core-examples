package hm.net.src.thread.example;

import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * @author Yan Jiahong
 * Created on 2022/6/22
 */
public class CountdownLatchExample {
    private final CountDownLatch latch = new CountDownLatch(4);
    private static final Random random = new Random();

    class Worker extends Thread {
        @Override
        public void run() {
            try {
                Thread.sleep(random.nextInt(3000));
            } catch (InterruptedException e) {
                // do nothing
            }
            latch.countDown();
            System.out.printf("%s %s 结束工作！\n", System.currentTimeMillis(), Thread.currentThread().getName());
        }
    }

    public void start() throws InterruptedException {
        new Worker().start();
        new Worker().start();
        new Worker().start();
        new Worker().start();

        latch.await();
        System.out.printf("%s All work done！\n", System.currentTimeMillis());
    }

    public static void main(String[] args) throws InterruptedException {
        new CountdownLatchExample().start();
    }
}
