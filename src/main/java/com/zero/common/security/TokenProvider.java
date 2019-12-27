package com.zero.common.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description:
 */

@Component
public class TokenProvider {

    @Value("${jwt.secret_key}")
    private String secretKey;

    @Value("${jwt.token_validity}")
    private long tokenValidity;

    @Value("${jwt.token_validity_remember_me}")
    private long tokenValidityRememberMe;


    /**
     * 生成token
     */
    public String createToken(Authentication authentication, Boolean rememberMe) {
        long now = (new Date()).getTime();
        Date validity = rememberMe ? new Date(now + this.tokenValidityRememberMe * 1000) : new Date(now + this
                .tokenValidity * 1000);

        JWTUser authUser = (JWTUser) authentication.getPrincipal();
        Long userId = authUser.getUserId();
        String openId = authUser.getOpenId();
        String username = authUser.getUsername();

        return Jwts.builder().setSubject(authentication.getName())
                .claim("userId", userId.toString())
                .claim("openId", openId)
                .claim("username", username)
                .claim("roles", getRoleFromAuthorities(authentication.getAuthorities()))
                .signWith(SignatureAlgorithm.HS256, secretKey)
                .setExpiration(validity)
                .compact();
    }

    /**
     * 获取
     */
    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        JWTUser jwtUser = null;

        String openId = claims.get("openId", String.class);
        Long userId = Long.valueOf(claims.get("userId").toString());
        String username = claims.get("username", String.class);
        jwtUser = new JWTUser(userId, username, openId);

        return new UsernamePasswordAuthenticationToken(jwtUser, null, getAuthoritiesFromRole(claims.get("roles", String.class)));
    }


    /**
     * token验证
     */
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
        } catch (Exception e) {
            return false;
        }
        return true;
    }


    private String getRoleFromAuthorities(Collection<? extends GrantedAuthority> authorities) {
        List<String> roles = new ArrayList<>();
        for (GrantedAuthority authority : authorities) {
            if (authority instanceof SimpleGrantedAuthority) {
                roles.add(authority.getAuthority());
            }
        }
        return String.join(",", roles);
    }


    private List<SimpleGrantedAuthority> getAuthoritiesFromRole(String roles) {
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        String[] roleArray = roles.split(",");
        for (String role : roleArray) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }
}
