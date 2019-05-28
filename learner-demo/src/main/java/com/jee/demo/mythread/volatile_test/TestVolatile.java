package com.jee.demo.mythread.volatile_test;

/**
 * volatile ：线程不安全
 *
 */
public class TestVolatile {

    public static volatile int num = 0;
    public static volatile boolean flag = true;

    public static void main(String[] args) throws InterruptedException {
//        test();
        test2();
        test1();
    }


    /**
     * 使用场景
     *    每次判断运算都会去主内存取flag变量，线程可以改变flag值
     */
    public static void test1(){
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (flag){
                        System.out.println(String.valueOf("ceshi"));
                    }
                }
            }).start();
        }
    }
    public static void test2(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                flag = false;
                System.out.println("ceshifalse");
            }
        }).start();
    }
    /**
     * 这个方法中 volatile是没什么用的
     * @throws InterruptedException
     */
    public static void test() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            System.out.println(num);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 1000; i++) {
                        num++; //操作本身也有影响，拿到时是同一个，但是jvm操作过程中会有快慢
                    }
                }
            }).start();
        }

        Thread.sleep(2000);
        System.out.println(num);
    }
}

