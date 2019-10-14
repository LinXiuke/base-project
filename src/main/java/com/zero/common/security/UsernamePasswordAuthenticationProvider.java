package com.zero.common.security;

import com.zero.common.base.result.CommonException;
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
    private SecurityUserDetailsService securityUserDetailsService;

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

        SecurityUserDetails userDetails = (SecurityUserDetails) securityUserDetailsService.loadUserByUsername((String) authentication.getPrincipal());

        if (!userDetails.getPassword().equals(authentication.getCredentials())) {
            throw new CommonException("密码错误");
        }

        JWTUser jwtUser = new JWTUser();
        jwtUser.setOpenId(userDetails.getOpenId());
        jwtUser.setUserId(userDetails.getUserId());
        jwtUser.setUsername(userDetails.getUsername());
        token.setDetails(jwtUser);

        return new UsernamePasswordAuthenticationToken(jwtUser, null, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }

}
