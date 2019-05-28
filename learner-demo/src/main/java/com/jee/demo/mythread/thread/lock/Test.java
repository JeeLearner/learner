package com.jee.demo.mythread.thread.lock;

/**
 * @Description:
 * @Auther: lyd
 * @Date: 2019/5/26
 * @Version:v1.0
 */
public class Test {

    public static void main(String[] args) {
        int num = Runtime.getRuntime().availableProcessors();
        //4      4核CPU
        System.out.println(num);
    }
}

