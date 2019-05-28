package com.jee.demo.mq_simple.topic;

import java.util.Random;

/**
 * @Description:
 * @Auther: lyd
 * @Date: 2019/5/27
 * @Version:v1.0
 */
public class ProducerTest {

    public static void main(String[] args)  throws Exception{
        ProducerTool producerTool = new ProducerTool();
        Random random = new Random();
        for(int i=0;i<20;i++){
            Thread.sleep(random.nextInt(10)*1000);
            producerTool.produceMessage("Hello, world!--"+i);
            producerTool.close();
        }
    }
}

