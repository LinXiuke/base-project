package com.zero.common.core.errors;

import com.zero.common.base.result.CommonException;
import com.zero.common.base.result.CommonResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Description:
 * @Author: LXK
 * @Date: 2018/9/25
 */
@ControllerAdvice
public class AdminExceptionHandler {

    @ResponseBody
    @ExceptionHandler(CommonException.class)
    public CommonResult handle(Exception e) {
        if (e instanceof CommonException) {
            CommonResult result = new CommonResult(e);
            return result;
        }

        return null;
    }
}
