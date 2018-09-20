package com.zero.common.security;

import com.zero.common.base.config.ApplicationProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Date;

/**
 * @Description:
 */

@Component
public class TokenProvider {

    private String secretKey;

    private long tokenValidity;

    private long tokenValidityRememberMe;

    private final ApplicationProperties applicationProperties;

    public TokenProvider(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @PostConstruct
    public void init() {
        this.secretKey = applicationProperties.getSecurity().getAuthentication().getJwt().getSecret();

        this.tokenValidity = 1000
                * applicationProperties.getSecurity().getAuthentication().getJwt().getTokenValidity();
        this.tokenValidityRememberMe = 1000 * applicationProperties.getSecurity().getAuthentication()
                .getJwt().getTokenValidityRememberMe();
    }

    /**
     * 生成token
     */
    public String createToken(Authentication authentication, Boolean rememberMe) {
        long now = (new Date()).getTime();
        Date validity = rememberMe ? new Date(now + this.tokenValidityRememberMe) : new Date(now + this.tokenValidity);

        AuthUser authUser = (AuthUser) authentication.getDetails();
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
    public Authentication getToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody();

        JWTUser jwtUser = null;
//        AuthUser authUser = new AuthUser();

        String openId = claims.get("openId", String.class);
        Long userId = Long.valueOf(claims.get("userId").toString());
        String username = claims.get("username", String.class);
        if (!StringUtils.isEmpty(openId) && !openId.trim().equals("") && null != userId) {
            jwtUser = JWTUser.builder().username(username).openId(openId).userId(userId).build();
//            authUser.setUsername(username);
        }

        return new UsernamePasswordAuthenticationToken(jwtUser, token, null);
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
