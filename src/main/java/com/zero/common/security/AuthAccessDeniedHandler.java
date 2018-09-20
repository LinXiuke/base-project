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
 * @Description:
 */
public class AuthAccessDeniedHandler implements AccessDeniedHandler {


    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException e) throws IOException, ServletException {

        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        CommonResult envelop = new CommonResult("Prohibition of access");
        envelop.setCode("403");
        PrintWriter out = response.getWriter();
        out.print(JSON.toJSONString(envelop));
        out.flush();
        out.close();
    }
}
