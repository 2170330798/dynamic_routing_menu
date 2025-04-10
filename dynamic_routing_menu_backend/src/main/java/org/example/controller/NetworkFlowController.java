package org.example.controller;

import org.example.entity.NetworkFlow;
import org.example.entity.OperatorSystemInfo;
import org.example.entity.PageResult;
import org.example.entity.ResponseResult;
import org.example.service.NetworkFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/network")
public class NetworkFlowController {

        @Autowired
        private NetworkFlowService networkFlowService;

        @GetMapping
        public ResponseResult<List<NetworkFlow>> getNetworkFlow() {
            List<NetworkFlow> info = networkFlowService.findNetworkFlow();
            //Integer code =  info.size() > 0 ? 200 : 500; //返回状态码
            //String message = info.size() > 0 ? "success" : "error";//响应消息
            Integer code = !info.isEmpty() ? 200 : 500; //返回状态码
            String message = !info.isEmpty() ? "success" : "error";//响应消息
            System.out.println(info);
            return new ResponseResult<>(code, message, info);
        }

        // 新增分页查询接口
        @GetMapping("/page")
        public ResponseResult<PageResult<NetworkFlow>> getNetworkFlowByPage(
                @RequestParam(defaultValue = "1") int pageNum,
                @RequestParam(defaultValue = "10") int pageSize) {

                PageResult<NetworkFlow> pageResult = networkFlowService.findNetworkFlowByPage(pageNum, pageSize);

                //Integer code = pageResult.getList().size() > 0 ? 200 : 500;
                //String message = pageResult.getList().size() > 0 ? "success" : "error";
                // 修改这里的检查逻辑
                Integer code = !pageResult.getList().isEmpty() ? 200 : 500;
                String message = !pageResult.getList().isEmpty() ? "success" : "error";
                return new ResponseResult<>(code, message, pageResult);
        }
}
