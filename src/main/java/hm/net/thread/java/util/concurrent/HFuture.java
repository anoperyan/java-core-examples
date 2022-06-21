package hm.net.thread.java.util.concurrent;

/**
 * 一个{@link HFuture}表示一个异步计算结果。它提供了检查计算是否完成，等待自身完成，检索完成结果的方法。
 * 他的结果只能在计算完成之后使用get方法检索，在结果准备好之前会一直阻塞。取消是通过cancel方法执行的。
 * 额外提供了确认任务是否正常完成还是被取消的。一旦一个计算已经完成，则计算不可取消。
 * 如果你为了可取消性而使用Future，但是不需要返回结果，你可以使用Future<?>返回空结果。
 *
 * @author Yan Jiahong
 * Created on 2022/6/21
 */
public interface HFuture<V> {
}
