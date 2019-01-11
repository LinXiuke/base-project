package com.zero.common.base.config;

import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Locale;

/**
 * @Description: 自定义locale
 * @Author: Hogwarts
 * @Date: 2019/01/09
 */


public class SystemAcceptHeaderLocaleResolver extends AcceptHeaderLocaleResolver {

    private final static String US = "en-US";



    @Override
    public Locale resolveLocale(HttpServletRequest request) {

        Locale locale;

        String acceptLanguage = request.getHeader("Accept-Language");
        String language = acceptLanguage.substring(acceptLanguage.indexOf(","));
        System.out.println(language);
        switch (language) {
            case US:
                locale = Locale.US;
                break;
            default:
                locale = Locale.CHINA;
        }

        return locale;

    }
}
