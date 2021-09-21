package com.zxd.study.juc.pool;


import java.util.concurrent.*;

/**
 * ThreadPoolExecutor
 */
public class ThreadPoolExecutorDemo {

    public static void main(String[] args) {
        ExecutorService threadPool = new ThreadPoolExecutor(
                2,
                5,
                3,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()); // 默认拒绝策略，队列满了，不处理，抛出异常
        /**
         * new ThreadPoolExecutor.AbortPolicy() // 银行满了，还有人进来，不处理这个人的，抛出异 常
         * new ThreadPoolExecutor.CallerRunsPolicy() // 哪来的去哪里！
         * new ThreadPoolExecutor.DiscardPolicy() //队列满了，丢掉任务，不会抛出异常！
         * new ThreadPoolExecutor.DiscardOldestPolicy() //队列满了，尝试去和最早的竞争，也不会 抛出异常！
         */

        try {
            // 最大承载大小 = 阻塞队列大小 + maxPoolSize
            for (int i = 1; i <=9; i++) {
               threadPool.execute(()->{
                   System.out.println(Thread.currentThread().getName() + " -> ok");
               });
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 关闭线程池
            threadPool.shutdown();
        }


    }





}
