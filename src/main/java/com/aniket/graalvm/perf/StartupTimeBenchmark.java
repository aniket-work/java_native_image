package com.aniket.graalvm.perf;

import org.openjdk.jmh.annotations.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Warmup(iterations = 3, time = 50, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 3, time = 50, timeUnit = TimeUnit.SECONDS)
@State(Scope.Benchmark)
public class StartupTimeBenchmark {

    private String configuration;

    @Benchmark
    public void measureStartupTime() {
        // Simulate startup operations by reading a configuration file
        loadConfiguration();
        // You can add other initialization tasks here
    }

    private void loadConfiguration() {
        // Simulate loading a configuration file during startup
        try (BufferedReader reader = new BufferedReader(new FileReader("config.txt"))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            configuration = sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        org.openjdk.jmh.Main.main(args);
    }
}
