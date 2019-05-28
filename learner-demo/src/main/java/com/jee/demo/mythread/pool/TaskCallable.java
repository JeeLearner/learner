package com.jee.demo.mythread.pool;

import java.util.Random;
import java.util.concurrent.Callable;

/**
 * @Description:
 * @Auther: lyd
 * @Date: 2019/5/27
 * @Version:v1.0
 */
public class TaskCallable implements Callable<String> {

    private int s;
    Random r = new Random();
    public TaskCallable(int s){
        this.s = s;
    }

    @Override
    public String  call() throws Exception {
        String name = Thread.currentThread().getName();
        long currentTimeMillis = System.currentTimeMillis();
        System.out.println(name+" 启动时间：" + currentTimeMillis/1000);

        int rint = r.nextInt(3);
        try {
            Thread.sleep(rint*1000);
        } catch (InterruptedException e) {

            e.printStackTrace();
        }
        System.out.println(name + " is working..."+s);
        return s+"";
    }
}

