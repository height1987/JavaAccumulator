package com.height.concurrent.volatileDemo.practice;

import com.height.common.ThreadUtils;

public class ThreadDemo extends Thread {
    private boolean normalFlag = true;
    private boolean volatileFlag = true;

    @Override
    public void run() {
        ThreadUtils.sleep(100);
        normalFlag = false;
        volatileFlag = false;
    }

    public boolean isVolatileFlag() {
        return volatileFlag;
    }

    public boolean isNormalFlag() {
        return normalFlag;
    }
}
