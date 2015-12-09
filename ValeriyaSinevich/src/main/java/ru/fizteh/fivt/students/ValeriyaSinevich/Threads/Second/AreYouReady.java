package ru.fizteh.fivt.students.ValeriyaSinevich.Threads.Second;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

public class AreYouReady {
    public static void main(String[] args) {
        final CyclicBarrier barrier;
        int numberOfThreads = 0;
        if (args.length == 0) {
            throw new IllegalArgumentException("Wrong input");
        }
        numberOfThreads = Integer.parseInt(args[0]);
        if (numberOfThreads <= 0) {
            throw new IllegalArgumentException("Wrong input");
        }
        barrier = new CyclicBarrier(numberOfThreads + 1);
        List<Thread> answerers = new LinkedList<>();
        for (int i = 0; i < numberOfThreads; ++i) {
            Thread yesCaptain = new Thread(new YesCaptain(barrier));
            answerers.add(yesCaptain);
            answerers.get(i).start();
        }
        asking(numberOfThreads, barrier);
    }

    public static void asking(int numberOfThreads, CyclicBarrier barrier){
        while (!YesCaptain.getSuccess()) {
            YesCaptain.setSuccess(true);
            //barrier.reset();
            System.out.println("Are you ready?\n");
            try {
                barrier.await();
            }  catch (InterruptedException | BrokenBarrierException e) {
                System.out.println("cap interrupted while asking!");
                e.printStackTrace();
            }
            try {
                barrier.await();
            }  catch (InterruptedException | BrokenBarrierException e) {
                System.out.println("cap interrupted while asking!");
                e.printStackTrace();
            }
        }
        YesCaptain.setEnd(true);
        try {
            barrier.await();
        }  catch (InterruptedException | BrokenBarrierException e) {
            System.out.println("cap interrupted while asking!");
            e.printStackTrace();
        }
    }
}
