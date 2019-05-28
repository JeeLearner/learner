package com.jee.demo.proxy;

import com.jee.demo.proxy.base.IPersonService;
import com.jee.demo.proxy.base.PersonService;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @Description:
 * @Auther: lyd
 * @Date: 2019/5/26
 * @Version:v1.0
 */
public class ProxyPerson {

    public static void main(String[] args) {
        final PersonService p = new PersonService();
        IPersonService proxy = (IPersonService) Proxy.newProxyInstance(
                PersonService.class.getClassLoader(),
                PersonService.class.getInterfaces(),
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        if (method.getName() == "saySomeThing") {
                            System.out.println("say some thing is special handled.....");
                            p.saySomeThing();
                        } else {
                            Object obj = method.invoke(p, args); // 调用任何public方法都拦截
                            System.out.println("proxy is leaving....");
                        }
                        return null;
                    }
                }
        );
        proxy.doSomeThing();
        proxy.saySomeThing();
    }
}

