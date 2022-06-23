package hm.net.exam.hsemaphore;

import hm.net.java.util.concurrent.HSemaphore;

import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 参考 {@link HSemaphore}
 * <p>
 * 打印机数量使有限，如果有多个人同时想要打印东西，可能需要等待。
 *
 * @author Yan Jiahong
 * Created on 2022/6/22
 */
public class SemaphoreExample {

    static class PrintTask implements Runnable {
        private static final Random random = new Random();
        private final long id;
        private final String text;
        private final Semaphore semaphore;

        PrintTask(long id, String text, Semaphore semaphore) {
            this.id = id;
            this.text = text;
            this.semaphore = semaphore;
        }


        @Override
        public void run() {
            try {
                semaphore.acquire();
                System.out.printf("打印机【%s】开始处理打印任务【%s】\n",
                        Thread.currentThread().getName(), id);
                try {
                    Thread.sleep(random.nextInt(3000));
                    System.out.printf("  打印内容：%s\n", text);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.printf("  打印机【%s】完成处理打印任务【%s】\n",
                        Thread.currentThread().getName(), id);
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.printf("打印机%s被中断！任务%s被迫中断打印！",
                        Thread.currentThread().getName(), id);
            } finally {
                semaphore.release();
            }

        }
    }


    static class PrinterPool {
        /**
         * 任务ID， 不断递增的
         */
        private final AtomicLong nextTaskId = new AtomicLong(0);

        private final ExecutorService executor;

        /**
         * 用于控制同步的同步器
         */
        private final Semaphore semaphore;

        public PrinterPool(int printNumber) {
            this.semaphore = new Semaphore(printNumber);
            this.executor = Executors.newFixedThreadPool(printNumber);
        }

        /**
         * 打印资料，如果无法立即打印，则等待到有打印机器可用
         *
         * @param text 需要打印的内容
         * @throws InterruptedException 如果当前线程已经被中断
         */
        public void print(String text) throws InterruptedException {
            // 获取唯一ID
            long taskId = nextTaskId.getAndIncrement();
            // 生成打印任务
            PrintTask task = new PrintTask(taskId, text, semaphore);
            executor.submit(task);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        PrinterPool pool = new PrinterPool(3);
        for (int i = 0; i < 100; i++) {
            pool.print("hello_" + i);
        }
    }
}