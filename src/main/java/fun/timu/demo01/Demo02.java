package fun.timu.demo01;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * JUC之后的操作
 * Lock锁+Lambda表达式
 */
public class Demo02 {
    public static void main(String[] args) {
        // TODO:1. 新建资源类
        Ticket2 ticket = new Ticket2();
        // TODO:2. 线程操作资源类 所有的函数式接口，都可以用lambda表达式简化
        new Thread(() -> {
            for (int i = 0; i < 40; i++) ticket.saleTicket();
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 40; i++) ticket.saleTicket();
        }, "B").start();
        new Thread(() -> {
            for (int i = 0; i < 40; i++) ticket.saleTicket();
        }, "C").start();
    }
}

/**
 * 使用Lock锁
 */
class Ticket2 {
    /**
     * Lock 是一个对象
     * <p>
     * ReentrantLock 可重入锁
     * 默认是非公平锁
     * <p>
     * 非公平锁：后面的线程可以插队
     * 公平锁：后面的线程无法插队（容易阻塞）
     */
    private Lock lock = new ReentrantLock();
    private int number = 30;

    public void saleTicket() {
        lock.lock();// 加锁
        try {
            if (number > 0) {
                System.out.printf(Thread.currentThread().getName() + "卖出第%d票，还剩:%d\n", number--, number);
            }
        } catch (Exception e) {
        } finally {
            lock.unlock(); // 解锁
        }
    }

}