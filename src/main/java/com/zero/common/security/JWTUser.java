package com.zero.common.security;


import lombok.Builder;
import lombok.Data;

import java.security.Principal;

@Data
@Builder
public class JWTUser implements Principal {

    private Long userId;

    private String username;

    private String openId;

    public JWTUser() {
    }

    @Override
    public String getName() {
        return username;
    }
}