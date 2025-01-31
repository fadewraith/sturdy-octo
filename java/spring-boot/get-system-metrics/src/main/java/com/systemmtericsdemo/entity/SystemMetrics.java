package com.systemmtericsdemo.entity;

public class SystemMetrics {

    private long totalMemory;    // in bytes
    private long usedMemory;     // in bytes
    private long availableMemory; // in bytes
    private double cpuLoad;      // in percentage (0-1)
    private long totalDiskSpace; // in bytes
    private long usedDiskSpace;  // in bytes
    private double cpuTemperature; // in Celsius
    private double memoryPercentageUsed; // in percentage
    private double diskPercentageUsed; // in percentage

    public SystemMetrics(long totalMemory, long usedMemory, long availableMemory, double cpuLoad,
                         long totalDiskSpace, long usedDiskSpace, double cpuTemperature) {
        this.totalMemory = totalMemory;
        this.usedMemory = usedMemory;
        this.availableMemory = availableMemory;
        this.cpuLoad = cpuLoad;
        this.totalDiskSpace = totalDiskSpace;
        this.usedDiskSpace = usedDiskSpace;
        this.cpuTemperature = cpuTemperature;

        // Calculate memory and disk percentage used
        this.memoryPercentageUsed = ((double) usedMemory / totalMemory) * 100;
        this.diskPercentageUsed = ((double) usedDiskSpace / totalDiskSpace) * 100;
    }

    // Method to convert bytes to MB
    private long bytesToMB(long bytes) {
        return bytes / (1024 * 1024);
    }

    // Method to convert bytes to GB
    private long bytesToGB(long bytes) {
        return bytes / (1024 * 1024 * 1024);
    }

    // Getters for the fields
    public long getTotalMemoryMB() {
        return bytesToMB(totalMemory);
    }

    public long getUsedMemoryMB() {
        return bytesToMB(usedMemory);
    }

    public long getAvailableMemoryMB() {
        return bytesToMB(availableMemory);
    }

    public double getCpuLoadPercentage() {
        return cpuLoad * 100; // Convert from 0-1 scale to 0-100 percentage
    }

    public long getTotalDiskSpaceGB() {
        return bytesToGB(totalDiskSpace);
    }

    public long getUsedDiskSpaceGB() {
        return bytesToGB(usedDiskSpace);
    }

    public double getCpuTemperature() {
        return cpuTemperature;
    }

    public double getMemoryPercentageUsed() {
        return memoryPercentageUsed;
    }

    public double getDiskPercentageUsed() {
        return diskPercentageUsed;
    }
}
