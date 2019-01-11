package com.zero.project.web.controller;

import com.zero.common.base.result.CommonException;
import com.zero.common.base.result.CommonResult;
import com.zero.common.base.result.CommonResultTemplate;
import com.zero.common.base.result.ErrorCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: Hogwarts
 * @Date: 2019/01/11
 */

@RestController
@RequestMapping("/test")
public class TestController {


    @GetMapping("/in")
    public CommonResult test() {
        return CommonResultTemplate.execute(() -> {
            throw new CommonException(ErrorCode.SERVER_ERROR);
        });
    }
}
