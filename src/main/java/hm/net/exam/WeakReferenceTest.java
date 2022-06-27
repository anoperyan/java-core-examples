package hm.net.exam;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * @author Yan Jiahong
 * Created on 2022/6/27
 */
public class WeakReferenceTest {
    public static void main(String[] args) {
        Object referent = new Object();
        WeakReference<Object> wr = new WeakReference<>(referent);
        referent = null;
        System.gc();
        Object obj = wr.get();
        if (obj == null) {
            System.out.println("obj is null");
        } else {
            System.out.println("obj is not null");
        }
    }
}
