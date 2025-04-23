package org.example.entity;


import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName(value = "user")
public class User {

  @TableId(type = IdType.NONE)
  private String userId;
  private String userName;
  private String password;
  private String phone;
  private boolean role;
  private String createTime;
  private String updateTime;
  private String avatar;

  @Override
  public String toString() {
    return "User{" +
            "userId='" + userId + '\'' +
            ", userName='" + userName + '\'' +
            ", password='" + password + '\'' +
            ", phone='" + phone + '\'' +
            ", role=" + role +
            ", createTime='" + createTime + '\'' +
            ", updateTime='" + updateTime + '\'' +
            ", avatar='" + avatar + '\'' +
            '}';
  }
}
