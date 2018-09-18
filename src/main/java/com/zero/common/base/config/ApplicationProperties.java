package com.zero.common.base.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.web.cors.CorsConfiguration;

/**
 * @Description: 配置类
 */
@Data
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {


    private final HttpClient httpclient = new HttpClient();

    private final CorsConfiguration cors = new CorsConfiguration();
    private final Security security = new Security();
    private final Async async = new Async();

    @Data
    public static class Security {

        private final Authentication authentication = new Authentication();

        @Data
        public static class Authentication {

            private final Jwt jwt = new Jwt();

            @Data
            public static class Jwt {

                private String secret;

                private long tokenValidityInSeconds = 1800;

                private long tokenValidityInSecondsForRememberMe = 2592000;

            }
        }
    }

    @Data
    public static class Async {

        private int corePoolSize = 2;

        private int maxPoolSize = 50;

        private int queueCapacity = 10000;

    }


    @Data
    public static class HttpClient {

        private HttpClientPool pool = new HttpClientPool();
        private HttpClientTimeout standard = new HttpClientTimeout();
        private HttpClientTimeout wld = new HttpClientTimeout();

        @Data
        public static class HttpClientTimeout {

            private int socket_timeout = 10000;
            private int connect_timeout = 10000;

        }

        @Data
        public static class HttpClientPool {

            private int max_total = 500;
            private int max_per_route = 100;

        }
    }

}

