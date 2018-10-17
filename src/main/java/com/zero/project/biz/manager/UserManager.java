package com.zero.project.biz.manager;

import com.zero.common.base.result.CommonException;
import com.zero.common.security.AuthUser;
import com.zero.common.security.JWTConfigurer;
import com.zero.common.security.SecurityUtils;
import com.zero.common.security.TokenProvider;
import com.zero.project.dal.primary.jpa.dao.UserDAO;
import com.zero.project.dal.primary.jpa.entity.User;
import com.zero.project.web.form.SignInForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;

/**
 * @Description: 用户登录退出
 * 直接放manager不放service里
 */

@Service
public class UserManager {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;


    /**
     * 登录
     */
    public Object signIn(SignInForm form, HttpServletResponse response) {

        User user = userDAO.findByUsername(form.getUsername());
        if (user == null) {
            throw new CommonException("用户不存在");
        }

        if (!form.getPassword().equals(user.getPassword())) {
            throw new CommonException("用户名或密码不正确");
        }


        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                    (form.getUsername(), form.getPassword()));
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }
//        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.createToken(authentication, form.getRememberMe());
//        response.setHeader(JWTConfigurer.AUTHORIZATION, token);
        return token;
    }


    @Transactional("primaryTransactionManager")
    public void updatePassword(String password) {
        Long userId = SecurityUtils.getCurrentUser().getUserId();
        User user = userDAO.findOne(userId);
        user.setPassword(password);
        userDAO.save(user);
    }
}
