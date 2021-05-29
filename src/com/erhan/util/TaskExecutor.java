package com.erhan.util;


import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * TaskExecutor class is capable of executing the task.
 */
public class TaskExecutor implements Runnable {
    private static final Logger LOGGER = Logger.getLogger("Logger");
    final BlockingQueue<Runnable> queue;

    public TaskExecutor(BlockingQueue<Runnable> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
                Runnable task = queue.dequeue();
                task.run();
            }
        } catch (InterruptedException e) {
            LOGGER.log(Level.WARNING, "Sorting cars has been interrupted :", e);
            Thread.currentThread().interrupt();
        }

    }

    public void destroy() {
        synchronized (this) {
            Thread.currentThread().interrupt();
        }
    }
}