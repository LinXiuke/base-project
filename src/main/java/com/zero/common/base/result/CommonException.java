package com.zero.common.base.result;

import com.zero.common.core.biz.LocaleMessageSourceUtil;
import lombok.Data;
import org.eclipse.jetty.util.StringUtil;

/**
 * @Description: 业务通用异常
 */

@Data
public class CommonException extends RuntimeException {

    private ErrorCode errorCode;

    private String messageKey;

    public CommonException(String message) {
        super(message);
    }

    public CommonException(String messageKey, String message) {
        super(message);
        this.messageKey = messageKey;
    }

    public CommonException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public String getLocalizedMessage() {

        if (errorCode != null) {
            return LocaleMessageSourceUtil.getMessage(errorCode.toString(), errorCode.getMessage());
        }

        if (StringUtil.isNotBlank(messageKey)) {
            return LocaleMessageSourceUtil.getMessage(messageKey, getMessage());
        }

        return getMessage();
    }
}
