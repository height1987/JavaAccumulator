package com.height.concurrent.volatileDemo.practice;

import com.height.common.ThreadUtils;

public class ThreadDemo extends Thread{
    private String changingFace = "我现在很高兴！";

    public String getChangingFace() {
        return changingFace;
    }


    @Override
    public void run() {
        super.run();
//        ThreadUtils.sleep(10);
        changingFace = "我现在不开心了！";
    }
}
