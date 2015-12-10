package ru.fizteh.fivt.students.ValeriyaSinevich.Threads.Third;

import org.junit.Test;
import sun.awt.image.ImageWatched;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.*;


public class ThreadSafeQueueTest {

    @Test
    public static void takeTest()
    {
        ThreadSafeQueue <String> q = new ThreadSafeQueue<String>(0);

        Runnable syncThread = new Runnable() {
            private Random rand = new Random();
            @Override
            public void run() {
                int takeN = rand.nextInt(500);
                try {
                    q.take(takeN, 100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        List<Thread> ts = new LinkedList<>();

        for (int i = 0; i < 100; ++i) {
            Thread t = new Thread(syncThread, Integer.toString(i));
            ts.add(t);
        }
        for (int i = 0; i < 100; ++i) {
            ts.get(i).start();
        }
    }

    @Test
    public static void offerTest()
    {
        ThreadSafeQueue <String> q = new ThreadSafeQueue<String>(0);

        Runnable syncThread = new Runnable() {
            private List<String> e = new LinkedList<>();
            private List<String> al = new LinkedList<>();
            private Random rand = new Random();

            @Override
            public void run() {
                al.add("a");
                al.add("b");
                al.add("c");
                al.add("d");

                for (int i = 0; i < 10000; ++i) {
                    int k = rand.nextInt(3);
                    String add = al.get(k);
                    e.add(add);
                }

                try {
                    q.offer(e, 100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        List<Thread> ts = new LinkedList<>();

        for (int i = 0; i < 100; ++i) {
            Thread t = new Thread(syncThread, Integer.toString(i));
            ts.add(t);
        }
        for (int i = 0; i < 100; ++i) {
           ts.get(i).start();
        }
    }
}