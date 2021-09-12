package com.zxd.study.juc.pc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 生产者-消费者问题
 */
public class ProviderConsumerDemoWithLock {

    public static void main(String[] args) {

        TicketWithLock ticket = new TicketWithLock();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    ticket.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"A").start();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    ticket.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"B").start();



        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    ticket.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"C").start();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    ticket.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"D").start();





    }






}


/**
 * 用lock替代synchronized
 * 用 condition.await() 替代 this.wait()
 * 用 condition.signalAll() 替代 this.notifyAll()
 */

/**
 * 基本套路是 1.等待
 *          2.业务
 *          3.通知
 */
// 资源类
class TicketWithLock{

    private int number = 0;

    Lock lock = new ReentrantLock();

    Condition condition = lock.newCondition();


    public  void increment() throws InterruptedException {

        lock.lock();

        try {
            /**虚假唤醒
             * 用if判断的话，唤醒后线程会从wait之后的代码开始运行，但是不会重新判断if条件，直接继续运行if代码块之后的代码，而如果使用while的话，也会从wait之后的代码运行，但是唤醒后会重新判断循环条件，如果不成立再执行while代码块之后的代码块，成立的话继续wait。
             * 这也就是为什么用while而不用if的原因了，因为线程被唤醒后，执行开始的地方是wait之后。
             */
            //if (number != 0) {
            while (number != 0) {
                // 等待
                condition.await();
            }
            // 业务
            number++;

            System.out.println(Thread.currentThread().getName() + "=>" + number);
            // 通知
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

    }



    public  void decrement() throws InterruptedException {
        lock.lock();

        try {
            //if (number == 0) {
            while (number == 0) {
                // 等待
                condition.await();
            }
            // 业务
            number--;

            System.out.println(Thread.currentThread().getName() + "=>" + number);
            // 通知
            condition.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }




}