package ru.fizteh.fivt.students.ValeriyaSinevich.Threads.Third;


import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadSafeQueue<T> {
    private Queue<T> q;
    private int maxQueueSize;
    private final Lock lock;

    ThreadSafeQueue(int maxQueueSize) {
        this.maxQueueSize = maxQueueSize;
        q = new LinkedList<>();
        lock = new ReentrantLock();
    }

    public final void offer(List<T> e, int wait) throws InterruptedException {
        if (e.size() + q.size() > maxQueueSize) {
            return;
        }
        try {
            if (lock.tryLock(wait, TimeUnit.MILLISECONDS)) {
                try {
                    q.addAll(e);
                    System.out.println("Adding elements by thread"
                            + Thread.currentThread().getName());
                }
                finally {
                    lock.unlock();
                }
            }
            else {
                System.out.println("unable to lock thread "
                        + Thread.currentThread().getName()
                        + " will retry again");
            }
        }
        catch (InterruptedException ex) {
            throw ex;
        }
    }

    public final void take(int n, int wait) throws InterruptedException {
        if (n < q.size()) {
            return;
        }
        try {
            if (lock.tryLock(wait, TimeUnit.MILLISECONDS)) {
                try {
                    for (int i = 0; i < n; ++i) {
                        q.remove();
                    }
                    System.out.println("Removing elements by thread"
                            + Thread.currentThread().getName());
                }
                finally {
                    lock.unlock();
                }
            }
            else {
                System.out.println("unable to lock thread "
                        + Thread.currentThread().getName()
                        + " will retry again");
            }
        }
        catch (InterruptedException ex) {
            throw ex;
        }
    }
}
