package com.test.jersey.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by shouguoli on 7/10/16.
 */
public class MyStats<T extends java.lang.Comparable> {

    private static final Logger logger = LoggerFactory.getLogger(MyStats.class);

    long samplingPeriodMs;
    ConcurrentLinkedQueue<T> queue;

    public MyStats(long samplingPeriodMs) {
        this.samplingPeriodMs = samplingPeriodMs;
        swapQueue();

        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                printStats();
            }
        }, samplingPeriodMs, samplingPeriodMs, TimeUnit.MILLISECONDS);
    }

    public void addVal(T val) {
        queue.add(val);
    }

    // swap a new empty queue and return old one
    synchronized ConcurrentLinkedQueue<T> swapQueue() {
        ConcurrentLinkedQueue<T> old = queue;
        queue = new ConcurrentLinkedQueue<T>();
        return old;
    }

    void printStats() {
        ConcurrentLinkedQueue<T> q = swapQueue();
        // want total, qps, max, min, p90, max
        ArrayList<T> l = new ArrayList<T>(q);
        Collections.sort(l);
        logger.info(String.format("STATS:  total:%d QPS:%d min:%d p90:%d max:%d",
                l.size(), l.size()/(samplingPeriodMs /1000), getPercentile(l, 0), getPercentile(l, 90), getPercentile(l, 100)));
    }

    T getPercentile(ArrayList<T> sortedList, int percentile) {
        if (sortedList.size() == 0) {
            return null;
        }

        if (percentile < 1) {
            return sortedList.get(0);
        }

        if (percentile > 100) {
            return sortedList.get(sortedList.size()-1);
        }

        return sortedList.get(sortedList.size()*percentile/100 - 1);
    }

}
