package org.example.controller;

import org.example.entity.OperatorSystemInfo;
import org.example.entity.ResponseResult;
import org.example.service.OperatorSystemInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/info")
public class OperatorSystemController {

     @Autowired
     private OperatorSystemInfoService operatorSystemInfoService;

     @GetMapping
     public ResponseResult getOperatorSystemInfo() {
         List<OperatorSystemInfo> info = operatorSystemInfoService.getOperatorSystemInfo();
         Integer code =  info.size() > 0 ? 200 : 500; //返回状态码
         String message = info.size() > 0 ? "success" : "error";//响应消息
         System.out.println(info);
         return new ResponseResult(code, message, info);
     }
}
