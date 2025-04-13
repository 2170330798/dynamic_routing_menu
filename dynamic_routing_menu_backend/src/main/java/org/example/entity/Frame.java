package org.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

@TableName(value = "frames")
public class Frame {

  @TableId(type = IdType.INPUT)
  private Integer frameId;
  private String frameName;
  private String frameDescribe;
  private String frameCharacter;


  public Integer getFrameId() {
    return frameId;
  }

  public void setFrameId(Integer frameId) {
    this.frameId = frameId;
  }


  public String getFrameName() {
    return frameName;
  }

  public void setFrameName(String frameName) {
    this.frameName = frameName;
  }


  public String getFrameDescribe() {
    return frameDescribe;
  }

  public void setFrameDescribe(String frameDescribe) {
    this.frameDescribe = frameDescribe;
  }


  public String getFrameCharacter() {
    return frameCharacter;
  }

  public void setFrameCharacter(String frameCharacter) {
    this.frameCharacter = frameCharacter;
  }


  @Override
  public String toString() {
    return "Frame{" +
            "frameId=" + frameId +
            ", frameName='" + frameName + '\'' +
            ", frameDescribe='" + frameDescribe + '\'' +
            ", frameCharacter='" + frameCharacter + '\'' +
            '}';
  }

}
