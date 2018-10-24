package com.zero.project.web.controller;

import com.zero.common.base.result.CommonException;
import com.zero.common.base.result.CommonResult;
import com.zero.common.base.result.CommonResultTemplate;
import com.zero.common.security.SecurityUtils;
import com.zero.project.biz.manager.UserManager;
import com.zero.project.web.form.SignInForm;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jetty.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Map;

/**
 * @Description:
 */

@Slf4j
@Api(value = "用户接口")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserManager userManager;

    @ApiOperation(value = "登录")
    @PostMapping("/signIn")
    public CommonResult siginIn(@Valid @RequestBody SignInForm form, BindingResult result, HttpServletResponse response) {
        log.info("登录");
        return CommonResultTemplate.execute(() ->{
            if (result.hasErrors()) {
                throw new CommonException(result.getAllErrors().get(0).getDefaultMessage());
            }

            return userManager.signIn(form, response);
        });
    }

    @GetMapping("/userInfo")
    public CommonResult getUserInfo() {
        return CommonResultTemplate.execute(SecurityUtils::getCurrentUser);
    }

    @PostMapping("/updatePassword")
    public CommonResult updatePassword(@RequestBody Map<String, String> params) {
        return CommonResultTemplate.execute(() -> {
            String password = params.get("password");
            if (StringUtil.isBlank(password)) {
                throw new CommonException("密码不能为空");
            }
            userManager.updatePassword(password);
            return null;
        });
    }
}
