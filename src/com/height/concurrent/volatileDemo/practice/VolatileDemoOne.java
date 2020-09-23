package com.height.concurrent.volatileDemo.practice;

import com.height.common.ThreadUtils;

import static com.height.common.ThreadUtils.sleep;

public class VolatileDemoOne {

    public static void main(String args[]){
        ThreadDemo threadDemo = new ThreadDemo();
        threadDemo.start();
        for (int i = 0 ; i < 20 ; i++){
            System.out.println(i+""+threadDemo.getChangingFace());
        }
        System.out.println("最后一次机会：" + threadDemo.getChangingFace());
    }
}
