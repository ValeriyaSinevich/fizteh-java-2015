package ru.fizteh.fivt.students.ValeriyaSinevich.Threads.ThreadsFirst;


public class ThreadToCount extends Thread {
    private static int curThreadNum;
    private static int numberOfThreads;
    private static int myNumber;
    @Override
    public void run() {
        synchronized (this.getClass()) {
            while (true) {
                if (curThreadNum == myNumber) {
                    System.out.println("Thread " + myNumber);
                    curThreadNum = (curThreadNum + 1) % numberOfThreads;
                    this.getClass().notifyAll();
                }
                try {
                    this.getClass().wait();
                } catch (InterruptedException e) { }
            }
        }
    }

    public static void setNumberOfThreads(int numberOfThreadsGiven) {
        numberOfThreads = numberOfThreadsGiven;
    }

    public ThreadToCount(int thisThreadNumber) {
        this.myNumber = thisThreadNumber;
    }

}
