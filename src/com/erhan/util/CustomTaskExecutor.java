package com.erhan.util;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * TaskExecutor class is capable of executing the task.
 */
public class CustomTaskExecutor implements Runnable {
    private static final Logger LOGGER = Logger.getLogger("Logger");
    final CustomBlockingQueue<Runnable> queue;

    public CustomTaskExecutor(CustomBlockingQueue<Runnable> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
//              String threadName = Thread.currentThread().getName();
                Runnable task = queue.dequeue();
//                LOGGER.log(Level.INFO, "Task has been started by Thread : {0}", threadName);
                task.run();
//                LOGGER.log(Level.INFO, "Task has been finished by Thread : {0}", threadName);
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