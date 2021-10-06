package com.zxd.study.juc.jdbcplay;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.*;

public class ThreadPoolJDBCInsertDemo {
    public static void main(String[] args) {

        int threadCount = 500;

        int everyCount = 200;


        final CountDownLatch latch = new CountDownLatch(threadCount);


        ExecutorService threadPool = new ThreadPoolExecutor(
                threadCount,
                threadCount * 2,
                3,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<Runnable>(threadCount/2),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy()); // 默认拒绝策略，队列满了，不处理，抛出异常


        long startTime = System.currentTimeMillis();
        try {
            for (int i = 1; i <=threadCount; i++ ){
                threadPool.execute(() -> {
                    System.out.println("线程" + Thread.currentThread().getName()+ "正在执行。。");
                    // 插入数据库
                    Worker worker = new Worker(Thread.currentThread().getName(),latch,everyCount);
                    worker.doInsert();
                });
            }

            latch.await();
            long endTime = System.currentTimeMillis();

            System.out.println("==总耗时：====>" + (endTime - startTime));

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }


    }
}


class Worker {
    String threadName = "";
    CountDownLatch countDownLatch;
    int everyCount;
    String sql = "INSERT INTO `task_info`(`task_no`,`task_pid`,`task_type`,`ssd_path`,`hdd_path`,`start_time`,`end_time`,`computer_name`,`log_path`,`machine_id`,`username`,`fast_plot`) VALUES(?,?,?,?,?,?,?,?,?,?,?,?);";

    public Worker(String threadName, CountDownLatch countDownLatch, int everyCount) {
        this.threadName = threadName;
        this.countDownLatch = countDownLatch;
        this.everyCount = everyCount;
    }

    void doInsert(){
        System.out.println("====线程" + threadName + "开始准备插入数据===");
        for (int i = 1; i <= everyCount; i++) {
            Object[] params = new Object[]{
                    threadName + UUID.randomUUID().toString(),
                    UUID.randomUUID().toString(),
                    "jdbctest",
                    UUID.randomUUID().toString(),
                    UUID.randomUUID().toString(),
                    new Date(),
                    new Date(),
                    UUID.randomUUID().toString(),
                    UUID.randomUUID().toString(),
                    UUID.randomUUID().toString(),
                    "zxd" + UUID.randomUUID().toString(),
                    Boolean.TRUE
            };

            JDBCUtil.insert(sql,params);

        }

        System.out.println("====线程" + threadName + "执行结束===");
        countDownLatch.countDown();
    }

}
