package com.wallet.web.vo;

import com.wallet.web.myenum.CodeEnum;
import lombok.Data;

import java.io.Serializable;

@Data
public class ResponseResult<T> implements Serializable {
    /**
     * 错误码
     */
    private Integer code;
    /**
     * 错误信息
     */
    private String message;
    /**
     * 返回数据
     */
    private T data;
    public ResponseResult() {
        this.code = 0;
    }

    public ResponseResult(Integer code, T data) {
        this.code = code;
        this.data = data;
    }

    public ResponseResult(Integer code, String msg, T data) {
        this.code = code;
        this.message = msg;
        this.data = data;
    }

    public ResponseResult(Integer code, String msg) {
        this.code = code;
        this.message = msg;
    }
    public static ResponseResult errorResult(int code, String msg) {

        return  new ResponseResult(code,msg);

    }
    public static ResponseResult errorResult(CodeEnum enums){
        return errorResult(enums.getCode(),enums.getMessage());
    }
    public static ResponseResult okResult(Object data) {
        ResponseResult result = new ResponseResult(CodeEnum.SUCCESS.getCode(), CodeEnum.SUCCESS.getMessage());
        if(data!=null) {
            result.setData(data);
        }
        return result;
    }
    public static ResponseResult okResult() {
       return new ResponseResult(CodeEnum.SUCCESS.getCode(), CodeEnum.SUCCESS.getMessage());
    }
}
