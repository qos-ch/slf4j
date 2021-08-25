package org.slf4j.basicTests;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * This class demonstrates that threads accessing the STATE variable always see a consistent value. 
 * 
 * During ongoing initialization the observed value is either ONGOING_INITIALIZATION
 * or one of {SUCCESS, FAILURE}. 
 * 
 * Post initialization the observed value is always one of  {SUCCESS, FAILURE}.
 * 
 * See also http://jira.qos.ch/browse/SLF4J-167 
 * 
 * @author ceki
 *
 */
public class DoubleCheckedInt {

    final static int THREAD_COUNT = 10 + Runtime.getRuntime().availableProcessors() * 2;
    final static int UNINITIALIZED_STATE = 0;
    final static int ONGOING_INITIALIZATION = 1;
    final static int SUCCESS = 2;
    final static int FAILURE = 3;
    final static int NUMBER_OF_STATES = FAILURE + 1;

    private static int STATE = UNINITIALIZED_STATE;

    public static int getState() {
        if (STATE == 0) {
            synchronized (DoubleCheckedInt.class) {
                if (STATE == UNINITIALIZED_STATE) {
                    STATE = ONGOING_INITIALIZATION;
                    long r = System.nanoTime();
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                    }
                    if (r % 2 == 0) {
                        STATE = SUCCESS;
                    } else {
                        STATE = FAILURE;
                    }
                }
            }
        }
        return STATE;
    }

    static public void main(String[] args) throws InterruptedException, BrokenBarrierException {
        StateAccessingThread[] preInitializationThreads = harness();
        check(preInitializationThreads, false);

        System.out.println("============");
        StateAccessingThread[] postInitializationThreads = harness();
        check(postInitializationThreads, true);
    }

    private static StateAccessingThread[] harness() throws InterruptedException, BrokenBarrierException {
        StateAccessingThread[] threads = new StateAccessingThread[THREAD_COUNT];
        final CyclicBarrier barrier = new CyclicBarrier(THREAD_COUNT + 1);
        for (int i = 0; i < THREAD_COUNT; i++) {
            threads[i] = new StateAccessingThread(barrier);
            threads[i].start();
        }

        barrier.await();
        for (int i = 0; i < THREAD_COUNT; i++) {
            threads[i].join();
        }
        return threads;
    }

    private static void check(StateAccessingThread[] threads, boolean postInit) {

        int[] stateCount = getStateCount(threads);
        printStateCount(stateCount);

        if (stateCount[UNINITIALIZED_STATE] != 0) {
            throw new IllegalStateException("getState() should never return a zero value");
        }

        if (stateCount[SUCCESS] != 0 && stateCount[FAILURE] != 0) {
            throw new IllegalStateException("getState() should return consistent values");
        }

        if (postInit) {
            if (stateCount[SUCCESS] != THREAD_COUNT && stateCount[FAILURE] != THREAD_COUNT) {
                throw new IllegalStateException("getState() should return consistent values");
            }
        }

    }

    private static void printStateCount(int[] stateCount) {
        for (int i = 0; i < NUMBER_OF_STATES; i++) {
            switch (i) {
            case UNINITIALIZED_STATE:
                System.out.println("UNINITIALIZED_STATE count: " + stateCount[i]);
                break;
            case ONGOING_INITIALIZATION:
                System.out.println("ONGOING_INITIALIZATION count: " + stateCount[i]);
                break;
            case SUCCESS:
                System.out.println("SUCCESS count: " + stateCount[i]);
                break;
            case FAILURE:
                System.out.println("FAILURE count: " + stateCount[i]);
                break;
            }
        }
    }

    private static int[] getStateCount(StateAccessingThread[] threads) {
        int[] valCount = new int[NUMBER_OF_STATES];
        for (StateAccessingThread thread : threads) {
            int val = thread.state;
            valCount[val] = valCount[val] + 1;
        }
        return valCount;
    }

    static class StateAccessingThread extends Thread {
        public int state = -1;
        final CyclicBarrier barrier;

        StateAccessingThread(CyclicBarrier barrier) {
            this.barrier = barrier;
        }

        public void run() {
            try {
                barrier.await();
            } catch (Exception e) {
                e.printStackTrace();
            }
            state = DoubleCheckedInt.getState();
        }
    };
}
