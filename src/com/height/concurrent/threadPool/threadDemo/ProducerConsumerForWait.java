package com.height.concurrent.threadPool.threadDemo;

import java.util.LinkedList;
import java.util.Queue;

public class ProducerConsumerForWait {
    public static class Producer extends Thread {
        volatile Queue<Integer> queue;
        int maxsize;

        Producer(Queue<Integer> queue, int maxsize, String name) {
            this.queue = queue;
            this.maxsize = maxsize;
            this.setName(name);
        }
        @Override
        public void run() {
            while (true) {
                synchronized (queue) {
                    try{
                        Thread.sleep(500);
                    } catch (Exception e) {}

                    System.out.println(this.getName() + "获得队列的锁");
                    //条件的判断一定要使用while而不是if
                    while (queue.size() > 0) {
                        System.out.println("队列已满，生产者" + this.getName() + "等待");
                        try {
                            queue.wait();
                            System.out.println("生产者" + this.getName() + "醒来");
                        } catch (Exception e) {}
                    }
                    int num = (int)(Math.random()*100);
                    queue.offer(num);

                    System.out.println(this.getName() + "生产一个元素：" + num);
                    queue.notifyAll();

                    System.out.println(this.getName() + "退出一次生产过程！");
                }
            }
        }
    }

    public static class Consumer extends Thread {
        Queue<Integer> queue;
        int maxsize;

        Consumer(Queue<Integer> queue, int maxsize, String name) {
            this.queue = queue;
            this.maxsize = maxsize;
            this.setName(name);
        }

        @Override
        public void run() {
            while (true) {
                synchronized (queue) {
                    try{
                        Thread.sleep(500);
                    } catch (Exception e) {}

                    System.out.println(this.getName() + "获得队列的锁");
                    //条件的判断一定要使用while而不是if
                    while (queue.isEmpty()) {
                        System.out.println(this.getName() +"队列为空，消费者" + this.getName() + "等待");
                        try{
                            queue.wait(5000000);
                        } catch (Exception e) {}
                    }
//                    System.out.println(this.getName() +  "队列是否为空" + queue.isEmpty());
                    Integer poll = queue.poll();
                    System.out.println(this.getName() + "消费一个元素："+(poll == null ? "-XX-" : poll));
                    queue.notifyAll();

                    System.out.println(this.getName() + "退出一次消费过程！");
                }
            }
        }
    }

    public static void main(String[] args) {
        Queue<Integer> queue = new LinkedList<>();
        int maxsize = 2;

        Producer producer = new Producer(queue, maxsize, "Producer");
        Producer producer2 = new Producer(queue, maxsize, "Producer2");
        Producer producer3 = new Producer(queue, maxsize, "Producer3");
        Producer producer4 = new Producer(queue, maxsize, "Producer4");
        Producer producer5 = new Producer(queue, maxsize, "Producer5");
        Consumer consumer1 = new Consumer(queue, maxsize,"Consumer1");
//        Consumer consumer2 = new Consumer(queue, maxsize,"Consumer2");
//        Consumer consumer3 = new Consumer(queue, maxsize,"Consumer3");

        producer.start();
        consumer1.start();
        producer2.start();
        producer3.start();
        producer4.start();
        producer5.start();
//        consumer2.start();
//        consumer3.start();
    }
}
