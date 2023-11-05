import psutil
import os
import matplotlib.pyplot as plt
import time

target_pid = 19356

# Initialize lists to store data
time_data = []
cpu_percent_data = []
memory_usage_data = []

# Record the initial time
start_time = time.time()

# Main monitoring loop
while True:
    try:
        process = psutil.Process(target_pid)

        # Get CPU usage over a time window (e.g., 1 second)
        cpu_percent = process.cpu_percent(interval=1)

        # Get memory usage in megabytes (MB)
        memory_info = process.memory_info()
        memory_mb = memory_info.rss / (1024 * 1024)

        # Calculate the elapsed time since the start
        elapsed_time = time.time() - start_time

        time_data.append(elapsed_time)
        cpu_percent_data.append(cpu_percent)
        memory_usage_data.append(memory_mb)

        # Sleep for a short interval (e.g., 1 second) before the next measurement
        time.sleep(1)

    except psutil.NoSuchProcess:
        # The process has exited, so exit the monitoring loop
        break

# Create two separate plots
plt.figure(figsize=(10, 6))

# Plot for CPU Usage
plt.subplot(2, 1, 1)
plt.plot(time_data, cpu_percent_data, label="CPU Usage")
plt.xlabel("Time (seconds)")
plt.ylabel("Percentage")
plt.ylim(0, 100)  # Set y-axis limit to 0-100%
plt.legend()
plt.grid()
plt.title("CPU Usage Over Time")

# Plot for Memory Usage
plt.subplot(2, 1, 2)
plt.plot(time_data, memory_usage_data, label="Memory Usage")
plt.xlabel("Time (seconds)")
plt.ylabel("Memory (MB)")
plt.legend()
plt.grid()
plt.title("Memory Usage Over Time")

plt.tight_layout()

plt.show()
