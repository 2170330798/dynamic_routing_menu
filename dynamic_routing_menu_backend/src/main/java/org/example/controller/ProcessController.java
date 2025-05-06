package org.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/progress")
public class ProcessController {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostMapping("/api/start_process")
    public ResponseEntity<String> startProcess() {
        new Thread(() -> {
            for (int i = 0; i <= 100; i++) {
                try {
                    Thread.sleep(100);
                    // 发送包含进度信息的JSON
                    messagingTemplate.convertAndSend("/topic/progress",
                            Map.of("progress", i, "timestamp", System.currentTimeMillis()));
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }).start();
        return ResponseEntity.ok("Process started");
    }
}