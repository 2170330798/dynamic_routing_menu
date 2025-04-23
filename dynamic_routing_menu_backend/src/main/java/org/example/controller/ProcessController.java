package org.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ProcessController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostMapping("/api/long-process")
    public ResponseEntity<String> startLongProcess() {
        // 模拟长时间处理
        new Thread(() -> {
            for (int i = 0; i <= 100; i++) {
                try {
                    Thread.sleep(100); // 模拟处理耗时
                    // 发送进度到前端
                    messagingTemplate.convertAndSend("/topic/progress",
                            Map.of("progress", i));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();

        return ResponseEntity.ok("Process started");
    }
}