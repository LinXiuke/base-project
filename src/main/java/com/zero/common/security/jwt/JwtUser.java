package com.zero.common.security.jwt;


import lombok.Builder;
import lombok.Data;

import java.security.Principal;

@Data
@Builder
public class JwtUser implements Principal {

    private Long userId;

    private String login;

    @Override
    public String getName() {
        return login;
    }
}