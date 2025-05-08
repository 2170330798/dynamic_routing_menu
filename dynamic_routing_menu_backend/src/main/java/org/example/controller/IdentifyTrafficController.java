//package org.example.controller;
//
//
//import org.example.entity.*;
//import org.example.service.IdentifyTrafficService;
//import org.example.service.NetworkFlowService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.util.Map;
//
//@RestController
//@RequestMapping("/traffic")
//public class IdentifyTrafficController {
//    @Autowired
//    private IdentifyTrafficService identifyTrafficService;
//
//    // 新增分页查询接口
//    @GetMapping("/page")
//    public ResponseResult<PageResult<IdentifyTraffic>> getNetworkFlowByPage(@RequestParam(defaultValue = "1") int pageNumber,
//                                                                        @RequestParam(defaultValue = "20") int pageSize
//    )
//
//    {
//        PageResult<IdentifyTraffic> pageResult = identifyTrafficService.findTrafficLogByPage(pageNumber, pageSize);
//
//        Integer code = !pageResult.getList().isEmpty() ? 200 : 500;
//        String message = !pageResult.getList().isEmpty() ? "success" : "error";
//        //System.out.println("请求结果:"+code+'\n'+message+'\n'+pageResult);
//
//        return new ResponseResult<>(code, message, pageResult);
//    }
//
//    @GetMapping("/statistic")
//    public ResponseResult<Map<Integer, Long>> statisticTrafficResponseResult() {
//
//        // 获取统计结果
//        Map<Integer, Long> labelCounts = identifyTrafficService.countLabelsByDatabase();
//        Integer code = !labelCounts.isEmpty() ? 200 : 500;
//        String message = !labelCounts.isEmpty() ? "success" : "error";
//        return  new ResponseResult<>(code, message, labelCounts);
//    }
//}
