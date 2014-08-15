package com.trevore.parcist.sample;

import android.util.Log;
import android.widget.TextView;

import junit.framework.TestResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Trevor on 8/14/2014.
 */
public class BenchmarkRunner {

    private List<User> testObjects;
    private int testSamples;

    public BenchmarkRunner(int testSamples, int listSize) {
        this.testSamples = testSamples;

        //Prepare data
        testObjects = new ArrayList<User>();
        for(int x = 0; x < listSize; x++) {
            testObjects.add(new User("Trevor", 23, "trevor-e", "trevore.com"));
        }
    }

    public void benchmark(TextView output, Benchmark benchmark, Benchmark benchmark2) {
        Log.d("benchmark", "Starting benchmark " + benchmark.getClass().getSimpleName());
        BenchmarkResult result1 = startBenchmark(output, benchmark);
        Log.d("benchmark", benchmark.getClass().getSimpleName() + " benchmark finished.");
        Log.d("benchmark", "Starting benchmark " + benchmark2.getClass().getSimpleName());
        BenchmarkResult result2 = startBenchmark(output, benchmark2);
        Log.d("benchmark", benchmark2.getClass().getSimpleName() + " benchmark finished.");
        Log.d("benchmark", "Speed ratio: " + benchmark2.getClass().getSimpleName() + " : " + benchmark.getClass().getSimpleName() + " " + String.format("%.2f",((double) result2.getTotalTime() / (double) result1.getTotalTime())));
        Log.d("benchmark", "Size ratio: " + benchmark2.getClass().getSimpleName() + " : " + benchmark.getClass().getSimpleName() + " " + String.format("%.2f",((double) result2.getSizeInBytes() / (double) result1.getSizeInBytes())));
    }

    private BenchmarkResult startBenchmark(TextView output, Benchmark benchmark) {
        //Perform tests
        long totalTime = 0;
        int size = 0;

        for(int x = 0; x < testSamples; x++) {
            long startTime = System.currentTimeMillis();
            size = benchmark.run(testObjects);
            long endTime = System.currentTimeMillis();
            totalTime += (endTime - startTime);
        }
        Log.d("benchmark", "Average " + benchmark.getClass().getSimpleName() + " time (ms): " + (totalTime / (double) testSamples));
        Log.d("benchmark", "Size " + benchmark.getClass().getSimpleName() + " time (b): " + size);
        return new BenchmarkResult(totalTime, size);
    }
}
