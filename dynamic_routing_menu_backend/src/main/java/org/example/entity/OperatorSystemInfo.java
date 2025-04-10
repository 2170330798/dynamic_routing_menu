package org.example.entity;

import com.baomidou.mybatisplus.annotation.TableName;

@TableName(value = "")
public class OperatorSystemInfo {

    private float cpuUsage;
    private float networkUsage;
    private float memoryUsage;

    public float getCpuUsage() {
        return cpuUsage;
    }

    public void setCpuUsage(float cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    public float getNetworkUsage() {
        return networkUsage;
    }

    public void setNetworkUsage(float networkUsage) {
        this.networkUsage = networkUsage;
    }

    public float getMemoryUsage() {
        return memoryUsage;
    }

    public void setMemoryUsage(float memoryUsage) {
        this.memoryUsage = memoryUsage;
    }

    @Override
    public String toString() {
        return "OperatorSystemInfo{" +
                "cpuUsage=" + cpuUsage +
                ", networkUsage=" + networkUsage +
                ", memoryUsage=" + memoryUsage +
                '}';
    }
}
