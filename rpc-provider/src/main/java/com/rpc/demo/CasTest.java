package com.rpc.demo;

import com.rpc.demo.model.User;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @ClassName CasTest
 * @Description: TODO
 * @Author zly
 * @Date 2021/6/16
 **/
public class CasTest {

    static  User user = new User();
    static int a = 0;
    static CountDownLatch c = new CountDownLatch(2);

    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private ReentrantReadWriteLock.WriteLock writeLock = lock.writeLock();
    private ReentrantReadWriteLock.ReadLock readLock = lock.readLock();

    LongAdder longAdder = new LongAdder();

    public void write() throws InterruptedException {
        writeLock.lock();
        System.out.println(Thread.currentThread().getName() + "准备写数据");
        a++;
        Thread.sleep(5000);
        System.out.println(Thread.currentThread().getName() + "完成写数据");
        writeLock.unlock();
    }

    public void read() throws InterruptedException {
        readLock.lock();
        System.out.println(Thread.currentThread().getName() + "准备读数据");
        Thread.sleep(5000);
        System.out.println(Thread.currentThread().getName() + "完成读数据:" + a);
        readLock.unlock();
    }

    public static void main(String[] args) throws InterruptedException {
        /*List<Thread> ts = new ArrayList<>();
        CasTest test = new CasTest();
        for (int i = 0; i < 5; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        test.write();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            t.start();
        }
        for (int i = 0; i < 5; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        test.read();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
            t.start();
        }*/

        ReentrantLock reentrantLock = new ReentrantLock();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    reentrantLock.lock();
                    System.out.println("第1次获取锁，这个锁是：" + reentrantLock);

                    int index = 1;
                    while (true) {
                        try {
                            reentrantLock.lock();
                            System.out.println("第" + (++index) + "次获取锁，这个锁是：" + reentrantLock);

                            try {
                                Thread.sleep(new Random().nextInt(200));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            if (index == 10) {
                                break;
                            }
                        } finally {
                            reentrantLock.unlock();
                        }

                    }

                } finally {
                    reentrantLock.unlock();
                }
            }
        }).start();
    }
}
