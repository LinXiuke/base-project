package com.zero.project.web.form;

import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

/**
 * @Description: 登录表单
 */

@Data
public class SignInForm {

    @NotBlank(message = "用户名不能为空")
    private String username;

    @NotBlank(message = "密码不能为空")
    private String password;

    private Boolean rememberMe = Boolean.FALSE;
}
