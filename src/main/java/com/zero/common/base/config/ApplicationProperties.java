package com.zero.common.base.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;

/**
 * 属性在application.yml文件中配置
 */

@Data
@Component
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final CorsConfiguration cors = new CorsConfiguration();

    private final Async async = new Async();

    @Data
    public static class Async {

        private int corePoolSize = 2;
        private int maxPoolSize = 50;
        private int queueCapacity = 1000;
        private String threadNamePrefix = "thread-";

    }

}
