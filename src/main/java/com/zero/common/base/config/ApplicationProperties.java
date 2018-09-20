package com.zero.common.base.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;

/**
 * @Description: 配置类
 */
@Data
@Component
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final CorsConfiguration cors = new CorsConfiguration();
    private final Security security = new Security();

    @Data
    public static class Security {

        private final Authentication authentication = new Authentication();

        @Data
        public static class Authentication {

            private final Jwt jwt = new Jwt();

            @Data
            public static class Jwt {

                private String secret;

                private long tokenValidity = 86400;

                private long tokenValidityRememberMe = 2592000;

            }
        }
    }


}

