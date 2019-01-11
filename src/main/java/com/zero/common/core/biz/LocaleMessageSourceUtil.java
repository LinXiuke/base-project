package com.zero.common.core.biz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class LocaleMessageSourceUtil {

    private static MessageSource messageSource;

    @Autowired
    public LocaleMessageSourceUtil(MessageSource messageSource) {
        LocaleMessageSourceUtil.messageSource = messageSource;
    }

    /**
     * @param code：message code
     * @return
     */

    public static String getMessage(String code) {

        return getMessage(code, "");

    }

    public static String getMessage(String code, Locale locale) {

        return getMessage(code, "", locale);

    }

    /**
     * @param code
     * @param defaultMessage :
     * @return
     */

    public static String getMessage(String code,  String defaultMessage) {

        Locale locale = LocaleContextHolder.getLocale();

        return getMessage(code, defaultMessage, locale);

    }

    /**
     * @param code
     * @param defaultMessage
     * @param locale
     * @return
     */

    public static String getMessage(String code, String defaultMessage, Locale locale) {

        return messageSource.getMessage(code, null, defaultMessage, locale);

    }

}
