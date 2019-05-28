package com.jee.demo.webservice.client;

import com.jee.demo.api.User;
import com.jee.demo.webservice.server.DemoService;
import org.apache.cxf.binding.BindingConfiguration;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.transport.http.HTTPConduit;
import org.apache.cxf.transports.http.configuration.HTTPClientPolicy;

public class Cxfclient {
    //webservice接口地址
    private static String ADDRESS = "http://127.0.0.1:8080/demo/api?wsdl";

    //测试
    public static void main(String[] args) throws Exception {
        test1();
        test2();
    }

    /**
     * 方式1:使用代理类工厂,需要拿到对方的接口
     *          通过接口协议获取数据类型
     */
    public static void test1() throws Exception{
        try {
            // 代理工厂
            JaxWsProxyFactoryBean jaxWsProxyFactoryBean = new JaxWsProxyFactoryBean();
            // 设置代理地址
            jaxWsProxyFactoryBean.setAddress(ADDRESS);
            //添加用户名密码拦截器
            jaxWsProxyFactoryBean.getOutInterceptors().add(new LoginInterceptor("root","admin"));;
            // 设置接口类型
            jaxWsProxyFactoryBean.setServiceClass(DemoService.class);
            // 创建一个代理接口实现
            DemoService demoService = (DemoService) jaxWsProxyFactoryBean.create();

            //可选：设置链接超时和响应时间
            Client proxy= ClientProxy.getClient(demoService);
            HTTPConduit conduit=(HTTPConduit)proxy.getConduit();
            HTTPClientPolicy policy=new HTTPClientPolicy();
            policy.setConnectionTimeout(1000);
            policy.setReceiveTimeout(1000);
            conduit.setClient(policy);

            // 数据准备
            int userId = 1;
            // 调用代理接口的方法调用并返回结果
            User result = demoService.getUser(userId);
            System.out.println("==============返回结果:" + result.getName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 方式2：动态调用方式
     */
    public static void test2() {
        // 创建动态客户端
        JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
        Client client = dcf.createClient(ADDRESS);
        // 需要密码的情况需要加上用户名和密码
        client.getOutInterceptors().add(new LoginInterceptor("root","admin"));
        Object[] objects = new Object[5];
        try {
            // invoke("方法名",参数1,参数2,参数3....);
            System.out.println("======client"+client);
            objects = client.invoke("getUser", 1);
            System.out.println("返回数据:" + objects[0].toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}