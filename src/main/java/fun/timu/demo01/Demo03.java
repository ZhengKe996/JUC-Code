package fun.timu.demo01;

/**
 * Synchronized版（四条线程不能实现交替执行，存在虚假唤醒问题）
 * 目的：有两个线程A、B，还有一个初始值为0；
 * 实现两个线程交替执行，对该变量 +1 -1 交替10次；
 * <p>
 * 传统的 wait和notifyAll 无法实现精准唤醒
 */
public class Demo03 {
    public static void main(String[] args) {
        Data data = new Data();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    data.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    data.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "C").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    data.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "B").start();

        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                try {
                    data.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "D").start();

    }
}

/**
 * 线程之间的通信：判断   执行  通知
 */
class Data {
    private int number = 0;

    public synchronized void increment() throws InterruptedException {
        if (number != 0) {
            //TODO 判断是否需要等待
            this.wait();
        }
        number++; // 执行
        System.out.println(Thread.currentThread().getName() + "\t" + number);

        // 通知
        this.notifyAll();// 唤醒所有线程
    }

    public synchronized void decrement() throws InterruptedException {
        if (number == 0) {
            //TODO 判断是否需要等待
            this.wait();
        }
        number--; // 执行
        System.out.println(Thread.currentThread().getName() + "\t" + number);

        // 通知
        this.notifyAll();// 唤醒所有线程
    }
}