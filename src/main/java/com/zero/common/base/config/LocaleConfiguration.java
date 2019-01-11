package com.zero.common.base.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

/**
 * @Description:
 * @Author: Hogwarts
 * @Date: 2019/01/11
 */

@Configuration
public class LocaleConfiguration {

    @Bean(name = "localeResolver")
    public LocaleResolver localeResolver() {
//        return new AcceptHeaderLocaleResolver();
        return new SystemAcceptHeaderLocaleResolver();
    }
}
