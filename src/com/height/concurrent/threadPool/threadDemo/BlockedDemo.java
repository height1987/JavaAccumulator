package com.height.concurrent.threadPool.threadDemo;

public class BlockedDemo {
    public static void main(String[] args) {
        testBlockedState();
    }
    public static void testBlockedState() {
        Object lock = new Object();
        SleepThread t1 = new SleepThread("t1", lock);
        SleepThread t2 = new SleepThread("t2", lock);
        t1.start();
        t2.start();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Thread t1's state " + t1.getState());
        System.out.println("Thread t2's state " + t2.getState());
    }

    static class SleepThread extends Thread {
        private final String name;
        private final Object lock;

        public SleepThread(String name, Object lock) {
            super(name);
            this.name = name;
            this.lock = lock;
        }

        @Override
        public void run() {
            System.out.println("Thread:" + name + " in run.");

            synchronized (lock) {
                System.out.println("Thread:" + name + " hold the lock.");

                try {
                    Thread.sleep(1000 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println("Thread:" + name + " return the lock.");
            }
        }
    }
}
