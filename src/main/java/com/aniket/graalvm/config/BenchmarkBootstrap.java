package com.aniket.graalvm.config;

import com.aniket.graalvm.perf.NativeImageBenchmark;
import org.openjdk.jmh.Main;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.profile.JavaFlightRecorderProfiler;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class BenchmarkBootstrap  {

    private static final Logger logger = LoggerFactory.getLogger(BenchmarkBootstrap.class);

    public static void main(String[] args) throws Exception {
        runBenchmarks();
    }

    public static void runBenchmarks() throws RunnerException {
        String classpath = System.getProperty("java.class.path");
        System.out.println("Classpath: " + classpath);

        logger.info("calling runBenchmarks..");
        CustomJSONResultFormat customResultFormat = new CustomJSONResultFormat(System.out);

        Options options = new OptionsBuilder()
                .include(NativeImageBenchmark.class.getSimpleName())
                .resultFormat(ResultFormatType.JSON)
                .mode(Mode.AverageTime)
                .timeUnit(TimeUnit.NANOSECONDS)
                .warmupTime(TimeValue.seconds(1))
                .warmupIterations(1)
                .measurementTime(TimeValue.nanoseconds(1))
                .measurementIterations(5)
                .forks(0)
                .threads(1)
                .build();

        org.openjdk.jmh.runner.Runner runner = new org.openjdk.jmh.runner.Runner(options);
        runner.run();
    }
}
