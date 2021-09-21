package com.zxd.study.juc.blockingqueue;

/**
 * blockingqueue四组API
 */

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 1.抛出异常 add/remove
 * 2.有返回值，不抛出异常 offer/poll
 * 3.阻塞等待 put/take
 * 4.超时等待 offer/poll
 */
public class BQ4APIDemo {

    public static void main(String[] args) throws InterruptedException {
        // 抛出异常
//         test1();

        // 返回布尔值
//        test2();

        // 阻塞等待 （一直等待）
//        test3();

        // 超时等待
        test4();


    }

    /**
     * 抛出异常
     */
    public static void test1(){
        // 指定大小
        ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue(3);
        System.out.println(arrayBlockingQueue.add("A"));
        System.out.println(arrayBlockingQueue.add("B"));


        System.out.println(arrayBlockingQueue.add("C"));
        // 查看队首
        System.out.println(arrayBlockingQueue.element());


        // 抛出异常
        //System.out.println(arrayBlockingQueue.add("D"));

        System.out.println("--------------");


        // 移除的方法
        System.out.println(arrayBlockingQueue.remove());
        System.out.println(arrayBlockingQueue.remove());
        System.out.println(arrayBlockingQueue.remove());

        // 查看队首,抛出异常
        System.out.println(arrayBlockingQueue.element());

    }

    /**
     * 有返回值，不抛出异常
     */
    public static void test2() {
        // 指定大小
        ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue(3);
        System.out.println(arrayBlockingQueue.offer("A"));
        System.out.println(arrayBlockingQueue.offer("B"));
        System.out.println(arrayBlockingQueue.offer("C"));

        // 返回false，不抛异常
        System.out.println(arrayBlockingQueue.offer("D"));

        // 查看队首元素，不抛异常
        System.out.println(arrayBlockingQueue.peek());


        System.out.println("-------");
        System.out.println(arrayBlockingQueue.poll());
        System.out.println(arrayBlockingQueue.poll());
        System.out.println(arrayBlockingQueue.poll());

        // 查看队首元素，不抛异常
        System.out.println(arrayBlockingQueue.peek());

        // 返回null 不抛异常
        System.out.println(arrayBlockingQueue.poll());

    }

    /**
     * 阻塞等待（一直阻塞）
     */
    public static void test3() throws InterruptedException {

        ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue(3);

        arrayBlockingQueue.put("A");
        System.out.println("put A");

        arrayBlockingQueue.put("B");
        System.out.println("put B");

        arrayBlockingQueue.put("C");
        System.out.println("put C");

        // 阻塞等待（一直阻塞）
//        arrayBlockingQueue.put("D");
//        System.out.println("put D");


        System.out.println("------------");
        System.out.println(arrayBlockingQueue.take());
        System.out.println(arrayBlockingQueue.take());
        System.out.println(arrayBlockingQueue.take());

        // 阻塞等待（一直阻塞）
        System.out.println(arrayBlockingQueue.take());


    }


    /**
     * 超时等待
     */
    public static void test4() throws InterruptedException {
        ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue(3);

        System.out.println(arrayBlockingQueue.offer("A"));
        System.out.println(arrayBlockingQueue.offer("B"));
        System.out.println(arrayBlockingQueue.offer("C"));

        // 超时等待
        System.out.println(arrayBlockingQueue.offer("D", 2,TimeUnit.SECONDS));

        System.out.println("----------------");
        System.out.println(arrayBlockingQueue.poll());
        System.out.println(arrayBlockingQueue.poll());
        System.out.println(arrayBlockingQueue.poll());

        // 超时等待
        System.out.println(arrayBlockingQueue.poll(2,TimeUnit.SECONDS));


    }





}
