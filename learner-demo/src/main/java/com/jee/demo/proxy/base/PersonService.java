package com.jee.demo.proxy.base;

/**
 * @Description:
 * @Auther: lyd
 * @Date: 2019/5/26
 * @Version:v1.0
 */
public class PersonService implements IPersonService {
    @Override
    public void doSomeThing(){
        System.out.println("MyPerson is doing its thing.....");
    }

    @Override
    public void saySomeThing() {
        System.out.println("MyPerson is saying its thing.....");

    }


    private void xx(){
        System.out.println("MyPerson is xx its thing.....");
    }
}

