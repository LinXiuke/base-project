package com.zero.common.base.model;

import lombok.Data;

/**
 * @Description:
 * @Author: LXK
 * @Date: 2018/10/9
 */

@Data
public abstract class PageParams {

    private Integer pageNumber = 1;

    private Integer pageSize = 10;
}
