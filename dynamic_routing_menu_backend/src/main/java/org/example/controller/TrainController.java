package org.example.controller;


import com.sun.jna.platform.mac.SystemB;
import org.example.entity.ResponseResult;
import org.example.entity.Train;
import org.example.service.ModelService;
import org.example.service.TrainService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Objects;

@RestController
@RequestMapping("/train")
public class TrainController {

    @Autowired
    private TrainService trainService;

    @PostMapping("/start")
    public ResponseResult<Train> trainModel(@RequestBody Train train, BindingResult bindingResult) throws IOException {


        try {
            // 1. 参数校验
            if (bindingResult.hasErrors()) {
                return new ResponseResult<>(400, Objects.requireNonNull(bindingResult.getFieldError()).toString(), null);
            }

            // 2. 业务处理
            boolean success = trainService.trainModel(train);
            System.out.println(success);
            // 3. 返回结果
            return success ? new ResponseResult<>(200, "模型训练成功", train) : new ResponseResult<>(500, "模型训练失败", null);

        } catch (IllegalArgumentException e) {
            return new ResponseResult<>(401, e.getMessage(), train);
        } catch (Exception e) {
            return new ResponseResult<>(501, "服务器内部错误: " + e.getMessage(), null);
        }

    }

}
