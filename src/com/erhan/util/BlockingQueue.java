package com.erhan.util;

import java.util.LinkedList;

/**
 * BlockingQueue class will be used to store tasks.
 */

public class BlockingQueue<Type> {
    private final LinkedList<Type> queue = new LinkedList<>();
    private final int EMPTY = 0;
    private final int MAX_TASK_IN_QUEUE;

    public BlockingQueue(int size){
        this.MAX_TASK_IN_QUEUE = size;
    }

    public synchronized void enqueue(Type task) throws InterruptedException  {
        while(this.queue.size() == this.MAX_TASK_IN_QUEUE) {
            wait();
        }
        if(this.queue.size() == EMPTY) {
            notifyAll();
        }
        this.queue.offer(task);
    }

    public synchronized Type dequeue() throws InterruptedException{
        while(this.queue.size() == EMPTY){
            wait();
        }
        if(this.queue.size() == this.MAX_TASK_IN_QUEUE){
            notifyAll();
        }
        return this.queue.poll();
    }

    public int getQueueSize() {
        return this.queue.size();
    }

}
