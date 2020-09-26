package com.height.concurrent.volatileDemo.practice;

import com.height.common.ThreadUtils;

public class ThreadDemo extends Thread {
    private volatile boolean normalFlag = true;

    @Override
    public void run() {
        ThreadUtils.sleep(1000);
        normalFlag = false;
        while (true){

        }
    }
    public boolean isNormalFlag() {
        return normalFlag;
    }
}
