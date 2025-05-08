//package org.example.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import oshi.SystemInfo;
//import oshi.hardware.CentralProcessor;
//import oshi.hardware.GlobalMemory;
//import oshi.hardware.NetworkIF;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Service;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Service
//public class SystemMonitorService  {
//
//    private final SystemInfo systemInfo = new SystemInfo();
//    private final CentralProcessor processor = systemInfo.getHardware().getProcessor();
//    private final GlobalMemory memory = systemInfo.getHardware().getMemory();
//    private final SimpMessagingTemplate messagingTemplate;
//    private long[] prevNetworkReadWrite = new long[2];
//    private long[] prevTicks = null;
//
//    @Autowired
//    public SystemMonitorService(SimpMessagingTemplate messagingTemplate) {
//        this.messagingTemplate = messagingTemplate;
//        // 初始化网络基准值
//        NetworkIF network = systemInfo.getHardware().getNetworkIFs().get(0);
//        prevNetworkReadWrite = new long[]{network.getBytesRecv(), network.getBytesSent()};
//        // 初始化CPU基准值
//        prevTicks = processor.getSystemCpuLoadTicks();
//    }
//
////    @Scheduled(fixedDelay = 1000)
////    public void sendTestMessage() {
////        Map<String, Object> metrics = new HashMap<>();
////        messagingTemplate.convertAndSend("/topic/system-metrics", metrics);
////
////    }
//    //@Scheduled(fixedRate = 9000) // 每秒发送一次数据
//    public void sendSystemMetrics() {
//        System.out.println("【DEBUG】定时任务执行"); // 添加调试输出
//        Map<String, Object> metrics = new HashMap<>();
//
//        // 1. CPU指标 - 使用正确的tick数组
//        long[] currentTicks = processor.getSystemCpuLoadTicks();
//        if (prevTicks != null && prevTicks.length == currentTicks.length) {
//            double cpuUsage = processor.getSystemCpuLoadBetweenTicks(prevTicks) * 100;
//            metrics.put("cpu", Map.of(
//                    "usage", String.format("%.2f", cpuUsage)
//            ));
//        }
//        prevTicks = currentTicks;
//
//        // 2. 内存指标
//        long totalMemory = memory.getTotal();
//        long availableMemory = memory.getAvailable();
//        metrics.put("memory", Map.of(
//                "total", totalMemory,
//                "used", totalMemory - availableMemory,
//                "free", availableMemory,
//                "usage", String.format("%.2f", (totalMemory - availableMemory) * 100.0 / totalMemory)
//        ));
//
//        // 3. 网络指标
//        NetworkIF network = systemInfo.getHardware().getNetworkIFs().get(0);
//        long[] currentNetworkReadWrite = {network.getBytesRecv(), network.getBytesSent()};
//        metrics.put("network", Map.of(
//                "uploadSpeed", String.format("%.2f", (currentNetworkReadWrite[1] - prevNetworkReadWrite[1]) / 1024.0),
//                "downloadSpeed", String.format("%.2f", (currentNetworkReadWrite[0] - prevNetworkReadWrite[0]) / 1024.0)
//        ));
//        prevNetworkReadWrite = currentNetworkReadWrite;
//        System.out.println("metrics: "+metrics);
//        // 通过WebSocket发送数据
//        messagingTemplate.convertAndSend("/topic/system-metrics", metrics);
//    }
//}