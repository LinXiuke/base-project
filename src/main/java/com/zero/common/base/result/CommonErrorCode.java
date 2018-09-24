package com.zero.common.base.result;

import lombok.Getter;

/**
 * @Description: 业务通用异常代码
 */

@Getter
public enum  CommonErrorCode {

    SUCCESS("0000", "success"),
    SERVER_ERROR("9999", "system error"),

    TOKEN_LOSE_EFFICACY("403", "token失效"),
    ;

    private String code;

    private String message;

    CommonErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static CommonErrorCode getByCode(String code) {

        for (CommonErrorCode errorCode : CommonErrorCode.values()) {
            if (errorCode.getCode().equals(code)) {
                return errorCode;
            }
        }

        return null;
    }
}
