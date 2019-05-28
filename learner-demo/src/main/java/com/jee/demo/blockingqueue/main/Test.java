package com.jee.demo.blockingqueue.main;

import com.jee.demo.blockingqueue.consumer.Consumer;
import com.jee.demo.blockingqueue.producer.Producer;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @Description:
 * @Auther: lyd
 * @Date: 2019/5/27
 * @Version:v1.0
 */
public class Test {

    public static void main(String[] args) {
        BlockingQueue<String> queue = new LinkedBlockingDeque<>(2);
        // BlockingQueue<String> queue = new LinkedBlockingQueue<String>();
        // 不设置的话，LinkedBlockingQueue默认大小为Integer.MAX_VALUE
        // BlockingQueue<String> queue = new ArrayBlockingQueue<String>(2);
        Consumer consumer = new Consumer(queue);
        Producer producer = new Producer(queue);
        for (int i = 0; i < 3; i++) {
            new Thread(producer, "Producer" + (i + 1)).start();
        }
        for (int i = 0; i < 5; i++) {
            new Thread(consumer, "Consumer" + (i + 1)).start();
        }

        new Thread(producer, "Producer" + (5)).start();
    }
}

