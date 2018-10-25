package com.zero.common.core.advice;

import com.github.pagehelper.PageInfo;
import com.zero.common.base.result.CommonResult;
import org.springframework.core.MethodParameter;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description:
 * @Author: Hogwarts
 * @Date: 2018/10/25
 */

@ControllerAdvice("com.zero.project.web.controller")
public class ReturnBodyAdvice implements ResponseBodyAdvice<Object> {


    @Override
    public boolean supports(MethodParameter methodParameter, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object beforeBodyWrite(Object body, MethodParameter methodParameter, MediaType mediaType, Class<? extends
            HttpMessageConverter<?>> aClass, ServerHttpRequest serverHttpRequest, ServerHttpResponse
                                          serverHttpResponse) {

        if (body instanceof CommonResult) {
            if (((CommonResult) body).getData() instanceof Page) {
                ((CommonResult) body).setPage(getJpaPageInfo((Page) ((CommonResult) body).getData()));
                ((CommonResult) body).setData(((Page) ((CommonResult) body).getData()).getContent());
            }

            if (((CommonResult) body).getData() instanceof PageInfo) {
                ((CommonResult) body).setPage(getMybatisPageInfo((PageInfo) ((CommonResult) body).getData()));
                ((CommonResult) body).setData(((PageInfo) ((CommonResult) body).getData()).getList());
            }
        }

        return body;
    }


    private Map getJpaPageInfo(Page page) {
        Map pageInfo = new HashMap();

        //当前页数量
        pageInfo.put("currentPageItemCount", page.getNumberOfElements());
        //总数量
        pageInfo.put("totalItems", page.getTotalElements());
        //总页数
        pageInfo.put("totalPages", page.getTotalPages());
        //当前页下表，从0开始
        pageInfo.put("pageIndex", page.getNumber());
        //每页数量
        pageInfo.put("pageSize", page.getSize());

        return pageInfo;
    }

    private Map getMybatisPageInfo(PageInfo page) {
        Map pageInfo = new HashMap();

        pageInfo.put("currentPageItemCount", page.getSize());
        pageInfo.put("totalItems", page.getTotal());
        pageInfo.put("totalPages", page.getPages());
        pageInfo.put("pageIndex", page.getPageNum() - 1);
        pageInfo.put("pageSize", page.getPageSize());

        return pageInfo;
    }
}
