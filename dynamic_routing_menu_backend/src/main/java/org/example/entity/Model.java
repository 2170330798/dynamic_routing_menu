package org.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.util.Date;

@TableName(value = "models")
public class Model {

  @TableId(type = IdType.INPUT)
  private Long modelId;
  private String modelVersion;
  private String modelName;
  private double loss;
  private double accuracy;
  private Date createDate;
  private Date updateTime;
  private String modelPath;
  private Boolean isApply;

  public Long getModelId() {
    return modelId;
  }

  public void setModelId(Long modelId) {
    this.modelId = modelId;
  }


  public String getModelVersion() {
    return modelVersion;
  }

  public void setModelVersion(String modelVersion) {
    this.modelVersion = modelVersion;
  }

  public String getModelName() {
    return modelName;
  }

  public void setModelName(String modelName) {
    this.modelName = modelName;
  }


  public double getLoss() {
    return loss;
  }

  public void setLoss(double loss) {
    this.loss = loss;
  }


  public double getAccuracy() {
    return accuracy;
  }

  public void setAccuracy(double accuracy) {
    this.accuracy = accuracy;
  }


  public Date getCreateDate() {
    return createDate;
  }

  public void setCreateDate(java.sql.Timestamp createDate) {
    this.createDate = createDate;
  }


  public Date getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(java.sql.Timestamp updateTime) {
    this.updateTime = updateTime;
  }


  public String getModelPath() {
    return modelPath;
  }

  public void setModelPath(String modelPath) {
    this.modelPath = modelPath;
  }

  public Boolean getIsApply() {
    return isApply;
  }

  public void setIsApply(Boolean isApply) {
    this.isApply = isApply;
  }

  @Override
  public String toString() {
    return "Model{" +
            "modelId=" + modelId +
            ", modelVersion='" + modelVersion + '\'' +
            ", modelName='" + modelName + '\'' +
            ", loss=" + loss +
            ", accuracy=" + accuracy +
            ", createDate=" + createDate +
            ", updateTime=" + updateTime +
            ", modelPath='" + modelPath + '\'' +
            ", isApply=" + isApply +
            '}';
  }
}
