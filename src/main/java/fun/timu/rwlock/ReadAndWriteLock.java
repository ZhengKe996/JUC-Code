package fun.timu.rwlock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadAndWriteLock {
    public static void main(String[] args) {
        MyCacheLock myCache = new MyCacheLock();
        for (int i = 1; i <= 5; i++) {
            final int tempInt = i;
            new Thread(() -> {
                myCache.put(tempInt + "", tempInt + "");
            }, String.valueOf(i)).start();
        }

        for (int i = 1; i <= 5; i++) {
            final int tempInt = i;
            new Thread(() -> {
                myCache.get(tempInt + "");
            }, String.valueOf(i)).start();
        }

    }
}

class MyCache {
    private volatile Map<String, Object> map = new HashMap<>();

    // Read: 支持多线程同时读
    public void get(String key) {
        System.out.println(Thread.currentThread().getName() + "Read: " + key);
        Object o = map.get(key);
        System.out.println(Thread.currentThread().getName() + " Result=>: " + o);
    }

    // Write: 应该保证原子性
    public void put(String key, Object value) {
        System.out.println(Thread.currentThread().getName() + "Write: " + key);
        map.put(key, value);
        System.out.println(Thread.currentThread().getName() + " Write OK");
    }
}

class MyCacheLock {
    private volatile Map<String, Object> map = new HashMap<>();

    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    // Read: 支持多线程同时读
    public void get(String key) {
        readWriteLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "Read: " + key);
            Object o = map.get(key);
            System.out.println(Thread.currentThread().getName() + " Result=>: " + o);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    // Write: 应该保证原子性
    public void put(String key, Object value) {
        readWriteLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "Write: " + key);
            map.put(key, value);
            System.out.println(Thread.currentThread().getName() + " Write OK");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }
}