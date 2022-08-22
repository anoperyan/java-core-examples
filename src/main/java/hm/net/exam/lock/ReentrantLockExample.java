package hm.net.exam.lock;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Yan Jiahong
 * Created on 2022/7/9
 */
public class ReentrantLockExample implements Runnable {
    volatile int count;
    private static final Random random = new Random();
    private final ReentrantLock lock = new ReentrantLock();
    private final ExecutorService executors = Executors.newFixedThreadPool(5);


    @Override
    public void run() {
        try {
            lock.lock();
            count++;
            System.out.printf("%s, count=%s\r\n", Thread.currentThread(), count);
        } finally {
            lock.unlock();
        }
    }

    void start() throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            executors.submit(new ReentrantLockExample());
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new ReentrantLockExample().start();
    }
}
