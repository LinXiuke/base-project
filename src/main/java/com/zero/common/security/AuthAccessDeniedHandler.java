package com.zero.common.security;

import com.alibaba.fastjson.JSON;
import com.zero.common.base.result.CommonResult;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @Description: ajax请求信息处理
 */
public class AuthAccessDeniedHandler implements AccessDeniedHandler {


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException e) throws IOException, ServletException {

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        CommonResult result = new CommonResult("Prohibition of access");
        result.setCode("403");
        PrintWriter out = response.getWriter();
        out.print(JSON.toJSONString(result));
        out.flush();
        out.close();
    }
}
