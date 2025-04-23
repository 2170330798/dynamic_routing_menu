package org.example.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResponseResult<T> {
      private Integer code;

      private String message;

      private T data;

      public ResponseResult(Integer code, String message, T data) {
            // 全参构造方法
            this.code = code;
            this.message = message;
            this.data = data;
      }

      public void setCode(Integer code) {
            this.code = code;
      }

      public void setMessage(String message) {
            this.message = message;
      }

      public void setData(T data) {
            this.data = data;
      }

}
