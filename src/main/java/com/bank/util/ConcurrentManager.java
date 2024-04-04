package com.bank.util;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ConcurrentManager {
    private final BlockingQueue<Runnable> taskQueue = new LinkedBlockingQueue<>();

    public void processConcurrently(int threadCount, Runnable task) throws InterruptedException {
        for (int i = 0; i < threadCount; i++) {
            taskQueue.add(task);
        }

        while (!taskQueue.isEmpty()) {
            Runnable nextTask = taskQueue.take();
            nextTask.run();
        }
    }
}
