package com.jee.demo.proxy;

import com.jee.demo.proxy.base.BossService;
import com.jee.demo.proxy.base.IBossService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Description:
 * @Auther: lyd
 * @Date: 2019/5/26
 * @Version:v1.0
 */
public class ProxyBoss {

    /**
     * 对接口方法进行代理
     */
    @SuppressWarnings("unchecked")
    public static <T> T getProxy(final int discountCoupon, final Class<?> interfaceClass, final Class<?> implementsClass) {
        return (T) Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                implementsClass.getInterfaces(),  //实现类的接口或者直接写new Class[] { interfaceClass },
                //new Class[] { interfaceClass },
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        Integer returnValue = (Integer) method.invoke(implementsClass.newInstance(), args); // 调用原始对象以后返回的值
                        return returnValue + discountCoupon; //代理加discountCoupon钱去卖
                    }
                });
    }
}

