package ru.fizteh.fivt.students.ValeriyaSinevich.Threads.Second;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class YesCaptain implements Runnable {
    private static boolean yes = false;

    public static void setEnd(boolean end) {
        YesCaptain.end = end;
    }

    private static boolean end = false;

    public static boolean getSuccess() {
        return yes;
    }

    public static void setSuccess(boolean success) {
        yes = success;
    }

    @Override
    public void run() {
        while (true) {
            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                System.out.println("child interrupted!");
                e.printStackTrace();
            }
            if (end) {
                return;
            }

            int ans = rand.nextInt(10);
            if (ans > 1) {
                System.out.printf("Yes, Captain!\n");
            } else {
                System.out.printf("No :(\n");
                yes = false;
            }
            try {
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                System.out.println("child interrupted!");
                e.printStackTrace();
            }
        }

    }

    private final CyclicBarrier cyclicBarrier;
    private Random rand = new Random();

    public YesCaptain(CyclicBarrier cyclicBarrier) {
        this.cyclicBarrier = cyclicBarrier;
    }

}
