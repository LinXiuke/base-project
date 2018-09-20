package com.zero.common.security;

import lombok.Data;

/**
 * @Description:
 */

@Data
public class AuthDetails {

    private Long userId;

    private String username;

    private String openId;
}
