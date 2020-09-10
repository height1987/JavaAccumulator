package com.height.concurrent.synchronization.practice;

/**
 * Synchronized 修饰静态方法
 */
public class SynchronizedDemoTwo {


    public static void firstMethod() {
        System.out.println("first  start !");
        sleep(1000);
        System.out.println("first  end ！");
    }

    public static void secondMethod() {
        System.out.println("second  start !");
        sleep(1000);
        System.out.println("second   end ！");
    }

    public synchronized static void firstSynchronizedMethod() {
        System.out.println("first synchronized start !");
        sleep(1000);
        System.out.println("first synchronized end ！");
    }

    public synchronized static void secondSynchronizedMethod() {
        System.out.println("second synchronized start !");
        sleep(1000);
        System.out.println("second synchronized  end ！");
    }

    public static void synchronizedClassMethod() {
        synchronized (SynchronizedDemoTwo.class) {
            System.out.println("synchronized class start !");
            sleep(1000);
            System.out.println("synchronized class end ！");
        }
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
