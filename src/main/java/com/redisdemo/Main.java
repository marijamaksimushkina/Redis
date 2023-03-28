package com.redisdemo;

import java.util.Random;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) {
        Storage storage = new Storage();
        storage.init();
        new Thread(() -> {
            for(;;) {
                storage.listUsers();
                try {
                    sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        new Thread(() -> {
            Random r1 = new Random();
            for(;;) {
                if (r1.nextInt(100) > 60)
                    storage.donate(String.valueOf(r1.nextInt(19) + 1));
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
