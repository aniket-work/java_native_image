package com.aniket.graalvm.perf;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import javax.swing.tree.TreeNode;
import java.util.concurrent.*;

@Warmup(iterations = 3, time = 50, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 3, time = 50, timeUnit = TimeUnit.SECONDS)
@State(Scope.Benchmark)
public class NativeImageBenchmark {

    private static final int MIN_DEPTH = 4;

    @Param("14")
    private static int settingParams;

    @Benchmark
    @Fork(0)
    public static void performMemoryIntensiveOperation(Blackhole blackhole) throws Exception {

        int iterations = 1000000;
        double[] data = new double[iterations];

        // Perform some memory-intensive calculations
        for (int i = 0; i < iterations; i++) {
            data[i] = Math.sin(i) * Math.cos(i);
        }
    }

    @Fork(0)
    @Benchmark
    public static void measureSuperIntenseCPUMemoryOperation(Blackhole blackhole) throws Exception {
        int numThreads = 4; // Increase the number of threads for more CPU usage
        int arraySize = 1000000; // Increase the array size for more memory usage

        ExecutorService executor = Executors.newFixedThreadPool(numThreads);
        Future<?>[] futures = new Future[numThreads];

        for (int t = 0; t < numThreads; t++) {
            futures[t] = executor.submit(() -> {
                long[] data = new long[arraySize];
                for (int i = 0; i < arraySize; i++) {
                    data[i] = ThreadLocalRandom.current().nextLong();
                }

                // Perform an extremely CPU-intensive operation
                long result = 0;
                for (int i = 0; i < arraySize; i++) {
                    for (int j = 0; j < 1000; j++) {
                        result += data[i] * j;
                    }
                }
            });
        }

        for (int t = 0; t < numThreads; t++) {
            futures[t].get();
        }
        executor.shutdown();
    }

    @Fork(0)
    @Benchmark
    public static void startupTimeComparison(Blackhole blackhole) throws Exception {
        long startTime = System.nanoTime();
        for (int i = 0; i < 1000000; i++) {
            // Simulate some computation
            Math.pow(Math.random(), 2);
        }
        long endTime = System.nanoTime();
        long duration = (endTime - startTime) / 1_000_000; // milliseconds

        //System.out.println("Startup time: " + duration + " ms");
    }
}