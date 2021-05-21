package com.height.concurrent.threadPool.threadDemo;


import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadPrintTwo {

    volatile static boolean strEnd = false;
    volatile static boolean noEnd = false;

    public static void main(String[] args) {

        String str  = "abcdefghihjkl";
        String no ="1234567890";

        ReentrantLock lock = new ReentrantLock();
        CountDownLatch strCountDownLatch = new CountDownLatch(1);


        Condition noCon = lock.newCondition();
        Condition strCon = lock.newCondition();

        Thread noPrint = new Thread(()->{
            try {
                lock.lock();
                Thread.sleep(1000);
                strCountDownLatch.countDown();
                for(int i = 0 ; i< no.length();i++){
                    printOne(no,i);

                    strCon.signal();
                    if(i < no.length() -1 && !strEnd){
                        noCon.await();
                    }
                }
                noEnd = true;
                lock.unlock();
            }catch (Exception e){

            }finally {
                if(lock.isLocked()) {
                    lock.unlock();
                }
            }

        },"");
        Thread strPrint = new Thread(()->{
            try {
                strCountDownLatch.await();
                lock.lock();
                for(int i = 0;i<str.length();i++){
                    printOne(str,i);
                    noCon.signal();
                    if(i < str.length() -1 && !noEnd) {
                        strCon.await();
                    }
                }
                noCon.signal();
                strEnd = true;
                lock.unlock();
            }catch (Exception e){

            }finally {
                if(lock.isLocked()) {
                    lock.unlock();
                }
            }
        });
        noPrint.setName("noPrint");
        strPrint.setName("strPrint");
        noPrint.start();
        strPrint.start();

    }

    private static void myNotify(Condition strCon) {

        strCon.signal();
    }

    private static void printOne(String no, int printIndex) {
        if(no.length() <= printIndex){
            return;
        }
        System.out.print(no.charAt(printIndex));
    }

    private static void myWait(Condition noCon)throws Exception {
        noCon.await();
    }

}
