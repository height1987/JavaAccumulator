package com.height.concurrent.volatileDemo.practice;


import com.height.common.ThreadUtils;

public class VolatileDemoOne {

    public static void main(String args[]) {

        methodOne();
        methodTwo();
        methodThree();

    }
    private static void methodOne() {
        ThreadDemo threadDemo = new ThreadDemo();
        threadDemo.start();
        while (threadDemo.isNormalFlag()){
        }
    }
    private static void methodTwo() {
        ThreadDemo threadDemo = new ThreadDemo();
        threadDemo.start();
        while (threadDemo.isNormalFlag()){
            System.out.println("跳出循环!");
        }
    }
    private static void methodThree() {
        ThreadDemo threadDemo = new ThreadDemo();
        threadDemo.start();
        while (threadDemo.isNormalFlag()){
            ThreadUtils.sleep(200);
        }
    }
}
