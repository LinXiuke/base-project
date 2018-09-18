package com.zero.common.security;

import com.zero.common.base.config.ApplicationProperties;
import com.zero.project.dal.primary.jpa.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.Date;

/**
 * @Description:
 * @Author: LXK
 * @Date: 2018/9/18
 */

//@Slf4j
public class TokenProvider {

    private String secretKey;

    private long tokenValidityInMilliseconds;

    private long tokenValidityInMillisecondsForRememberMe;

    private final ApplicationProperties applicationProperties;

    public TokenProvider(ApplicationProperties applicationProperties) {
        this.applicationProperties = applicationProperties;
    }

    @PostConstruct
    public void init() {
        this.secretKey = applicationProperties.getSecurity().getAuthentication().getJwt().getSecret();

        this.tokenValidityInMilliseconds = 1000
                * applicationProperties.getSecurity().getAuthentication().getJwt().getTokenValidityInSeconds();
        this.tokenValidityInMillisecondsForRememberMe = 1000 * applicationProperties.getSecurity().getAuthentication()
                .getJwt().getTokenValidityInSecondsForRememberMe();
    }

    /**
     * 生成token
     */
    public String createToken(Authentication authentication, Boolean rememberMe) {
        long now = (new Date()).getTime();
        Date validity;
        if (rememberMe) {
            //一个月
            validity = new Date(now + this.tokenValidityInMillisecondsForRememberMe);
        } else {
            //一天
            validity = new Date(now + this.tokenValidityInMilliseconds);
        }

        User user = (User) authentication.getDetails();
        Long userId = user.getId();
        String openId = user.getOpenId();

        return Jwts.builder().setSubject(authentication.getName())
                .claim("userId", userId)
                .claim("openId", openId)
                //加密方式和密钥
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

        JWTUser JWTUser = null;

        String openId = claims.get("openId", String.class);
        Long userId = claims.get("userId", Long.class);
        if (!StringUtils.isEmpty(openId) && openId.trim() != ""  && null != userId) {
            JWTUser = JWTUser.builder().login(claims.getSubject()).openId(openId).userId(userId).build();
        }

        return new UsernamePasswordAuthenticationToken(JWTUser, token, null);
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
