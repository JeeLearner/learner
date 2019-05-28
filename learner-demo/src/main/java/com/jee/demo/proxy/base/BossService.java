package com.jee.demo.proxy.base;

/**
 * @Description:
 * @Auther: lyd
 * @Date: 2019/5/26
 * @Version:v1.0
 */
public class BossService implements IBossService {

    @Override
    public int yifu(String size){
        System.err.println("天猫小强旗舰店，老板给客户发快递----衣服型号："+size);
        //这件衣服的价钱，从数据库读取
        return 50;
    }
    public void kuzi(){
        System.err.println("天猫小强旗舰店，老板给客户发快递----裤子");
    }
}

