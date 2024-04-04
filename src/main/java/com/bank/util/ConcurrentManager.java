package com.bank.util;

import java.util.ArrayList;
import java.util.List;

public class ConcurrentManager {
    public void processConcurrently(int threadCount, Runnable task) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();

        for (int i = 0; i < threadCount; i++) {
            Thread thread = new Thread(task);
            threads.add(thread);
            thread.start();
        }

        for (Thread thread : threads) {
            thread.join();
        }
    }
}
