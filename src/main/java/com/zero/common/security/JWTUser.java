package com.zero.common.security;


import lombok.Builder;
import lombok.Data;

import java.security.Principal;

@Data
@Builder
public class JWTUser implements Principal {

    private Long userId;

    private String login;

    private String openId;

    @Override
    public String getName() {
        return login;
    }
}