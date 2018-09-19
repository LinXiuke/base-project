package com.zero.common.base.result;

import lombok.Data;

/**
 * @Description: 业务通用异常
 */

@Data
public class CommonException extends RuntimeException {

    private CommonErrorCode errorCode;

    public CommonException(String message) {
        super(message);
    }

    public CommonException(CommonErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
