package com.height.netty.mock.nio;

import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

public class SelectorServerDemo {
    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        Selector selector = Selector.open();

        serverSocketChannel.socket().bind(new InetSocketAddress(6666));

        serverSocketChannel.configureBlocking(false);

        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true){
            if(selector.select(1000) == 0){
                System.out.println("服务端等到1s");
                continue;
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()){

                SelectionKey next = iterator.next();
                System.out.println(next.hashCode() + " " + next.channel().hashCode());
                if(next.isAcceptable()){
                    SocketChannel channel = serverSocketChannel.accept();
                    channel.configureBlocking(false);
                    channel.register(selector,SelectionKey.OP_READ, ByteBuffer.allocate(1024));

                }
                if(next.isReadable()){
                    ByteBuffer attachment = (ByteBuffer) next.attachment();
                    SocketChannel channel = (SocketChannel)next.channel();
                    channel.read(attachment);
                    System.out.println(new String(attachment.array()));
                }
                if(next.isValid()){
                    ServerSocketChannel channel = (ServerSocketChannel) next.channel();


                }
                iterator.remove();
            }
        }

    }
}
