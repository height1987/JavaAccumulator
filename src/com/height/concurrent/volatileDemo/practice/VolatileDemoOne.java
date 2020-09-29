package com.height.concurrent.volatileDemo.practice;


import com.height.common.ThreadUtils;

public class VolatileDemoOne {

    public static void main(String args[]) {
//        normalMethodOne();
//        normalMethodTwo();
//        normalMethodThree();
        volatileMethodOne();
    }


    /**
     * 结论：不能正常结束运行
     * 因为flag在子线程中的工作内存中被更新，
     * 没有同步到主内存，导致主线程没有获得。
     * 因为在死循环中，CPU一直被占用，没有触发JVM被动可见变量的操作。
     */
    private static void normalMethodOne() {
        ThreadDemo threadDemo = new ThreadDemo();
        threadDemo.start();
        while (threadDemo.isNormalFlag()){
        }
    }

    /**
     * 结论：可以正常结束运行
     * 因为flag在子线程中更新，同时System.out.print中有synchronized关键词，
     * 在获得锁的时候，会把主内存中的变量重新copy到工作内存，然后释放锁时再把工作内存中的变量同步到主存中去
     * 这种情况下，只要在while循环中添加synchronized模块，也能达到相同的目的
     */
    private static void normalMethodTwo() {
        ThreadDemo threadDemo = new ThreadDemo();
        threadDemo.start();
        while (threadDemo.isNormalFlag()){
            System.out.println("跳出循环!");
        }
    }

    /**
     * 结论：可以正常结束运行
     * 因为在死循环中，子线程释放CPU，触发了JVM的被动可见变量的逻辑
     */
    private static void normalMethodThree() {
        ThreadDemo threadDemo = new ThreadDemo();
        threadDemo.start();
        while (threadDemo.isNormalFlag()){
            ThreadUtils.sleep(200);
        }
    }

    /**
     * 结论：可以正常结束
     * 因为flag在子线程中的工作内存中被更新，
     * 由于变量被volatile关键词修饰，最新值被同步到主内存，
     * 并且通知其他线程重新获取最新值
     */
    private static void volatileMethodOne() {
        ThreadDemo threadDemo = new ThreadDemo();
        threadDemo.start();
        while (threadDemo.isVolatileFlg()){
        }
    }
}
