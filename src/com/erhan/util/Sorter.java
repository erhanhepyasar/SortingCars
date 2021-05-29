package com.erhan.util;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Sorter {
    private static final Logger LOGGER = Logger.getLogger("Logger");
    /**
     * Number of threads to use for sorting.
     */
    private static final int MAX_THREADS = Runtime.getRuntime().availableProcessors();  // Example: 8

    private static final int QUEUE_SIZE = 3;

    /**
     * Multiple to use when determining when to fall back.
     */
    private static final int FALLBACK = 2;

    /**
     * Thread pool used for executing sorting Runnable.
     */
    private static final ThreadPool pool = new ThreadPool(QUEUE_SIZE, MAX_THREADS);

    private Sorter() {}

    /**
     * Main method used for sorting from clients. Input is sorted in place using multiple threads.
     *
     * @param input The array to sort.
     * @param <T>   The type of the objects being sorted, must extend Comparable.
     */
    public static  <T extends Comparable<T>> void quicksort(T[] input) throws InterruptedException {
        final long startingTime = System.currentTimeMillis();
        final AtomicInteger count = new AtomicInteger(1);

        pool.submitTask(new QuicksortRunnable<T>(input, 0, input.length - 1, count));

        synchronized (count) {
            count.wait();
        }

        stopWhenDone(startingTime);
    }

    private static void stopWhenDone(Long startingTime) {
        boolean threadPoolIsActive = true;
        while (threadPoolIsActive) {
            if (pool.getQueue().getQueueSize() == 0) {
                pool.getTask().destroy();
                LOGGER.log(Level.INFO, "======================== STEP-4 ======= Cars has been sorted " +
                        "in \"{0} milliseconds\"", + (System.currentTimeMillis() - startingTime));
                threadPoolIsActive = false;
            }
        }
    }

    /**
     * Sorts a section of an array using quicksort. The method used is not technically recursive as it just creates new
     * runnables and hands them off to the ThreadPoolExecutor.
     *
     * @param <T> The type of the objects being sorted, must extend Comparable.
     */
    public static class QuicksortRunnable<T extends Comparable<T>> implements Runnable {
        /**
         * The array being sorted.
         */
        private final T[] values;
        /**
         * The starting index of the section of the array to be sorted.
         */
        private final int left;
        /**
         * The ending index of the section of the array to be sorted.
         */
        private final int right;
        /**
         * The number of threads currently executing.
         */
        private final AtomicInteger activeThreadCount;

        /**
         * Default constructor. Sets up the runnable object for execution.
         *
         * @param values The array to sort.
         * @param left   The starting index of the section of the array to be sorted.
         * @param right  The ending index of the section of the array to be sorted.
         * @param activeThreadCount  The number of currently executing threads.
         */
        public QuicksortRunnable(T[] values, int left, int right, AtomicInteger activeThreadCount) {
            this.values = values;
            this.left = left;
            this.right = right;
            this.activeThreadCount = activeThreadCount;
        }

        /**
         * The thread's run logic. When this thread is done doing its stuff it checks to see if all other threads are as
         * well. If so, then we notify the count object so Sorter.quicksort stops blocking.
         */
        @Override
        public void run() {
            try {
                quicksort(left, right);
            } catch (InterruptedException e) {
                LOGGER.log(Level.WARNING, "Interrupted!", e);
                Thread.currentThread().interrupt();

            }
            synchronized (activeThreadCount) {
                // AtomicInteger.getAndDecrement() returns the old value. If the old value is 1, then we know that the actual value is 0.
                if (activeThreadCount.getAndDecrement() == 1)
                    activeThreadCount.notify();
            }
        }

        /**
         * Method which actually does the sorting. Falls back on recursion if there are a certain number of queued /
         * running tasks.
         *
         * @param pLeft  The left index of the sub array to sort.
         * @param pRight The right index of the sub array to sort.
         */
        private void quicksort(int pLeft, int pRight) throws InterruptedException {
            if (pLeft < pRight) {
                int storeIndex = partition(pLeft, pRight);
                if (activeThreadCount.get() >= FALLBACK * MAX_THREADS) {
                    quicksort(pLeft, storeIndex - 1);
                    quicksort(storeIndex + 1, pRight);
                } else {
                    activeThreadCount.getAndAdd(2);
                    pool.submitTask(new QuicksortRunnable<T>(values, pLeft, storeIndex - 1, activeThreadCount));
                    pool.submitTask(new QuicksortRunnable<T>(values, storeIndex + 1, pRight, activeThreadCount));
                }
            }
        }

        /**
         * Partitions the portion of the array between indexes left and right, inclusively, by moving all elements less
         * than values[pivotIndex] before the pivot, and the equal or greater elements after it.
         *
         * @param pLeft
         * @param pRight
         * @return The final index of the pivot value.
         */
        private int partition(int pLeft, int pRight) {
            T pivotValue = values[pRight];
            int storeIndex = pLeft;
            for (int i = pLeft; i < pRight; i++) {
                if (values[i].compareTo(pivotValue) < 0) {
                    swap(i, storeIndex);
                    storeIndex++;
                }
            }
            swap(storeIndex, pRight);
            return storeIndex;
        }

        /**
         * Simple swap method.
         *
         * @param left  The index of the first value to swap with the second value.
         * @param right The index of the second value to swap with the first value.
         */
        private void swap(int left, int right) {
            T temp = values[left];
            values[left] = values[right];
            values[right] = temp;
        }
    }
}