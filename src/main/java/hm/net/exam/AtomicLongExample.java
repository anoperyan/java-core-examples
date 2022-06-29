package hm.net.exam;

import org.checkerframework.checker.units.qual.A;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * @author Yan Jiahong
 * Created on 2022/6/29
 */
public class AtomicLongExample {
    private final ExecutorService executor = Executors.newFixedThreadPool(30);
    private final AtomicLong counter = new AtomicLong(0);

    static final class AddTask implements Runnable {
        private final AtomicLong counter;
        private final long target;

        public AddTask(AtomicLong counter, long target) {
            this.counter = counter;
            this.target = target;
        }

        @Override
        public void run() {
            for (long count = counter.get(); count < target; count = counter.get()) {
                boolean success = counter.compareAndSet(count, count + 1);
                //if (success) {
                    //System.out.printf("%s : %s\r\n", Thread.currentThread(), counter.get());
                //}
            }
        }

    }

    public void start() throws InterruptedException {
        final long target = 1000 * 10000;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 50; i++) {
            executor.submit(new AddTask(counter, target));
        }
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.HOURS);
        long end = System.currentTimeMillis();
        System.out.printf("duration: %sms\r\n", (end - start));
    }

    public static void main(String[] args) throws Exception {
        AtomicLongExample example = new AtomicLongExample();
        example.start();
    }
}
