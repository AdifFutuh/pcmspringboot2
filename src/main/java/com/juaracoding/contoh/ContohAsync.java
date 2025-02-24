package com.juaracoding.contoh;

/*
IntelliJ IDEA 2024.1.4 (Ultimate Edition)
Build #IU-241.18034.62, built on June 21, 2024
@Author pollc a.k.a. Paul Christian
Java Developer
Created on 24/02/2025 21:18
@Last Modified 24/02/2025 21:18
Version 1.0
*/
public class ContohAsync {


    public static void main(String[] args) throws InterruptedException {
        System.out.println("=====MULAI======");
//        for (int i = 0; i < 10; i++) {
//            System.out.println(i);
//        }


        Thread t = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 10; i++) {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println(i);
                }
            }
        });
        t.start();
        Thread.sleep(2000);
        System.out.println("=====SELESAI======");
    }
}
