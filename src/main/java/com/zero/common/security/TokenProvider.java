package com.zero.common.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.Date;

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

        AuthUser authUser = (AuthUser) authentication.getPrincipal();
        Long userId = authUser.getUserId();
        String openId = authUser.getOpenId();
        String username = authUser.getUsername();

        return Jwts.builder().setSubject(authentication.getName())
                .claim("userId", userId.toString())
                .claim("openId", openId)
                .claim("username", username)
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
        if (!StringUtils.isEmpty(openId) && !openId.trim().equals("")) {
            jwtUser = JWTUser.builder().username(username).openId(openId).userId(userId).build();
        }

        return new UsernamePasswordAuthenticationToken(jwtUser, null, null);
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
}
