package org.example.controller;


import org.example.entity.Menu;
import org.example.entity.ResponseResult;
import org.example.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/menu")
public class MenuController {
    @Autowired
    private MenuService menuService;

    @GetMapping()
    public ResponseResult<List<Menu>> getMenuTree() {
        List<Menu> menuTree = menuService.findMenuTree();
        Integer code =  menuTree.size() > 0 ? 200 : 500; //返回状态码
        String message = menuTree.size() > 0 ? "success" : "error";//响应消息
        System.out.println(menuTree);
        return new ResponseResult<>(code, message, menuTree);
    }
}
