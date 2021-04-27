package com.height.netty.mock.bio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Scanner;

public class BioClient {
    public static void main(String[] args) {
        //创建Socket实例
        Socket socket = new Socket();
        OutputStream outputStream = null;
        BufferedReader bufferedReader = null;
        Scanner in = new Scanner(System.in);
        try {
            //连接服务器
            socket.connect(new InetSocketAddress("127.0.0.1",9999));
            System.out.println("客户端连接服务器成功！");
            while (true) {
                //发送数据给服务端，先得到socket的流，然后将数据写到流中
                outputStream = socket.getOutputStream();
                System.out.println("请写入数据：");
                String str = in.nextLine();
                outputStream.write((str + "\n").getBytes());

                //接收服务端的消息
                bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String msg = bufferedReader.readLine();
                System.out.println("服务端发来消息：" + msg);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                outputStream.close();
                bufferedReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
