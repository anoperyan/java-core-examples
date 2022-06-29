package hm.net.java.util;

/**
 * 参考{@link java.util.Iterator}
 *
 * @author Yan Jiahong
 * Created on 2022/6/28
 */
public interface HIterator<E> {
    boolean hasNext();

    E next();

    default void remove() {
        throw new UnsupportedOperationException("remove");
    }


}
