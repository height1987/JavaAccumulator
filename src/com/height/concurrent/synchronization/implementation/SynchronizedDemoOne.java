package com.height.concurrent.synchronization.implementation;

public class SynchronizedDemoOne {

    private static int age = 1;

    /**
     * synchronized 修饰静态方法
     *
     * @return
     */
    public static synchronized Integer getAgeOne() {
        return age;
    }


    /**
     * synchronized 修饰非静态方法
     *
     * @return
     */
    public synchronized Integer getAgeTwo() {
        return age;
    }

    /**
     * synchronized 修饰代码块
     *
     * @return
     */
    public Integer getAgeThree() {
        synchronized (this) {
            return age;
        }
    }

}
