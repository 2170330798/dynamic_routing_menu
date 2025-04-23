package org.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@TableName(value = "models")
public class Model {

  @TableId(type = IdType.INPUT)
  private Long modelId;
  private String modelVersion;
  private String modelName;
  private String frameName;
  private double loss;
  private double accuracy;
  private Date createDate;
  private Date updateTime;
  private String modelPath;
  private Boolean isApply;


}
