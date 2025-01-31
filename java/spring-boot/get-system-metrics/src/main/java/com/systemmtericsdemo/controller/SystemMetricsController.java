package com.systemmtericsdemo.controller;

import com.systemmtericsdemo.entity.SystemMetrics;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import oshi.SystemInfo;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.OperatingSystem;
import oshi.hardware.CentralProcessor;
import oshi.software.os.FileSystem;

@RestController
public class SystemMetricsController {

    private final SystemInfo systemInfo;

    public SystemMetricsController() {
        this.systemInfo = new SystemInfo();
    }

    @GetMapping("/metrics")
    public SystemMetrics getSystemMetrics() throws InterruptedException {
        HardwareAbstractionLayer hal = systemInfo.getHardware();
        OperatingSystem os = systemInfo.getOperatingSystem();

        // Fetch memory usage
        long totalMemory = hal.getMemory().getTotal();
        long availableMemory = hal.getMemory().getAvailable();
        long usedMemory = totalMemory - availableMemory;

        // Fetch CPU usage (correctly measure CPU load)
        CentralProcessor processor = hal.getProcessor();
        long[] prevTicks = processor.getSystemCpuLoadTicks(); // Capture CPU ticks before the interval
        Thread.sleep(1000);  // Wait for 1 second to simulate the interval
        double cpuLoad = processor.getSystemCpuLoadBetweenTicks(prevTicks); // Correct CPU load calculation

        // Fetch disk usage (use FileSystem to get disk space)
        long totalDiskSpace = 0;
        long usedDiskSpace = 0;
        for (var fs : os.getFileSystem().getFileStores()) {
            totalDiskSpace += fs.getTotalSpace();
            usedDiskSpace += fs.getTotalSpace() - fs.getUsableSpace();  // Calculate used space
        }

        // Fetch CPU temperature
        double cpuTemperature = hal.getSensors().getCpuTemperature(); // CPU temperature in Celsius

        // Return the metrics in a more readable format (MB/GB)
        return new SystemMetrics(totalMemory, usedMemory, availableMemory, cpuLoad, totalDiskSpace, usedDiskSpace, cpuTemperature);
    }
}
