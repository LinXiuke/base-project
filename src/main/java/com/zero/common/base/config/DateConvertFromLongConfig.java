package com.zero.common.base.config;


import com.zero.common.base.result.CommonException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @Description:
 * @Author: Hogwarts
 * @Date: 2018/10/20
 */

@Component
public class DateConvertFromLongConfig implements Converter<Long, Date> {

    @Override
    public Date convert(Long timestamp) {
        try {
            return new Date(timestamp);
        } catch (Exception e) {
            throw new CommonException("时间戳错误");
        }
    }
}
