package insights.lock_demo;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

/**
 * @Desc * @param
 * @return
 * @date:
 * @Author
 */
public class MyLock {

    AtomicBoolean flag =  new AtomicBoolean(false);

    Thread owner = null;

    AtomicReference<Node> head = new AtomicReference<>(new Node());

    AtomicReference<Node> tail = new AtomicReference<>(head.get());

    void lock() {
        if (flag.compareAndSet(false, true)) {
            System.out.println(Thread.currentThread().getName() + "直接拿到了锁");
            owner = Thread.currentThread();
            return ;
        }
        Node current = new Node();
        current.thread = Thread.currentThread();
        while (true) {
            Node currentTail = tail.get();
            if (tail.compareAndSet(currentTail, current)) {
                System.out.println(Thread.currentThread().getName() + "加入到了链表尾");
                current.prev = currentTail;
                currentTail.next = current;
                break;
            }
        }
        while (true) {
            if (current.prev == head.get() && flag.compareAndSet(false, true)) {
                owner = Thread.currentThread();
                head.set(current);
                current.prev.next = null;
                current.prev = null;
                System.out.println(Thread.currentThread().getName() + "被唤醒后拿到锁");
                return;
            }
            LockSupport.park();
        }
    }
    void unlock() {
        if (Thread.currentThread() != owner) {
            throw new IllegalStateException("当前线程并没有持有锁");
        }
        Node headNode = head.get();
        Node next = headNode.next;
        owner = null;
        flag.set(false);
        if (next != null) {
            System.out.println(Thread.currentThread().getName() + "唤醒了" + next.thread.getName());
            LockSupport.unpark(next.thread);
        }
    }

    class Node {
        Node prev;
        Node next;
        Thread thread;
    }
}
