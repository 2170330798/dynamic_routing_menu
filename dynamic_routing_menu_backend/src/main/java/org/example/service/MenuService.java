package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.entity.Menu;
import org.springframework.stereotype.Service;

import java.util.List;

@Service  // 必须加这个注解，否则 Spring 不会管理它
public interface MenuService extends IService<Menu> {

    /*查询菜单数据并返回菜单结构*/
    List<Menu> findMenuTree();
}
