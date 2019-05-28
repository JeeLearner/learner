package com.jee.demo.mythread.pool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * callable 跟runnable的区别：
 * runnable的run方法不会有任何返回结果，所以主线程无法获得任务线程的返回值
 *
 * callable的call方法可以返回结果，但是主线程在获取时是被阻塞，需要等待任务线程返回才能拿到结果
 *
 */
public class ThreadPoolWithRunable {

    /**
     * 通过线程池执行线程
     * @param args
     */
    public static void main(String[] args) {
        //创建一个线程池
        ExecutorService pool = Executors.newCachedThreadPool();
        for(int i = 1; i < 15; i++){
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("thread name: " + Thread.currentThread().getName());
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        pool.shutdown();
    }
}

