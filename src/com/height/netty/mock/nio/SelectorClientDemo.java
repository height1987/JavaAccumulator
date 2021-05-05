package com.height.netty.mock.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class SelectorClientDemo {

    public static void main(String[] args) throws IOException {

        SocketChannel client = SocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);

        client.configureBlocking(false);

        ByteBuffer byteBuffer = ByteBuffer.wrap("hello~".getBytes());

        if(!client.connect(inetSocketAddress)){
            while (!client.finishConnect()){
                System.out.println("qwert");
            }
        }

        client.write(byteBuffer);
        client.finishConnect();
        client.close(); //TODO 为什么这里close会让server一直读取到buffer数据，但是如果停在这里 就不会出现问题。
    }

}
