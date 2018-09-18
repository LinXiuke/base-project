package com.zero.common.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * @Description:
 */
public final class SecurityUtils {

    public static JWTUser getJwtUser() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof JWTUser) {
                return (JWTUser) authentication.getPrincipal();
            }
        }

        return null;
    }

}
