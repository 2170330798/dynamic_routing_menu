//package org.example.controller;
//
//import org.example.service.SystemMonitorService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/system")
//public class SystemInfoController {
//
//    @Autowired
//    private SystemMonitorService systemMonitorService;  // 直接注入服务
//
//    @PostMapping("/info")
//    public ResponseEntity<String> getSystemMonitorService() {
//        systemMonitorService.sendSystemMetrics();
//        //System.out.println("start...");
//        return ResponseEntity.ok("started");
//    }
//}
