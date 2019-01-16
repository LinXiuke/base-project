package com.zero.project.biz.manager;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zero.common.base.result.CommonException;
import com.zero.common.security.SecurityUtils;
import com.zero.common.security.TokenProvider;
import com.zero.project.dal.primary.dao.UserDAO;
import com.zero.project.dal.primary.entity.User;
import com.zero.project.dal.primary.mapper.UserMapper;
import com.zero.project.web.form.SignInForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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

        Authentication authentication;

        try {
            authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken
                    (form.getUsername(), form.getPassword()));
        } catch (Exception e) {
            throw new CommonException(e.getMessage());
        }

        return tokenProvider.createToken(authentication, form.getRememberMe());
    }


    public Object getUserInfo() {


        return SecurityUtils.getCurrentUser();

    }


    @Transactional("primaryTransactionManager")
    public void updatePassword(String password) {
        Long userId = SecurityUtils.getCurrentUser().getUserId();
        User user = userDAO.findOne(userId);
        user.setPassword(password);
        userDAO.save(user);
    }
}
