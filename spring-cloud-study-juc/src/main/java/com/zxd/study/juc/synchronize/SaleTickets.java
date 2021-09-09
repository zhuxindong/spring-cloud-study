package com.zxd.study.juc.synchronize;

public class SaleTickets {

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

    // 加上synchronized关键词
    public synchronized void sale(){
        if (number > 0) {
            System.out.println(Thread.currentThread().getName() + "卖出了倒数第" + (number--) + "张票，剩余" + (number) + "张" );
        }
    }

}