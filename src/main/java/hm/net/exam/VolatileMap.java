package hm.net.exam;

import javax.xml.soap.Node;
import java.util.Random;

/**
 * @author Yan Jiahong
 * Created on 2022/6/29
 */
public class VolatileMap<K, V> {
    private final Random random = new Random();
    volatile int[] table = new int[10];

    private void setAndCheck() {
        for (int i = 0; i < 100; i++) {
            Thread thread = new Thread(() -> {
                int[] tab = table;
                int idx = random.nextInt(10);
                int val = random.nextInt(10) + 10;
                tab[idx] = val;
            });
        }
    }
}
