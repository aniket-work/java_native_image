----------------  run as regular jdk -----------------
base_path = C:\samadhi\technology
worksapce_path= C:/samadhi/workspace

1. open cmd
2. to temporarily set given jdk
    set PATH=${base_path}\ava\jdk-17.0.8_windows-x64_bin\jdk-17.0.8\bin;%PATH%
    java --version
3. ${base_path}\apache\maven\bin\mvn clean package

4. run as regular jdk
    cd ${worksapce_path}/java/graalvm
    ${base_path}\ava\jdk-17.0.8_windows-x64_bin\jdk-17.0.8\bin\java -jar target\benchmarks-jvm-native-img.jar

----------------  run as native image -----------------
1. open native prompt ( search native on windows, you need to install VS code 2022)

2. cd ${worksapce_path}/java/graalvm/

3. build and run like a regular jvm ( and record perf) 
    ${base_path}\apache\maven\bin\mvn clean package exec:exec

4. build native image
    ${base_path}\apache\maven\bin\mvn package -Pnative
5. run native image
    target\target\benchmarks-jvm-native-img.exe

-- Info about what we going to test
java -cp C:/samadhi/workspace/java/graalvm/target/classes com.aniket.graalvm.perf.Info


--- for next part 
5. run native image ( and record perf)
    target\benchmark-binary-tree.exe -XX:+FlightRecorder -XX:StartFlightRecording="filename=recording.jfr"
