package org.example.controller;

import org.example.entity.Menu;
import org.example.entity.Model;
import org.example.entity.ResponseResult;
import org.example.service.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/model")
public class ModelController {

    @Autowired
    private ModelService modelService;

    @GetMapping("/get")
    public ResponseResult<List<Model>> getMenuTree() {
        List<Model> models = modelService.getModelList();
        Integer code =  models.size() > 0 ? 200 : 500; //返回状态码
        String message = models.size() > 0 ? "success" : "error";//响应消息
        System.out.println(models);
        return new ResponseResult<>(code, message, models);
    }

    @PostMapping("/add")
    public ResponseResult<Model> createMenu(@RequestBody Model model, BindingResult bindingResult) {
        try {
            // 1. 参数校验
            if (bindingResult.hasErrors()) {
                return new ResponseResult<>(400, Objects.requireNonNull(bindingResult.getFieldError()).toString(), null);
            }

            // 2. 业务处理
            boolean success = modelService.addModel(model);

            // 3. 返回结果
            return success ?
                    new ResponseResult<>(200, "模型创建成功", model) :
                    new ResponseResult<>(500, "模型创建失败", null);

        } catch (IllegalArgumentException e) {
            return new ResponseResult<>(400, e.getMessage(), model);
        } catch (Exception e) {
            return new ResponseResult<>(500, "服务器内部错误: " + e.getMessage(), null);
        }
    }

    @DeleteMapping("/delete")
    public ResponseResult<Model> deleteModel(@RequestParam Long id) {
        try {
            boolean success = modelService.deleteModel(id);
            if (success) {
                // 删除成功，返回204 No Content状态码
                return new ResponseResult<>(200, "模型删除成功", null);
            } else {
                // 菜单不存在或删除失败
                return new ResponseResult<>(404, "模型不存在或删除失败", null);
            }
        } catch (IllegalArgumentException e) {
            // 参数验证失败，返回400 Bad Request
            return new ResponseResult<>(400, e.getMessage(), null);
        } catch (Exception e) {
            // 其他异常
            return new ResponseResult<>(500, "服务器内部错误: " + e.getMessage(), null);
        }
    }

    // 更新菜单API
    @PutMapping("/update")
    public ResponseResult<Model> updateMenu(@RequestBody Model model, BindingResult bindingResult) {
        try {
            // 1. 参数校验
            if (bindingResult.hasErrors()) {
                return new ResponseResult<>(400, Objects.requireNonNull(bindingResult.getFieldError()).toString(), null);
            }

            // 检查菜单ID是否为空
            if (model.getModelId() == null) {
                return new ResponseResult<>(401, "模型ID不能为空", null);
            }

            // 2. 业务处理 - 调用更新服务
            boolean success = modelService.updateModel(model);

            // 3. 返回结果
            return success ?
                    new ResponseResult<>(200, "模型更新成功", model) :
                    new ResponseResult<>(500, "模型更新失败", null);

        } catch (IllegalArgumentException e) {
            return new ResponseResult<>(400, e.getMessage(), null);
        } catch (Exception e) {
            return new ResponseResult<>(500, "服务器内部错误: " + e.getMessage(), null);
        }
    }

}
