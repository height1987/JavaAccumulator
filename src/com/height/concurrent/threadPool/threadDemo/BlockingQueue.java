package com.height.concurrent.threadPool.threadDemo;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueue {

    public static int QUEUE_SIZE = 10;
    public volatile static Object[] queue = new Object[QUEUE_SIZE];
    public volatile static int elementCount = 0; // 0 : null;  QUEUE_SIZE -1 : full
    public static ReentrantLock lock = new ReentrantLock();
    Condition needAdd = lock.newCondition();
    Condition needConsume = lock.newCondition();

    public void offer(Object e) throws Exception{
        try {
            lock.lock();
            if(elementCount == QUEUE_SIZE){
                needAdd.await();
            }else{
                queue[elementCount] = e;
                elementCount++;
                needConsume.signal();
            }

        }finally {
            if(lock.isLocked()){
                lock.unlock();
            }
        }
    }
    public void poll(){

    }



}
