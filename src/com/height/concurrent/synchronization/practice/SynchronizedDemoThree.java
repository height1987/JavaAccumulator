package com.height.concurrent.synchronization.practice;

/**
 * Synchronized 修饰非静态方法
 */
public class SynchronizedDemoThree {


    private synchronized void firstSynchronizedMethod() {
        System.out.println("first synchronized start !");
        sleep(1000);
        System.out.println("first synchronized end ！");
    }

    private synchronized void secondSynchronizedMethod() {
        System.out.println("second synchronized start !");
        sleep(1000);
        System.out.println("second synchronized  end ！");
    }


    public static void main(String args[]) {
        synchronizedRun();
    }

    private static void synchronizedRun() {

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
    }

    private static void sleep(int second) {
        try {
            Thread.sleep(second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
