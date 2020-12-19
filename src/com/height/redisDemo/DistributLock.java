package com.height.redisDemo;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;

/**
 * 分布式锁 redis版本
 * 1.唯一性
 * 2.不可被抢夺
 * 3.不会发生死锁
 * 4.redis异常时能释放锁
 */
public class DistributLock {
    public static void main(String args[]){
        RedissonClient redissonClient = Redisson.create();
        RLock lock = redissonClient.getLock("12");
        lock.lock();
        doOneThing();
        lock.lock();
        doTwoThing();
        lock.unlock();
        lock.unlock();




    }

    private static void doTwoThing() {
        System.out.println("do oneThing");
    }


    private static void doOneThing() {
        System.out.println("do twoThing");
    }
}
