package com.aniket.graalvm.perf;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.infra.Blackhole;

import java.util.concurrent.*;

@Warmup(iterations = 1, time = 50, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 1, time = 50, timeUnit = TimeUnit.SECONDS)
@State(Scope.Benchmark)
public class NativeImageBenchmark {

    private static final int MIN_DEPTH = 4;

    @Param("14")
    private static int settingParams;

    @Benchmark
    @Fork(0)
    public static void test(Blackhole blackhole) throws Exception {

        final int maxDepth = settingParams < (MIN_DEPTH + 2) ? MIN_DEPTH + 2 : settingParams;
        final int stretchDepth = maxDepth + 1;

        blackhole.consume("stretch tree of depth " + stretchDepth + "\t check: "
                + bottomUpTree(stretchDepth).itemCheck());

        final TreeNode longLivedTree = bottomUpTree(maxDepth);

        final String[] results = new String[(maxDepth - MIN_DEPTH) / 2 + 1];

        final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

        for (int d = MIN_DEPTH; d <= maxDepth; d += 2) {
            final int depth = d;
            executorService.execute(() -> {
                int check = 0;

                final int iterations = 1 << (maxDepth - depth + MIN_DEPTH);
                for (int i = 1; i <= iterations; ++i) {
                    final TreeNode treeNode1 = bottomUpTree(depth);
                    check += treeNode1.itemCheck();
                }
                results[(depth - MIN_DEPTH) / 2]
                        = iterations + "\t trees of depth " + depth + "\t check: " + check;
            });
        }

        executorService.shutdown();
        executorService.awaitTermination(120L, TimeUnit.SECONDS);

        for (final String str : results) {
            blackhole.consume(str);
        }

        blackhole.consume("long lived tree of depth " + maxDepth
                + "\t check: " + longLivedTree.itemCheck());
    }

    @Fork(0)
    @Benchmark
    public static void measureSuperIntenseCPUMemoryOperation(Blackhole blackhole) throws Exception {
        int numThreads = 10; // Increase the number of threads for more CPU usage
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

        System.out.println("Startup time: " + duration + " ms");
    }

    private static TreeNode bottomUpTree(final int depth) {
        if (0 < depth) {
            return new TreeNode(bottomUpTree(depth - 1), bottomUpTree(depth - 1));
        }
        return new TreeNode();
    }

    private static final class TreeNode {

        private final TreeNode left;
        private final TreeNode right;

        private TreeNode(final TreeNode left, final TreeNode right) {
            this.left = left;
            this.right = right;
        }

        private TreeNode() {
            this(null, null);
        }

        private int itemCheck() {
            // if necessary deallocate here
            if (null == left) {
                return 1;
            }
            return 1 + left.itemCheck() + right.itemCheck();
        }
    }
}