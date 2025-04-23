package org.example.controller;


import org.example.entity.ResponseResult;
import org.example.entity.User;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;


@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/list")
    public ResponseResult<List<User>> getUsersByPage() {
        List<User> users = userService.findUsers();
        Integer code = !users.isEmpty() ? 200 : 500;
        String message = !users.isEmpty() ? "success" : "error";
        return new ResponseResult<>(code, message, users);
    }

    @PostMapping("/login")
    public ResponseResult<User> login(@RequestBody User user) {
        System.out.println(user.getUserName() + '\n' + user.getPassword());
        boolean success = userService.login(user);
        if (success) {
            User user1 = userService.findUserById(user.getUserName());
            System.out.println(user1.toString());
            return new ResponseResult<>(200, "登入成功", user1);
        }
        return new ResponseResult<>(500, "登入失败", null);
    }

    @PostMapping("/add")
    public ResponseResult<User> createUser(@RequestBody User user, BindingResult bindingResult) {
        try {
            // 1. 参数校验
            if (bindingResult.hasErrors()) {
                return new ResponseResult<>(400, Objects.requireNonNull(bindingResult.getFieldError()).toString(), null);
            }

            // 2. 业务处理
            boolean success = userService.addUser(user);

            // 3. 返回结果
            return success ?
                    new ResponseResult<>(200, "用户创建成功", user) :
                    new ResponseResult<>(500, "用户创建失败", null);

        } catch (IllegalArgumentException e) {
            return new ResponseResult<>(400, e.getMessage(), user);
        } catch (Exception e) {
            return new ResponseResult<>(500, "服务器内部错误: " + e.getMessage(), null);
        }
    }



    @DeleteMapping("/delete")
    public ResponseResult<User> deleteModel(@RequestParam String id) {
        try {
            boolean success = userService.deleteUser(id);
            if (success) {
                // 删除成功，返回204 No Content状态码
                return new ResponseResult<>(200, "用户删除成功", null);
            } else {
                // 菜单不存在或删除失败
                return new ResponseResult<>(400, "用户不存在或删除失败", null);
            }
        } catch (IllegalArgumentException e) {
            // 参数验证失败，返回401 Bad Request
            return new ResponseResult<>(401, e.getMessage(), null);
        } catch (Exception e) {
            // 其他异常
            return new ResponseResult<>(500, "服务器内部错误: " + e.getMessage(), null);
        }
    }

    // 更新菜单API
    @PutMapping("/update")
    public ResponseResult<User> updateUser(@RequestBody User user, BindingResult bindingResult) {
        try {
            // 1. 参数校验
            if (bindingResult.hasErrors()) {
                return new ResponseResult<>(400, Objects.requireNonNull(bindingResult.getFieldError()).toString(), null);
            }

            // 2. 业务处理 - 调用更新服务
            boolean success = userService.updateUser(user);

            // 3. 返回结果
            return success ?
                    new ResponseResult<>(200, "用户信息更新成功", user) :
                    new ResponseResult<>(500, "用户信息更新失败", null);

        } catch (IllegalArgumentException e) {
            return new ResponseResult<>(402, e.getMessage(), null);
        } catch (Exception e) {
            return new ResponseResult<>(500, "服务器内部错误: " + e.getMessage(), null);
        }
    }
}
