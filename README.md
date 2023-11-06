# 🚀 Exploring Low Latency with JMH

"Low latency" – it's a phrase that might not get everyone excited, but it sure has its moments. It's like that one dish on the menu that some people can't get enough of, and I'm right there with them. We all have our quirks, right? In the world of software performance, it's not just about what we like; it's about the quest for smoother and faster experiences. That's why we're delving deep into the world of performance in Part 3, with a special focus on the trusty tool called JMH.

If you've been following our expedition from the very beginning, you've already embarked on an exhilarating adventure that has taken you from the introductory shores of GraalVM in Part 1 to the depths of JVM's inner workings in Part 2. Now, as we enter Part 3, prepare yourselves for a deep dive into the heart of performance, where we will explore why JMH is a crucial tool if you truly want to measure real performance.

## 🛠️ Getting Started

### Build your project with Maven

To run your project as a regular JDK, open your command prompt and execute the following commands:

```sh
# Open a native prompt (search "native" on Windows, you need to install Visual Studio 2022).
cd <your_workspace_path>/java/graalvm/

# Build and run like a regular JVM (and record performance):
set PATH=<your_base_path>\java\jdk-17.0.8_windows-x64_bin\jdk-17.0.8\bin;%PATH%
java --version
<your_base_path>\apache\maven\bin\mvn clean package

# Run as a regular JDK:
cd ${workspace_path}/java/graalvm
${base_path}/java/jdk-17.0.8_windows-x64_bin/jdk-17.0.8/bin/java -jar target/benchmarks-jvm-native-img.jar
```
Build and run as a native image
```sh
# Open a native prompt (search "native" on Windows, you need to install Visual Studio 2022).
cd <your_workspace_path>/java/graalvm/

# Build and run like a regular JVM (and record performance):
<your_base_path>\apache\maven\bin\mvn clean package exec:exec

# Build the native image:
<your_base_path>\apache\maven\bin\mvn package -Pnative

# Run the native image:
target/benchmark-binary-tree.exe
```

Additional Information
To learn more about what we're going to test, execute:
```shell
java -cp <your_workspace_path>/java/graalvm/target/classes com.aniket.graalvm.perf.Info
```

