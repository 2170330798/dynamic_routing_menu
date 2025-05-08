//package org.example.controller;
//
//
//import org.apache.ibatis.annotations.Param;
//import org.example.entity.Menu;
//import org.example.entity.ResponseResult;
//import org.example.service.MenuService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.validation.BindingResult;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Objects;
//
//@RestController
//@RequestMapping("/menu")
//public class MenuController {
//    @Autowired
//    private MenuService menuService;
//
//    @GetMapping("/get")
//    public ResponseResult<List<Menu>> getMenuTree() {
//        List<Menu> menuTree = menuService.findMenuTree();
//        Integer code =  menuTree.size() > 0 ? 200 : 500; //返回状态码
//        String message = menuTree.size() > 0 ? "success" : "error";//响应消息
//        //System.out.println(menuTree);
//        return new ResponseResult<>(code, message, menuTree);
//    }
//
//
//    @PostMapping("/add")
//    public ResponseResult<Menu> createMenu(@RequestBody Menu menu, BindingResult bindingResult) {
//        try {
//            // 1. 参数校验
//            if (bindingResult.hasErrors()) {
//                return new ResponseResult<>(400, Objects.requireNonNull(bindingResult.getFieldError()).toString(), null);
//            }
//
//            // 2. 业务处理
//            boolean success = menuService.addMenu(menu);
//
//            // 3. 返回结果
//            return success ?
//                    new ResponseResult<>(200, "菜单创建成功", menu) :
//                    new ResponseResult<>(500, "菜单创建失败", null);
//
//        } catch (IllegalArgumentException e) {
//            return new ResponseResult<>(400, e.getMessage(), null);
//        } catch (Exception e) {
//            return new ResponseResult<>(500, "服务器内部错误: " + e.getMessage(), null);
//        }
//    }
//
//    @DeleteMapping("/delete")
//    public ResponseResult<Menu> deleteMenu(@RequestParam Long id) {
//        try {
//            boolean success = menuService.deleteMenu(id);
//            if (success) {
//                // 删除成功，返回204 No Content状态码
//                return new ResponseResult<>(200, "菜单删除成功", null);
//            } else {
//                // 菜单不存在或删除失败
//                return new ResponseResult<>(404, "菜单不存在或删除失败", null);
//            }
//        } catch (IllegalArgumentException e) {
//            // 参数验证失败，返回400 Bad Request
//            return new ResponseResult<>(400, e.getMessage(), null);
//        } catch (Exception e) {
//            // 其他异常
//            return new ResponseResult<>(500, "服务器内部错误: " + e.getMessage(), null);
//        }
//    }
//
//    // 更新菜单API
//    @PutMapping("/update")
//    public ResponseResult<Menu> updateMenu(@RequestBody Menu menu, BindingResult bindingResult) {
//        try {
//            // 1. 参数校验
//            if (bindingResult.hasErrors()) {
//                return new ResponseResult<>(400, Objects.requireNonNull(bindingResult.getFieldError()).toString(), null);
//            }
//
//            // 检查菜单ID是否为空
//            if (menu.getMenuId() == null) {
//                return new ResponseResult<>(401, "菜单ID不能为空", null);
//            }
////../views/log/LogView.vue
//            // 检查菜单ID是否存在
//            if (menu.getMenuId() <= 0) {
//                return new ResponseResult<>(402, "菜单ID必须大于0", null);
//            }
//
//            // 2. 业务处理 - 调用更新服务
//            boolean success = menuService.updateMenu(menu);
//
//            // 3. 返回结果
//            return success ?
//                    new ResponseResult<>(200, "菜单更新成功", menu) :
//                    new ResponseResult<>(500, "菜单更新失败", null);
//
//        } catch (IllegalArgumentException e) {
//            return new ResponseResult<>(400, e.getMessage(), null);
//        } catch (Exception e) {
//            return new ResponseResult<>(500, "服务器内部错误: " + e.getMessage(), null);
//        }
//    }
//}
