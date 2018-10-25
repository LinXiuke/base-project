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
    private UserMapper userMapper;

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


    public Object getUserInfo() {

//        PageHelper.startPage(1, 10);
//        List<User> list = userMapper.selectAll();
//        PageInfo<User> page = new PageInfo<>(list);

//        Page<Integer> page = new PageImpl<>(list, new PageRequest(0, 10), list.size());

//        return page;

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
