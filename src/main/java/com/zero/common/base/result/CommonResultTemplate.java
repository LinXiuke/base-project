package com.zero.common.base.result;

/**
 * @Description: 返回类封装
 */
public class CommonResultTemplate {

    public static CommonResult excute(Callback callback) {
        CommonResult result;

        try {
            result = new CommonResult();
            result.setData(callback.execute());

        } catch (CommonException e) {
            if (e.getErrorCode() != null) {
                result = new CommonResult(e.getErrorCode());
            } else {
                result = new CommonResult(e.getMessage());
            }
        } catch (Exception e) {
            result = new CommonResult(CommonErrorCode.SERVER_ERROR);

        }

        return result;
    }

    public interface Callback<T> {
        T execute();
    }
}
