import psutil
import os
import matplotlib.pyplot as plt
import time

target_pid = 12728

# Initialize lists to store data
time_data = []
process_cpu_percent_data = []
system_cpu_percent_data = []
memory_usage_data = []

# Record the initial time
start_time = time.time()

# Main monitoring loop
while True:
    try:
        process = psutil.Process(target_pid)

        # Get CPU usage over a time window (e.g., 1 second) for the process
        process_cpu_percent = process.cpu_percent(interval=1)

        # Get CPU usage for the system and calculate the average
        system_cpu_percent = sum(psutil.cpu_percent(interval=1, percpu=True)) / psutil.cpu_count()

        # Get memory usage in megabytes (MB) for the process
        memory_info = process.memory_info()
        memory_mb = memory_info.rss / (1024 * 1024)

        # Calculate the elapsed time since the start
        elapsed_time = time.time() - start_time

        time_data.append(elapsed_time)
        process_cpu_percent_data.append(process_cpu_percent)
        system_cpu_percent_data.append(system_cpu_percent)
        memory_usage_data.append(memory_mb)

        # Sleep for a short interval (e.g., 1 second) before the next measurement
        time.sleep(1)

    except psutil.NoSuchProcess:
        # The process has exited, so exit the monitoring loop
        break

# Create separate plots for process CPU usage, system CPU usage, and memory usage
plt.figure(figsize=(10, 8))

# Plot for Process CPU Usage
plt.subplot(3, 1, 1)
plt.plot(time_data, process_cpu_percent_data, label="Process CPU Usage")
plt.xlabel("Time (seconds)")
plt.ylabel("Percentage")
plt.ylim(0, 100)  # Set y-axis limit to 0-100%
plt.legend()
plt.grid()
plt.title("Process CPU Usage Over Time")

# Plot for System CPU Usage
plt.subplot(3, 1, 2)
plt.plot(time_data, system_cpu_percent_data, label="System CPU Usage")
plt.xlabel("Time (seconds)")
plt.ylabel("Percentage")
plt.ylim(0, 100)  # Set y-axis limit to 0-100%
plt.legend()
plt.grid()
plt.title("System CPU Usage Over Time")

# Plot for Memory Usage
plt.subplot(3, 1, 3)
plt.plot(time_data, memory_usage_data, label="Memory Usage")
plt.xlabel("Time (seconds)")
plt.ylabel("Memory (MB)")
plt.legend()
plt.grid()
plt.title("Memory Usage Over Time")

plt.tight_layout()

plt.show()
