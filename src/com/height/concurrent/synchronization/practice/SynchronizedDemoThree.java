package com.height.concurrent.synchronization.practice;

/**
 * Synchronized 修饰非静态方法和代码块
 */
public class SynchronizedDemoThree {
    public synchronized void firstSynchronizedMethod() {
        System.out.println("first synchronized start !");
        sleep(1000);
        System.out.println("first synchronized end ！");
    }
    public synchronized void secondSynchronizedMethod() {
        System.out.println("second synchronized start !");
        sleep(1000);
        System.out.println("second synchronized  end ！");
    }
    public void synchronizedBlockMethod() {
        synchronized (this) {
            System.out.println("synchronized block start !");
            sleep(1000);
            System.out.println("synchronized block end ！");
        }
    }
    public static void main(String args[]) {
        SynchronizedDemoThree demo1 = new SynchronizedDemoThree();
        new Thread(new Runnable() {
            @Override
            public void run() {
                demo1.firstSynchronizedMethod();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                demo1.secondSynchronizedMethod();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                demo1.synchronizedBlockMethod();
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
