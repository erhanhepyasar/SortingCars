package com.erhan.util;

public class ThreadPool {

    BlockingQueue<Runnable> queue;
    public ThreadPool(int queueSize, int nThread) {
        queue = new BlockingQueue<>(queueSize);

        for (int count = 0; count < nThread; count++) {
            String threadName = "Thread-" + count;
            CustomTaskExecutor task = new CustomTaskExecutor(queue);
            Thread thread = new Thread(task, threadName);
            thread.start();
        }
    }

    public void submitTask(Runnable task) throws InterruptedException {
        queue.enqueue(task);
    }
}