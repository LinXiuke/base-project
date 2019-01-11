package com.zero.common.base.result;

import org.slf4j.LoggerFactory;

/**
 * @Description: 返回类封装
 */
public class CommonResultTemplate {

    public static <T> CommonResult<T> execute(Callback<T> callback) {
        CommonResult<T> result = new CommonResult<>();

        try {
            result.setData(callback.execute());

        } catch (CommonException e) {

            LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[3].getClassName()).debug("business " +
                    "error", e);

            String message = e.getLocalizedMessage();
            result.setMessage(e.getLocalizedMessage());

        } catch (Exception e) {

            LoggerFactory.getLogger(Thread.currentThread().getStackTrace()[3].getClassName()).debug("business " +
                    "error", e);

            result.setMessage(new CommonException(ErrorCode.SERVER_ERROR).getLocalizedMessage());
        }

        return result;
    }

    public interface Callback<T> {
        T execute();
    }
}
