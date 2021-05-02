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
        serverSocket.bind(new InetSocketAddress("12",6666));

        Socket accept = serverSocket.accept();
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.submit(new Runnable() {
            @Override
            public void run() {
                handle(accept);
            }
        });

    }

    private static void handle(Socket accept) {

        InputStream inputStream = null;
        byte[] bytes = new byte[1024];

        try {
            inputStream= accept.getInputStream();
            int read = inputStream.read(bytes);
            if(read != -1){
                System.out.println(new String(bytes));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


}
