package org.example.controller;

import org.example.entity.NetworkFlow;
import org.example.entity.PageResult;
import org.example.entity.ResponseResult;
import org.example.service.NetworkFlowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/network_flow")
public class NetworkFlowController {

    @Autowired
    private NetworkFlowService networkFlowService;

    // 新增分页查询接口
    @GetMapping  ("/page")
    public ResponseResult<PageResult<NetworkFlow>> getNetworkFlowByPage(@RequestParam(defaultValue = "1") int pageNumber,
                                                                        @RequestParam(defaultValue = "20") int pageSize
                                                                        )

    {
        PageResult<NetworkFlow> pageResult = networkFlowService.findNetworkFlowByPage(pageNumber, pageSize);

        Integer code = !pageResult.getList().isEmpty() ? 200 : 500;
        String message = !pageResult.getList().isEmpty() ? "success" : "error";
        //System.out.println("请求结果:"+code+'\n'+message+'\n'+pageResult);

        return new ResponseResult<>(code, message, pageResult);
    }
}
