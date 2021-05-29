package com.erhan.util;

import java.util.logging.Level;
import java.util.logging.Logger;

public class CustomThreadPool {
    private CustomBlockingQueue queue;
    private CustomTaskExecutor task;
    private static final Logger LOGGER = Logger.getLogger("Logger");

    public CustomThreadPool(int queueSize, int numberOfThreads) {
        queue = new CustomBlockingQueue<>(queueSize);
        startThreads(numberOfThreads);
    }

    private void startThreads(int numberOfThreads) {
        for (int count = 0; count < numberOfThreads; count++) {
            task = new CustomTaskExecutor(queue);
            new Thread(task, "Thread-" + count)
                .start();
        }
        LOGGER.log(Level.INFO, "======================== STEP-3 ======= \"{0}\" threads in the Thread Pool has been started.", numberOfThreads);
    }

    public CustomBlockingQueue<Runnable> getQueue() {
        return queue;
    }

    public CustomTaskExecutor getTask() {
        return task;
    }

    public void submitTask(Runnable task) throws InterruptedException {
        queue.enqueue(task);
    }
}