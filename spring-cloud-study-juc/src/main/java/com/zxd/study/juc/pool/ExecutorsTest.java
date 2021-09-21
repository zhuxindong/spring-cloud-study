package com.zxd.study.juc.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Executors 工具类，三大方法
 *  !! 【强制】 不允许使用Executors 创建线程，此处仅供演示参考
 *   创建线程池推荐使用 ThreadPoolExecutor
 * @see java.util.concurrent.ThreadPoolExecutor
 */
public class ExecutorsTest {

    public static void main(String[] args) {
        // 单个线程
//        ExecutorService threadPool = Executors.newSingleThreadExecutor();

        //固定大小的线程
//        ExecutorService threadPool = Executors.newFixedThreadPool(5);

        // 可伸缩的
        ExecutorService threadPool = Executors.newCachedThreadPool();


        try {
            for (int i = 0; i < 10; i++) {
                threadPool.execute(()->{
                    System.out.println(Thread.currentThread().getName() + " -> ok");
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 用完关闭
            threadPool.shutdown();
        }



    }

}
