package org.example.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@TableName(value = "user")
public class User {

  private long userId;
  private String username;
  private String password;
  private String phone;
  private long userRole;
  private java.sql.Timestamp createTime;
  private java.sql.Timestamp updateTime;

}
