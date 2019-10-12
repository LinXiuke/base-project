package com.zero.common.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @Description ：
 * @Date ： 2019/10/12
 */

@Getter
@Setter
public class SecurityUserDetails extends User {

    private static final long serialVersionUID = 1L;

    private Long userId;

    private String openId;

    public SecurityUserDetails(Long userId, String openId, String username, String password,  Collection<? extends GrantedAuthority> authorities) {
        super(username, password, true, false, false, false, authorities);
        this.userId = userId;
        this.openId = openId;
    }
}
