package org.example.service.impl;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.entity.Menu;
import org.example.mapper.MenuMapper;
import org.example.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

      @Autowired
      private MenuMapper menuMapper;

       @Override
       public List<Menu> findMenuTree(){
              //一维向量数据集合
              List <Menu> menus = menuMapper.selectList(null);

              //菜单树集合
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
}
