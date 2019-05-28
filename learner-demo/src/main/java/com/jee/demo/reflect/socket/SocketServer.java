package com.jee.demo.reflect.socket;

import com.jee.demo.socket.ServiceServerTask;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Description:
 * @Auther: lyd
 * @Date: 2019/5/24
 * @Version:v1.0
 */
public class SocketServer {

    public static String ADDRESS = "localhost";
    private static int PORT = 8899;

    public static void main(String[] args) throws IOException {
        // 创建一个serversocket，绑定到本机的8899端口上
        ServerSocket server = new ServerSocket();
        server.bind(new InetSocketAddress(ADDRESS, PORT));

        // 接受客户端的连接请求;accept是一个阻塞方法，会一直等待，到有客户端请求连接才返回
        while (true) {
            Socket socket = server.accept();
            new Thread(new TestServerTask(socket)).start();
        }
    }
}

