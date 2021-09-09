package com.zxd.study.juc.lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class SaleTicketsWithLock {

    public static void main(String[] args) {
        // 多线程卖票,把资源类丢进线程
        Ticket ticket = new Ticket();

        new Thread(() -> {
            for (int i = 0; i < 60; i++) {
                ticket.sale();
            }
        },"A").start();

        new Thread(() -> {
            for (int i = 0; i < 60; i++) {
                ticket.sale();
            }
        },"B").start();

        new Thread(() -> {
            for (int i = 0; i < 60; i++) {
                ticket.sale();
            }
        },"C").start();



    }

}


// 定义票的属性

class Ticket{
    private int number = 50;

    Lock lock = new ReentrantLock();

    // 不需要synchronized关键词，改用lock

    /**
     * 1.synchronized是内置关键字，lock是一个java类
     * 2.synchronized无法获取锁的状态，lock可以
     * 3.synchronized会自动释放锁，lock必须手动释放，否则会造成死锁
     *
     */
    public void sale(){

        // 加锁
        lock.lock();

        try {
            // 业务代码
            if (number > 0) {
                System.out.println(Thread.currentThread().getName() + "卖出了倒数第" + (number--) + "张票，剩余" + (number) + "张" );
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
           //  解锁
            lock.unlock();
        }
    }

}