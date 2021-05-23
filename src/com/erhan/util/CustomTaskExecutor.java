package com.erhan.util;

/**
 * TaskExecutor class is capable of executing the task.
 */
public class CustomTaskExecutor implements Runnable {
    BlockingQueue<Runnable> queue;

    public CustomTaskExecutor(BlockingQueue<Runnable> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        try {
            while (true) {
//                String name = Thread.currentThread().getName();
                Runnable task = queue.dequeue();
//                System.out.println("Task Started by Thread :" + name);
                task.run();
//                System.err.println("Task Finished by Thread :" + name);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}