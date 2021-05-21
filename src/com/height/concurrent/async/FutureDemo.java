package com.height.concurrent.async;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

public class FutureDemo {

    public static void main(String[] args) {
        CompletableFuture<String> one = CompletableFuture.supplyAsync(getSupplier("one"));
        CompletableFuture<String> two = CompletableFuture.supplyAsync(getSupplier("two"));
        System.out.println("do others");

        try {
            CompletableFuture<Void> voidCompletableFuture = CompletableFuture.allOf(one, two);

            System.out.println("join begin");
            voidCompletableFuture.join();

            System.out.println("join end");

            String oneS = one.get();
            System.out.println("calOne get");

            String twoS = two.get();
            System.out.println("calTwo get");



            System.out.println(oneS + twoS);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private static Supplier<String> getSupplier(String name ){
        return () -> {
            try {
                System.out.println("run + " + name);
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return name;
        };

    }

    private static String calOne(String name ){
        Callable<String> callable = new Callable<String>() {
            @Override
            public String call() throws Exception {
                System.out.println("start  run " + name);

                Thread.sleep(3000);

                return "run : "+name;
            }
        };
        try {
            return callable.call();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }




}
