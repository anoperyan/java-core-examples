package hm.net.exam.lock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Yan Jiahong
 * Created on 2022/6/22
 */
public class TestReentrantLock {
    private synchronized void method1() {
        System.out.println("method1 invoked");
    }

    private synchronized void method2() {
        System.out.println("method2 invoked");
    }

    public void start() {
        method1();
        method2();
    }

    public static void main(String[] args) {
        new TestReentrantLock().start();
        new ReentrantLock();
    }
}
