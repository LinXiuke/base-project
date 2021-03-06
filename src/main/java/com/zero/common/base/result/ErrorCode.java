package com.zero.common.base.result;

import lombok.Getter;

/**
 * @Description: 业务通用异常代码
 */

@Getter
public enum ErrorCode {

    SUCCESS("0000", "success"),
    SERVER_ERROR("9999", "system error"),

    REQUEST_ERROR("400", "请求错误"),
    UNAUTHORIZED("401", "未授权"),
    NOT_ACCESSIBLE("403", "不可访问"),
    METHOD_NOT_ALLOWED("405", "方法不被允许"),
    UNSUPPORTED_MEDIA_TYPE("415", "不支持当前媒体类型"),
    INTERNAL_SERVER_ERROR("500", "Internal Server Error"),


    TOKEN_LOSE_EFFICACY("1001", "您的登录令牌已失效，请重新登录"),;

    private String code;

    private String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static ErrorCode getByCode(String code) {

        for (ErrorCode errorCode : ErrorCode.values()) {
            if (errorCode.getCode().equals(code)) {
                return errorCode;
            }
        }

        return null;
    }
}
