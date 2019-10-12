package com.zero.common.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Description:
 */
public final class SecurityUtils {

    public static JWTUser getCurrentUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof JWTUser) {
                return (JWTUser) authentication.getPrincipal();
            }
        }

        return null;
    }

    public static List<String> getRoleList() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null) {
            return null;
        }
        Collection<? extends GrantedAuthority> authorities =  authentication.getAuthorities();
        if(CollectionUtils.isEmpty(authorities)){
            return null;
        }

        return authorities.stream().map(GrantedAuthority::getAuthority).collect(Collectors.toList());
    }
}
