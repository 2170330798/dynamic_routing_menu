package org.example.entity;

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

      public Integer getCode() {
            return code;
      }

      public void setCode(Integer code) {
            this.code = code;
      }

      public String getMessage() {
            return message;
      }

      public void setMessage(String message) {
            this.message = message;
      }

      public T getData() {
            return data;
      }

      public void setData(T data) {
            this.data = data;
      }

}
