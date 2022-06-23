package hm.net.exam;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author Yan Jiahong
 * Created on 2022/6/22
 */
public class ReadWriteLockExample {
    private int count = 0;

    private final ReentrantReadWriteLock rwLock = new ReentrantReadWriteLock();
    private final Lock rLock = rwLock.readLock();
    private final Lock wLock = rwLock.writeLock();

    public void write(int amount) {
        wLock.lock();
        try {
            System.out.println("Write: amount=" + amount);
            count += amount;
        } finally {
            wLock.unlock();
        }
    }

    public void read() {
        rLock.lock();
        try {
            System.out.println("  Read:  count=" + count);
        } finally {
            rLock.unlock();
        }
    }

    private final ExecutorService executor = Executors.newCachedThreadPool();
    private final Random random = new Random();

    public void start() {
        for (int i = 0; i < 100; i++) {
            int action = random.nextInt(10) % 2;
            if (action == 1) {
                // 写操作
                int amount = random.nextInt(3);
                executor.submit(() -> write(amount));
            } else {
                executor.submit(this::read);
            }
        }
        executor.shutdown();
    }

    public static void main(String[] args) throws InterruptedException {
        new ReadWriteLockExample().start();
        Thread.currentThread().join();
    }
}
