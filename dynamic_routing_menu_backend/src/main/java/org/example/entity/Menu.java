package org.example.entity;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
   private List<Menu> children;

   public Long getMenuId() {
      return menuId;
   }

   public void setMenuId(Long menuId) {
      this.menuId = menuId;
   }

   public String getMenuName() {
      return menuName;
   }

   public void setMenuName(String menuName) {
      this.menuName = menuName;
   }

   public String getMenuPath() {
      return menuPath;
   }

   public void setMenuPath(String menuPath) {
      this.menuPath = menuPath;
   }

   public String getMenuComponent() {
      return menuComponent;
   }

   public void setMenuComponent(String menuComponent) {
      this.menuComponent = menuComponent;
   }

   public String getMenuTitle() {
      return menuTitle;
   }

   public void setMenuTitle(String menuTitle) {
      this.menuTitle = menuTitle;
   }

   public Long getParentId() {
      return parentId;
   }

   public void setParentId(Long parentId) {
      this.parentId = parentId;
   }

   public List<Menu> getChildren() {
      if (children == null) {
         children = new ArrayList<>();
      }
      return children;
   }

   public void setChildren(List<Menu> children) {
       this.children = children;
   }


   public Boolean getIsDirectory() {
      return isDirectory;
   }

   public void setIsDirectory(Boolean isDirectory) {
      this.isDirectory = isDirectory;
   }

   public String getMenuIcon() {
      return menuIcon;
   }

   public void setMenuIcon(String menuIcon) {
      this.menuIcon = menuIcon;
   }

   @Override
   public String toString() {
      return "Menu{" +
              "menuId=" + menuId +
              ", menuName='" + menuName + '\'' +
              ", menuPath='" + menuPath + '\'' +
              ", menuIcon='" + menuIcon + '\'' +
              ", menuComponent='" + menuComponent + '\'' +
              ", menuTitle='" + menuTitle + '\'' +
              ", parentId=" + parentId +
              ", isDirectory=" + isDirectory +
              ", children=" + children +
              '}';
   }
}
