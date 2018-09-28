package com.zero.common.base.result;

import lombok.Data;

import java.io.Serializable;

/**
 * @Description: 通用返回类
 */

@Data
public class CommonResult implements Serializable {

    private String code;
    private Object data;
    private String message;

    public CommonResult() {
        this.code = "0000";
        this.message = "";
    }

    public CommonResult(Object data) {
        this();
        this.data = data;
    }

    public CommonResult(String message) {
        this.code = "9999";
        this.message = message;
    }

    public CommonResult(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public CommonResult(ErrorCode errorCode) {
        this.code = errorCode.getCode();
        this.message = errorCode.getMessage();
    }
}
