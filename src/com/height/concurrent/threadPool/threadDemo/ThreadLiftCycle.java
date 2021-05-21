package com.height.concurrent.threadPool.threadDemo;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class ThreadLiftCycle {
    public static void main(String[] args) {
//        RunningDemo.testStateRunnable();
        BlockedDemo.testBlockedState();
    }

}
