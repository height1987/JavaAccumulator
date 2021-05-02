package com.height.netty.mock.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.*;

public class BioServerTest {
    public static void main(String[] args) throws Exception {

        ServerSocket serverSocket = new ServerSocket();
        serverSocket.bind(new InetSocketAddress("127.0.0.1", 6666));

        ExecutorService acceptorPool = Executors.newCachedThreadPool();
        ExecutorService workerPool = Executors.newCachedThreadPool();


        Socket accept = serverSocket.accept();
        System.out.println("accept one!");
        workerPool.submit(new Runnable() {
            @Override
            public void run() {
                handle(accept);
            }
        });

    }

    private static void handle(Socket accept) {

        InputStream inputStream = null;
        try {
            inputStream = accept.getInputStream();
            byte[] bytes = new byte[1024];

            while (true) {
                int read = inputStream.read(bytes);
                if (read != -1) {
                    System.out.println("read comes" +new String(bytes));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                accept.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
