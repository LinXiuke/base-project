package com.zero.project.web.controller;

import com.zero.common.base.result.CommonResult;
import com.zero.common.base.result.CommonResultTemplate;
import com.zero.project.biz.manager.EmailManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: Hogwarts
 * @Date: 2018/10/20
 */

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailManager emailManager;

    @PostMapping("/send")
    public CommonResult send() {
        return CommonResultTemplate.execute(() -> {
            emailManager.send();
            return null;
        });
    }
}
