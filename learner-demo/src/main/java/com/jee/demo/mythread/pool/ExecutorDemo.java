package com.jee.demo.mythread.pool;

import org.apache.shiro.subject.ExecutionException;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 列出并发包中的各种线程池
 */
public class ExecutorDemo {

    public static void main(String[] args) {
        //1.
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        //2.
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
        //3.
        int cpuNums = Runtime.getRuntime().availableProcessors();
        System.out.println(cpuNums);
        ExecutorService newFixedThreadPool = Executors.newFixedThreadPool(cpuNums);
        //4.
        ScheduledExecutorService newScheduledThreadPool = Executors.newScheduledThreadPool(8);
        //5.
        ScheduledExecutorService newSingleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
    }
}

