package com.zero.project.dal.primary.entity;

import com.zero.common.base.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Description ：
 * @Date ： 2019/10/12
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "user_role")
public class UserRole extends BaseEntity {

    private Long userId;

    private String roleCode;
}
