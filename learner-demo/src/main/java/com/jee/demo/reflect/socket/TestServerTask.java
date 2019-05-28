package com.jee.demo.reflect.socket;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jee.demo.api.User;
import com.jee.demo.socket.GetDataServiceImpl;

import java.io.*;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.List;

/**
 * @Description:
 * @Auther: lyd
 * @Date: 2019/5/25
 * @Version:v1.0
 */
public class TestServerTask implements Runnable{
    Socket socket ;
    InputStream in=null;
    OutputStream out = null;

    public TestServerTask(Socket socket) {
        this.socket = socket;
    }

    //业务逻辑：跟客户端进行数据交互
    @Override
    public void run() {
        try {
            //从socket连接中获取到与client之间的网络通信输入输出流
            in = socket.getInputStream();
            out = socket.getOutputStream();

            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            //从网络通信输入流中读取客户端发送过来的数据
            //注意：socket inputstream的读数据的方法都是阻塞的
            String request = br.readLine();

            /**
             * 反射
             *  com.jee.demo.reflect.base.TestBusiness:getPrice:yifu
             */
            String[] split = request.split("::");
            String className = split[0];
            String methodName = split[1];
            String methodParam= split[2];

            //处理
//            String result = simpleInvoke(className, methodName, methodParam);
            String result = userInvoke(className, methodName, methodParam);

            //将调用结果写到sokect的输出流中，以发送给客户端
            PrintWriter pw = new PrintWriter(out);
            pw.println(result);
            pw.flush();  //清空
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            try {
                in.close();
                out.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private String simpleInvoke(String className, String methodName, String methodParam) throws Exception {
        Class<?> forName = Class.forName(className);
        System.out.println("calling class: " + forName);
        Object newInstance = forName.newInstance();
        Method method = forName.getMethod(methodName, String.class);  //String.class指传参格式
        System.out.println("calling method: " + method);
        Object invoke = method.invoke(newInstance, methodParam);
        String result = invoke.toString();
        System.out.println("results: " + result);
        return result;
    }

    private String userInvoke(String className, String methodName, String methodParam) throws Exception {
        Class<?> forName = Class.forName(className);
        System.out.println("calling class: " + forName);
        Object newInstance = forName.newInstance();
        Method method = forName.getMethod(methodName, User.class);
        System.out.println("calling method: " + method);
        User user = JSON.toJavaObject(JSON.parseObject(methodParam), User.class);
        Object invoke = method.invoke(newInstance, user);
        List<User> list = (List<User>) invoke;
        String result = list.toString();
        System.out.println("results: " + result);
        return result;
    }
}