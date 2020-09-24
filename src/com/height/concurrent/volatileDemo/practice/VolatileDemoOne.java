package com.height.concurrent.volatileDemo.practice;


import com.height.common.ThreadUtils;

public class VolatileDemoOne {

    public static void main(String args[]) {

        ThreadDemo threadDemo = new ThreadDemo();
        threadDemo.start();
        while (threadDemo.isNormalFlag()){
            int i = 0;
            int iw = 0;
        }

    }
}
