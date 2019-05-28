package com.jee.demo.reflect.socket;

import com.alibaba.fastjson.JSON;
import com.jee.demo.api.User;

import java.io.*;
import java.net.Socket;

/**
 * @Description:
 * @Auther: lyd
 * @Date: 2019/5/24
 * @Version:v1.0
 */
public class SocketClient {

    public static String ADDRESS = "localhost";
    private static int PORT = 8899;

    public static void main(String[] args) throws IOException {
        /*ServiceIterface service = ProxyUtils.getProxy(ServiceIterface.class,"methodA",hostname,port);
		Result = service.methodA(parameters);*/

        // 向服务器发出请求建立连接
        Socket socket = new Socket(ADDRESS, PORT);
        // 从socket中获取输入输出流
        InputStream inputStream = socket.getInputStream();
        OutputStream outputStream = socket.getOutputStream();

        PrintWriter pw = new PrintWriter(outputStream);
        //1.simpleInvoke
        //pw.println("com.jee.demo.reflect.base.TestBusiness::getPrice::yifu");
        //2.userInvoke
        String params = JSON.toJSONString(new User(1, "test"));
        pw.println("com.jee.demo.reflect.base.TestBusiness::list::" + params);
        pw.flush();

        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        String readLine = br.readLine();
        System.out.println("client get result: " + readLine);

        inputStream.close();
        outputStream.close();
        socket.close();
    }
}

