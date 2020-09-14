package com.height.concurrent.synchronization.practice;

/**
 * Synchronized 修饰静态方法
 */
public class SynchronizedDemoTwo {
    public synchronized static void synchronizedStaticMethodMethod() {
        System.out.println("synchronized static method start !");
        sleep(1000);
        System.out.println("synchronized static method  end ！");
    }
    public static void synchronizedClassMethod() {
        synchronized (SynchronizedDemoTwo.class) {
            System.out.println("synchronized class start !");
            sleep(1000);
            System.out.println("synchronized class end ！");
        }
    }
    public static void main(String args[]) {
        synchronizedRun();
    }
    private static void synchronizedRun() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SynchronizedDemoTwo.synchronizedStaticMethodMethod();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                SynchronizedDemoTwo.synchronizedClassMethod();
            }
        }).start();
    }
    private static void sleep(int second) {
        try {
            Thread.sleep(second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
