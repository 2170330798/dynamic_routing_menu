package org.example.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@TableName(value = "system_menu_table")
public class Menu implements Serializable {

   @TableId(type = IdType.INPUT)
   private Long menuId;
   private String menuName;
   private String menuPath;
   private String menuIcon;
   private String menuComponent;
   private String menuTitle;
   private Long parentId;
   private Boolean isDirectory;

   @TableField(exist = false)
   private List<Menu> children = new ArrayList<>();

}
