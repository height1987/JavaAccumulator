package com.height.concurrent.threadPool.threadDemo;

public class WaitDemo {

    public static void main(String[] args) {
        testStateWatingByWait();
    }

    /**
     * 线程调用wait方法，状态变成WAITING。
     */
    public static void testStateWatingByWait() {
        Object lock = new Object();
        WaitingThread waitingThread = new WaitingThread("WaitingThread", lock);
        waitingThread.start();

        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Main thread check the state is " + waitingThread.getState() + "."); // WAITING
    }

    static class WaitingThread extends Thread {
        private int timeout = 0;
        private Object lock;

        public WaitingThread(String name, Object lock) {
            this(name, lock, 0);
        }

        public WaitingThread(String name, Object lock, int timeout) {
            super(name);
            this.timeout = timeout;
            this.lock = lock;
        }

        @Override
        public void run() {
            synchronized (lock) {
                if (timeout == 0) {
                    try {
                        System.out.println("Try to wait.");
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        System.out.println("Try to wait in " + timeout + ".");
                        lock.wait(timeout);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            System.out.println("Over thread.");
        }
    }

}
