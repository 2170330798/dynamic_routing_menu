package org.example.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.entity.Menu;
import org.example.mapper.MenuMapper;
import org.example.service.MenuService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

      @Autowired
      private MenuMapper menuMapper;

       @Override
       public List<Menu> findMenuTree(){
              //从数据查询一维向量数据集合
              List <Menu> menus = menuMapper.selectList(null);

              //将一维数据库集合转换成菜单树集合
              List <Menu> menuTree = converyMenuTree(menus);
              return  menuTree;
       }

       public List<Menu> converyMenuTree(List<Menu> menus){
            List<Menu> menuList = menus.stream().filter(menu -> menu.getParentId() == -1).collect(Collectors.toList());
            menuList.forEach(menu -> {
                menu = findChildren(menu, menus);
            });

            return  menuList;
       }


       private Menu findChildren(Menu menu, List<Menu> menus){
           menus.forEach(m -> {
               if(m.getParentId() == menu.getMenuId()){
                   //添加到父菜单
                   menu.getChildren().add(m);
                   //多级子菜单递归查询
                   m = findChildren(m, menus);

               }
           });

           return  menu;
       }

    @Override
    public boolean addMenu(Menu menu) {
        // 1. 验证ID必须存在
        if (menu.getMenuId() == null) {
            throw new IllegalArgumentException("菜单ID不能为空");
        }

        // 2. 检查ID是否已存在
        if (this.getById(menu.getMenuId()) != null  || menu.getMenuId() <= 0) {
            throw new IllegalArgumentException("菜单ID已存在");
        }
        // 2. 检查路由是否已存在
        if (menu.getMenuPath() == null) {
            throw new IllegalArgumentException("菜单路由不能为空");
        }

        if (menu.getMenuIcon()== null) {
            throw new IllegalArgumentException("菜单图标不能为空");
        }

        // 验证父菜单
        if (menu.getParentId() == null) {
            throw new IllegalArgumentException("父菜单ID不能为空");
        }
        // 3. 其他业务验证
        if (!StringUtils.hasText(menu.getMenuName()) || !StringUtils.hasText(menu.getMenuTitle())) {
            throw new IllegalArgumentException("菜单名称或者菜单标题不能为空");
        }

        // 4. 保存到数据库
        return this.save(menu);
    }

    @Override
    public boolean deleteMenu(Long menuId) {
        // 1. 检查菜单是否存在（可选）
        Menu menu = menuMapper.selectById(menuId);
        if (menu == null) {
            return false;
        }

        // 2. 删除菜单（返回影响的行数 > 0 表示成功）
        return menuMapper.deleteById(menuId) > 0;
    }

    @Override
    public boolean updateMenu(Menu menu) {

        // 在更新前检查菜单是否存在
        if (getById(menu.getMenuId()) == null) {
            throw new IllegalArgumentException("菜单不存在");
        }
        // 1. 验证ID必须存在
        if (this.getById(menu.getMenuId())== null) {
            throw new IllegalArgumentException("菜单ID不能为空");
        }
        
        if (menu.getMenuIcon() == null) {
            throw new IllegalArgumentException("菜单图标不能为空");
        }

        // 2. 检查ID是否已存在
        if (this.getById(menu.getMenuId()) == null || menu.getMenuId() <= 0) {
            throw new IllegalArgumentException("菜单ID必须大于0，且不能为空");
        }

        // 2. 检查路径是否已存在
        if (menu.getMenuPath() == null) {
            throw new IllegalArgumentException("菜单路由不能为空");
        }

        // 3. 其他业务验证
        if (!StringUtils.hasText(menu.getMenuName()) || !StringUtils.hasText(menu.getMenuTitle())) {
            throw new IllegalArgumentException("菜单名称或者菜单标题不能为空");
        }

        // 4. 保存到数据库
        return this.updateById(menu);
    }


}
