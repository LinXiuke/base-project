package com.zero.common.core;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @Description: 请求头
 */

@Data
public class CommonRequestHeader {

    /**
     * version 版本号
     */
    @ApiModelProperty(value = "应用版本号", dataType = "string", example = "1.0.0")
    private String version;

    /**
     * 平台类型
     */
    @ApiModelProperty(value = "平台类型", dataType = "string", example = "ios")
    private String platform;
}
