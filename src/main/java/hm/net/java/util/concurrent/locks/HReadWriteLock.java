package hm.net.java.util.concurrent.locks;

import java.util.concurrent.locks.Lock;

/**
 * 参考{@link java.util.concurrent.locks.ReadWriteLock}
 *
 * @author Yan Jiahong
 * Created on 2022/6/22
 */
public interface HReadWriteLock {
    Lock readLock();

    Lock writeLock();
}
