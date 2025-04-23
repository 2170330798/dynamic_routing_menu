package org.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;

@Getter
@TableName(value = "frames")
public class Frame {

  @TableId(type = IdType.INPUT)
  private Integer frameId;
  private String frameName;
  private String frameDescribe;
  private String frameCharacter;

  public void setFrameId(Integer frameId) {
    this.frameId = frameId;
  }

  public void setFrameName(String frameName) {
    this.frameName = frameName;
  }

  public void setFrameDescribe(String frameDescribe) {
    this.frameDescribe = frameDescribe;
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
