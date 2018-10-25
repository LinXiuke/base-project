package com.zero.common.base.result;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * @Description: 通用返回类
 */

@Data
public class CommonResult implements Serializable {

    /**
     * 0000为返回正常， 其它code均为请求错误
     */
    private String code;

    /**
     * 返回数据
     */
    private Object data;

    /**
     * 错误信息
     */
    private String message;

    /**
     * 分页信息
     */
    private Map page;

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
