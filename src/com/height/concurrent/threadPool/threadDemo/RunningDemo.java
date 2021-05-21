package com.height.concurrent.threadPool.threadDemo;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class RunningDemo {
    public static void main(String[] args) {
        testStateRunnable();
    }
    public static void testStateRunnable() {
        IOThread simpleThread = new IOThread("IOThread");
        simpleThread.start();

        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Main thread check the state is " + simpleThread.getState() + "."); // RUNNABLE
    }


    static class IOThread extends Thread {

        public IOThread(String name) {
            super(name);
        }

        @Override
        public void run() {
            System.out.println("In run method, state is " + getState() + "."); // RUNNABLE
            Socket socket = new Socket();
            try {
                System.out.println("Try to connect socket address which not exist...");
                socket.connect(new InetSocketAddress(
                        InetAddress.getByAddress(new byte[] { (byte) 192, (byte) 168, 1, 14 }), 5678));
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
