package com.zero.project.web.controller;

import com.zero.common.base.result.CommonException;
import com.zero.common.base.result.CommonResult;
import com.zero.common.base.result.CommonResultTemplate;
import com.zero.common.security.SecurityUtils;
import com.zero.project.biz.manager.UserManager;
import com.zero.project.web.form.SignInForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * @Description:
 */

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserManager userManager;

    @PostMapping("/signIn")
    public CommonResult siginIn(@Valid @RequestBody SignInForm form, BindingResult result) {
        return CommonResultTemplate.excute(() ->{
            if (result.hasErrors()) {
                throw new CommonException(result.getAllErrors().get(0).getDefaultMessage());
            }

            return userManager.signIn(form);
        });
    }

    @GetMapping("/userInfo")
    public CommonResult getUserInfo() {
        return CommonResultTemplate.excute(SecurityUtils::getCurrentUser);
    }
}
