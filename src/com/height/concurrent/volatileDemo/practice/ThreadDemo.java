package com.height.concurrent.volatileDemo.practice;

import com.height.common.ThreadUtils;

public class ThreadDemo extends Thread{
    private String changingFace = "我现在很高兴！";

    @Override
    public void run() {
        super.run();
        ThreadUtils.sleep(1000);
    }
}
