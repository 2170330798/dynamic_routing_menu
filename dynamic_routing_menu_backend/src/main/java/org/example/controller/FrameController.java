package org.example.controller;

import org.example.entity.ResponseResult;
import org.example.service.FrameService;
import org.example.entity.Frame;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/frame")
public class FrameController {


    @Autowired
    private FrameService frameService;

    @GetMapping("/get")
    public ResponseResult<List<Frame>> getFrameList() {
        List<Frame> frames= frameService.getFrameList();
        Integer code = frames.size() > 0 ? 200 : 500; //返回状态码
        String message = frames.size() > 0 ? "success" : "error";//响应消息
        System.out.println(frames);
        return new ResponseResult<>(code, message, frames);
    }

    @PostMapping("/add")
    public ResponseResult<Frame> createFrame(@RequestBody Frame frame, BindingResult bindingResult) {
        try {
            // 1. 参数校验
            if (bindingResult.hasErrors()) {
                return new ResponseResult<>(400, Objects.requireNonNull(bindingResult.getFieldError()).toString(), null);
            }

            // 2. 业务处理
            boolean success = frameService.addFrame(frame);

            // 3. 返回结果
            return success ?
                    new ResponseResult<>(200, "框架创建成功", frame) :
                    new ResponseResult<>(500, "框架创建失败", null);

        } catch (IllegalArgumentException e) {
            return new ResponseResult<>(400, e.getMessage(), frame);
        } catch (Exception e) {
            return new ResponseResult<>(500, "服务器内部错误: " + e.getMessage(), null);
        }
    }

    @DeleteMapping("/delete")
    public ResponseResult<Frame> deleteFrame(@RequestParam Long id) {
        try {
            boolean success = frameService.deleteFrame(id);
            if (success) {
                // 删除成功，返回204 No Content状态码
                return new ResponseResult<>(200, "框架删除成功", null);
            } else {
                // 菜单不存在或删除失败
                return new ResponseResult<>(404, "框架不存在或删除失败", null);
            }
        } catch (IllegalArgumentException e) {
            // 参数验证失败，返回400 Bad Request
            return new ResponseResult<>(400, e.getMessage(), null);
        } catch (Exception e) {
            // 其他异常
            return new ResponseResult<>(500, "服务器内部错误: " + e.getMessage(), null);
        }
    }

    @PutMapping("/update")
    public ResponseResult<Frame> updateMenu(@RequestBody Frame frame, BindingResult bindingResult) {
        try {
            // 1. 参数校验
            if (bindingResult.hasErrors()) {
                return new ResponseResult<>(400, Objects.requireNonNull(bindingResult.getFieldError()).toString(), null);
            }

            // 检查菜单ID是否为空
            if (frame.getFrameId() == null) {
                return new ResponseResult<>(401, "框架ID不能为空", null);
            }

            // 2. 业务处理 - 调用更新服务
            boolean success = frameService.updateFrame(frame);

            // 3. 返回结果
            return success ?
                    new ResponseResult<>(200, "框架更新成功", frame) :
                    new ResponseResult<>(500, "框架更新失败", null);

        } catch (IllegalArgumentException e) {
            return new ResponseResult<>(400, e.getMessage(), null);
        } catch (Exception e) {
            return new ResponseResult<>(500, "服务器内部错误: " + e.getMessage(), null);
        }
    }
}
