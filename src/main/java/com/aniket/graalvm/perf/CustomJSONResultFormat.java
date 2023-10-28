package com.aniket.graalvm.perf;

import org.openjdk.jmh.results.RunResult;
import org.openjdk.jmh.results.format.ResultFormat;

import java.io.PrintStream;
import java.util.Collection;

public class CustomJSONResultFormat implements ResultFormat {
    private final PrintStream out;

    public CustomJSONResultFormat(PrintStream out) {
        this.out = out;
    }

    @Override
    public void writeOut(Collection<RunResult> results) {
        // Implement your custom JSON formatting logic here.
        // You can format the results in your desired JSON format and print it to the console.
        // For simplicity, this example does not include the full JSON format.
        out.println("{");
        // Format and print the JSON content here
        out.println("}");
    }
}

