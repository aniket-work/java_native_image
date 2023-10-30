import psutil
import os
import matplotlib.pyplot as plt
import time

target_pid = 27332

# Initialize lists to store data
time_data = []
cpu_percent_data = []
memory_usage_data = []

# Main monitoring loop
while True:
    try:
        process = psutil.Process(target_pid)
        # Get CPU usage as a percentage
        cpu_percent = process.cpu_percent()
        # Get memory usage in bytes
        memory_info = process.memory_info()

        time_data.append(time.time())
        cpu_percent_data.append(cpu_percent)
        memory_usage_data.append(memory_info.rss)

        # Sleep for a short interval (e.g., 1 second) before the next measurement
        time.sleep(1)

    except psutil.NoSuchProcess:
        # The process has exited, so exit the monitoring loop
        break

# Plot the data
plt.figure(figsize=(10, 6))
plt.plot(time_data, cpu_percent_data, label="CPU Usage")
plt.plot(time_data, memory_usage_data, label="Memory Usage")
plt.xlabel("Time (seconds)")
plt.ylabel("Percentage / Bytes")
plt.legend()
plt.grid()
plt.title("Process Resource Usage Over Time")
plt.show()
