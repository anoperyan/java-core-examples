package hm.net.exam;

import sun.misc.Unsafe;

import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Yan Jiahong
 * Created on 2022/6/21
 */
public class Test {
    private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int CAPACITY = (1 << COUNT_BITS) - 1;

    // runState is stored in the high-order bits
    private static final int RUNNING = -1 << COUNT_BITS;
    private static final int SHUTDOWN = 0 << COUNT_BITS;
    private static final int STOP = 1 << COUNT_BITS;
    private static final int TIDYING = 2 << COUNT_BITS;
    private static final int TERMINATED = 3 << COUNT_BITS;

    public static void main(String[] args) {
        Unsafe unsafe = Unsafe.getUnsafe();
        System.out.println(unsafe);

        //System.out.printf("-1            = %32s\r\n", zeroPaddingBinaryString(-1));
        //System.out.printf("RUNNING       = %32s\r\n", zeroPaddingBinaryString(RUNNING));
        //System.out.printf("SHUTDOWN      = %32s\r\n", zeroPaddingBinaryString(SHUTDOWN));
        //System.out.printf("STOP          = %32s\r\n", zeroPaddingBinaryString(STOP));
        //System.out.printf("TIDYING       = %32s\r\n", zeroPaddingBinaryString(TIDYING));
        //System.out.printf("TERMINATED    = %32s\r\n", zeroPaddingBinaryString(TERMINATED));
        //ExecutorService executor = Executors.newFixedThreadPool(10);
        //executor.submit(() -> {
        //    System.out.println("hello world");
        //    System.out.println("hello world");
        //});
    }

    private static String zeroPaddingBinaryString(int val) {
        String binText = Integer.toBinaryString(val);
        int paddingCount = 32 - binText.length();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < paddingCount; i++) {
            sb.append("0");
        }
        String text = sb + binText;
        return text.substring(0, 3) + " " + text.substring(3);
    }
}
