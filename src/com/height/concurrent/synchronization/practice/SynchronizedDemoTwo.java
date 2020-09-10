package com.height.concurrent.synchronization.practice;

/**
 * Synchronized 修饰静态方法
 */
public class SynchronizedDemoTwo {


    private static void firstMethod() {
        System.out.println("first  start !");
        sleep(1000);
        System.out.println("first  end ！");
    }

    private static void secondMethod() {
        System.out.println("second  start !");
        sleep(1000);
        System.out.println("second   end ！");
    }

    private synchronized static void firstSynchronizedMethod() {
        System.out.println("first synchronized start !");
        sleep(1000);
        System.out.println("first synchronized end ！");
    }

    private synchronized static void secondSynchronizedMethod() {
        System.out.println("second synchronized start !");
        sleep(1000);
        System.out.println("second synchronized  end ！");
    }


    public static void main(String args[]) {
        simpleRun();
        sleep(3000);
        System.out.println("————————————————————————");
        synchronizedRun();
    }

    private static void simpleRun() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SynchronizedDemoTwo.firstMethod();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                SynchronizedDemoTwo.secondMethod();
            }
        }).start();
    }

    private static void synchronizedRun() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                SynchronizedDemoTwo.firstSynchronizedMethod();
            }
        }).start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                SynchronizedDemoTwo.secondSynchronizedMethod();
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
