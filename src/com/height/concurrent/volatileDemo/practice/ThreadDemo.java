package com.height.concurrent.volatileDemo.practice;

import com.height.common.ThreadUtils;

public class ThreadDemo extends Thread {
    private boolean normalFlag = true;
    private volatile boolean volatileFlg = true;

    @Override
    public void run() {
        ThreadUtils.sleep(1000);
        normalFlag = false;
        volatileFlg = false;

    }
    public boolean isNormalFlag() {
        return normalFlag;
    }

    public boolean isVolatileFlg() {
        return volatileFlg;
    }
}
