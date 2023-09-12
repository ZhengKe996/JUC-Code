package fun.timu.demo01;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 实现线程交替执行！
 * <p>
 * 主要实现目标：精准唤醒线程！
 * 三个线程：A、B、C
 * 三个方法：P5、P10、P15
 */
public class Demo05 {
    public static void main(String[] args) {
        Data3 data = new Data3();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) data.print5();
         }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) data.print10();
        }, "B").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) data.print15();
        }, "C").start();
    }
}


class Data3 {
    private int number = 1; // 1A 2B 3C
    private Lock lock = new ReentrantLock();

    // 实现精准访问
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();

    public void print5() {
        lock.lock();
        try {
            while (number != 1) {
                //TODO 判断是否需要等待
                condition1.await();
            }
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            } // 执行

            // 通知第二个干活
            number = 2;
            condition2.signal();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void print10() {

        lock.lock();
        try {
            while (number != 2) {
                //TODO 判断是否需要等待
                condition2.await();
            }
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            } // 执行

            // 通知三个干活
            number = 3;
            condition3.signal();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            lock.unlock();
        }

    }

    public void print15() {
        lock.lock();
        try {
            while (number != 3) {
                //TODO 判断是否需要等待
                condition3.await();
            }
            for (int i = 0; i < 5; i++) {
                System.out.println(Thread.currentThread().getName() + "\t" + i);
            } // 执行

            // 通知第一个干活
            number = 1;
            condition1.signal();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            lock.unlock();
        }
    }
}