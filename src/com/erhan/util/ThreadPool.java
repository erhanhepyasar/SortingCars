package com.erhan.util;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ThreadPool {
    private BlockingQueue queue;
    private TaskExecutor task;
    private static final Logger LOGGER = Logger.getLogger("Logger");

    public ThreadPool(int queueSize, int numberOfThreads) {
        queue = new BlockingQueue<>(queueSize);
        startThreads(numberOfThreads);
    }

    private void startThreads(int numberOfThreads) {
        for (int count = 0; count < numberOfThreads; count++) {
            task = new TaskExecutor(queue);
            new Thread(task, "Thread-" + count)
                .start();
        }
        LOGGER.log(Level.INFO, "======================== STEP-3 ======= \"{0}\" threads in the Thread Pool has been started.", numberOfThreads);
    }

    public BlockingQueue<Runnable> getQueue() {
        return queue;
    }

    public TaskExecutor getTask() {
        return task;
    }

    public void submitTask(Runnable task) throws InterruptedException {
        queue.enqueue(task);
    }
}