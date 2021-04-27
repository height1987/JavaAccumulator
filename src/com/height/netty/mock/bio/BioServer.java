package com.height.netty.mock.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class BioServer {
    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        Socket socket = null;
        BufferedReader reader = null;
        try {
            //创建serversocket实例
            serverSocket = new ServerSocket();
            //绑定端口
            serverSocket.bind(new InetSocketAddress("127.0.0.1",9999));
            System.out.println("服务端启动了...");

            //进行监听，等待客户端连接，返回的是客户端的socket实例
            socket = serverSocket.accept();
            System.out.println("客户端：" + socket.getRemoteSocketAddress() + "连接上了");//获取客户端的ip和port

            //服务端来读取消息,从socket的流中读取数据
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));//获取客户端的读取流
            String msg = null;
            System.out.println("正在等待客户端发送消息....");
            while ((msg = reader.readLine()) != null) {
                System.out.println("客户端发来消息：" + msg);
                //回复消息
                OutputStream write = socket.getOutputStream();//获取服务端的输出流
                write.write(("echo:" + msg + "\n").getBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) reader.close();
                if (socket != null) socket.close();
                if (serverSocket != null) serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
