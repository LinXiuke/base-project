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

                private long tokenValidityInSeconds;

                private long tokenValidityInSecondsForRememberMe;

            }
        }
    }

    @Data
    public static class Async {

        private int corePoolSize;

        private int maxPoolSize;

        private int queueCapacity;

    }


    @Data
    public static class HttpClient {

        private HttpClientPool pool = new HttpClientPool();
        private HttpClientTimeout standard = new HttpClientTimeout();
        private HttpClientTimeout wld = new HttpClientTimeout();

        @Data
        public static class HttpClientTimeout {

            private int socket_timeout;

            private int connect_timeout;

        }

        @Data
        public static class HttpClientPool {

            private int max_total;

            private int max_per_route;

        }
    }

}

