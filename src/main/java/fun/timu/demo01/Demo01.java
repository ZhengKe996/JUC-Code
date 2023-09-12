package fun.timu.demo01;

public class Demo01 {
    /**
     * 传统的Synchronized
     * Synchronized方法和Synchronized块
     * 1.架构：高内聚，低耦合
     * 2.套路：线程操作资源类，资源类是单独的
     *
     * @param args
     */
    public static void main(String[] args) {
        // TODO:1. 新建资源类
        Ticket ticket = new Ticket();
        // TODO:2. 线程操作资源类
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 40; i++) {
                    ticket.saleTicket();
                }
            }
        }, "A").start(); // Java 不能创建线程

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 40; i++) {
                    ticket.saleTicket();
                }
            }
        }, "B").start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 40; i++) {
                    ticket.saleTicket();
                }
            }
        }, "C").start();
    }
}

/**
 * 单独的资源类：属性和方法
 * 这样才能实现复用
 */
class Ticket {
    private int number = 30;

    /**
     * 传统的同步锁
     */
    public synchronized void saleTicket() {
        if (number > 0) {
            System.out.printf(Thread.currentThread().getName() + "卖出第%d票，还剩:%d\n", number--, number);
        }
    }

}