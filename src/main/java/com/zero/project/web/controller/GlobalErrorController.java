package com.zero.project.web.controller;

import com.alibaba.fastjson.JSON;
import com.zero.common.base.result.CommonException;
import com.zero.common.base.result.CommonResult;
import com.zero.common.base.result.ErrorCode;
import io.jsonwebtoken.JwtException;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.WebAttributes;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 全局异常处理
 */
@RestController
public class GlobalErrorController implements ErrorController {

    @RequestMapping("/error")
    public static void error(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        Throwable error = (Throwable) request.getAttribute(WebUtils.ERROR_EXCEPTION_ATTRIBUTE);
        if (null == error) {
            error = (Throwable) request.getAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
        }
        if (null == error) {
            error = (Throwable) request.getAttribute(WebAttributes.ACCESS_DENIED_403);
        }

        if (null == error) {
            error = (Throwable) request.getAttribute("500 Internal Server Error");
        }

        CommonResult result;
        if (error instanceof JwtException) {
            result = new CommonResult(ErrorCode.UNAUTHORIZED);
        } else if (error instanceof AuthenticationException || error instanceof AccessDeniedException) {
            result = new CommonResult(ErrorCode.NOT_ACCESSIBLE);
        } else if (error instanceof MethodArgumentNotValidException) {
            result = new CommonResult(ErrorCode.REQUEST_ERROR);
        } else if (error instanceof CommonException) {
            result = new CommonResult(((CommonException) error).getErrorCode());
        } else {
            result = new CommonResult(ErrorCode.SERVER_ERROR);
        }

        PrintWriter out = response.getWriter();
        out.print(JSON.toJSONString(result));
        out.flush();
        out.close();
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }

}
