package com.zero.common.core.filter;


import com.zero.common.core.CommonRequestHeader;
import com.zero.common.core.CommonRequestHeaderHolder;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

/**
 * @Description: 请求头过滤器
 */
public class CommonRequestHeaderFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        try {
            HttpServletRequest request = (HttpServletRequest) servletRequest;
            CommonRequestHeader header = new CommonRequestHeader();
            BeanWrapper beanWrapper = new BeanWrapperImpl(header);
            Enumeration<String> headerNames = request.getHeaderNames();
            while (headerNames.hasMoreElements()) {
                String headerName = headerNames.nextElement();
                beanWrapper.setPropertyValue(headerName, request.getHeader(headerName));
            }

            CommonRequestHeaderHolder.setContext(header);
            filterChain.doFilter(servletRequest, servletResponse);

        } catch (Exception e) {
            throw new ServletException("header error");
        } finally {
            CommonRequestHeaderHolder.clear();
        }
    }

    @Override
    public void destroy() {

    }
}
