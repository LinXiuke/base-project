package com.zero.common.security;

import com.zero.common.base.result.CommonException;
import com.zero.project.dal.primary.dao.UserDAO;
import com.zero.project.dal.primary.entity.User;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;


@Component
public class UsernamePasswordAuthenticationProvider implements AuthenticationProvider, InitializingBean,
        MessageSourceAware {

    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    @Autowired
    private UserDAO userDAO;


    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(this.messages, "A message source must be set");
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        User user = userDAO.findByUsername((String) token.getPrincipal());

        if (user == null) {
            throw new CommonException("用户不存在");
        }

        if (!StringUtils.equals(user.getPassword(), (String) token.getCredentials())) {
            throw new CommonException("用户名或密码不正确");
        }

        AuthUser details = new AuthUser();
        details.setOpenId(user.getOpenId());
        details.setUserId(user.getId());
        details.setUsername((String) token.getPrincipal());
        token.setDetails(details);

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new
                UsernamePasswordAuthenticationToken(details, null, null);

        return usernamePasswordAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

}
