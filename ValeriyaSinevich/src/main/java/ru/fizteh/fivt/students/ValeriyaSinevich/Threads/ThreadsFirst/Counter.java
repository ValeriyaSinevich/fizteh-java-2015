package ru.fizteh.fivt.students.ValeriyaSinevich.Threads.ThreadsFirst;

import java.io.IOException;

/**
 * Created by root on 12/9/15.
 */
public class Counter {
    public static void main(String[] args) {
        int numberOfThreads = 0;
        try {
            if (args.length == 0) {
                throw new IOException();
            }
            numberOfThreads = Integer.parseInt(args[0]);
            if (numberOfThreads <= 0) {
                throw new IOException();
            }
        } catch (NumberFormatException | IOException e) {
            System.err.println("Wrong input");
            System.exit(1);
        }
        startCounting(numberOfThreads);
    }

    private static void startCounting(int numberOfThreads) {
        ThreadToCount.setNumberOfThreads(numberOfThreads);
        for (int i = 0; i < numberOfThreads; ++i) {
            new ThreadToCount(i).start();
        }
    }
}
