package com.height.concurrent.synchronization.practice;

/**
 * Synchronized 修饰代码块方法
 */
public class SynchronizedDemoFour {


    public void firstSynchronizedMethod() {
        synchronized (this) {
            System.out.println("first synchronized start !");
            sleep(1000);
            System.out.println("first synchronized end ！");
        }
    }

    public void secondSynchronizedMethod() {
        synchronized (this) {
            System.out.println("second synchronized start !");
            sleep(1000);
            System.out.println("second synchronized  end ！");
        }
    }


    public static void main(String args[]) {
        synchronizedRun();
    }

    private static void synchronizedRun() {

        SynchronizedDemoFour demo1 = new SynchronizedDemoFour();

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
