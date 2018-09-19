package com.zero.common.core;

import lombok.Data;

/**
 * @Description: 请求头封装
 */

@Data
public class CommonRequestHeaderHolder {

    public static final ThreadLocal<CommonRequestHeader> context = new ThreadLocal<>();

    public static void clear() {
        context.set(null);
    }

    public static void setContext(CommonRequestHeader header) {
        context.set(header);
    }

    public static CommonRequestHeader getContext() {
        return context.get();
    }
}
