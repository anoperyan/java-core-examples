package hm.net.src.thread.example;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 参考 {@link java.util.concurrent.Semaphore}
 *
 * @author Yan Jiahong
 * Created on 2022/6/22
 */
public class SemaphoreExample {
    static class Pool {
        private static final int MAX_AVAILABLE = 5;
        private final Semaphore available = new Semaphore(MAX_AVAILABLE);

        protected boolean[] used = new boolean[MAX_AVAILABLE];
        protected Object[] items = new Object[MAX_AVAILABLE];

        public Pool() {
            for (int i = 0; i < MAX_AVAILABLE; i++) {
                if (null == items[i]) {
                    items[i] = new Object();
                }
            }
        }

        public Object getItem() throws InterruptedException {
            available.acquire();
            return getNextAvailableItem();
        }

        public void putObject(Object o) {
            if (markAsUnused(o)) {
                available.release();
            }
        }

        protected synchronized Object getNextAvailableItem() {
            for (int i = 0; i < MAX_AVAILABLE; i++) {
                if (!used[i]) {
                    used[i] = true;
                    return items[i];
                }
            }
            return null;
        }

        protected synchronized boolean markAsUnused(Object item) {
            for (int i = 0; i < MAX_AVAILABLE; i++) {
                if (item == items[i]) {
                    if (used[i]) {
                        used[i] = false;
                        return true;
                    } else
                        return false;
                }
            }
            return false;
        }
    }

    private static final ExecutorService executor = Executors.newFixedThreadPool(10);
    private static final Random random = new Random();

    public static void main(String[] args) {
        final Pool pool = new Pool();
        for (int i = 0; i < 200; i++) {
            executor.submit(() -> {
                Object obj = null;
                try {
                    obj = pool.getItem();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    System.out.println("acquire resource failed!");
                    return;
                }

                try {
                    Thread.sleep(random.nextInt(3000));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.printf("%s use done!\n", Thread.currentThread().getName());
                pool.putObject(obj);
            });
        }
        executor.shutdown();
    }
}
