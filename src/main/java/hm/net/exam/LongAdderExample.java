package hm.net.exam;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.atomic.LongAdder;

/**
 * 参考{@link LongAdder}
 *
 * @author Yan Jiahong
 * Created on 2022/6/29
 */
public class LongAdderExample {
    private final ExecutorService executor = Executors.newFixedThreadPool(30);
    private final LongAdder adder = new LongAdder();

    static final class AddTask implements Runnable {
        private final LongAdder adder;
        private final long target;

        public AddTask(LongAdder adder, long target) {
            this.adder = adder;
            this.target = target;
        }

        @Override
        public void run() {
            while (adder.sum() < target) {
                adder.increment();
                //System.out.printf("%s : %s\r\n", Thread.currentThread(), adder.sum());
            }
        }
    }

    public void start() throws InterruptedException {
        final long target = 1000 * 10000;
        long start = System.currentTimeMillis();
        for (int i = 0; i < 50; i++) {
            executor.submit(new AddTask(adder, target));
        }
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.HOURS);
        long end = System.currentTimeMillis();
        // duration: 92403ms
        System.out.printf("duration: %sms\r\n", (end - start));
    }

    public static void main(String[] args) throws Exception {
        LongAdderExample example = new LongAdderExample();
        example.start();
    }
}
