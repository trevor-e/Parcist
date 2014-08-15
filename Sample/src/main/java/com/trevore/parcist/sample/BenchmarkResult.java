package com.trevore.parcist.sample;

/**
 * Created by Trevor on 8/15/2014.
 */
public class BenchmarkResult {
    private long totalTime;
    private int sizeInBytes;

    public BenchmarkResult(long totalTime, int sizeInBytes) {
        this.totalTime = totalTime;
        this.sizeInBytes = sizeInBytes;
    }

    public long getTotalTime() {
        return totalTime;
    }

    public int getSizeInBytes() {
        return sizeInBytes;
    }
}
