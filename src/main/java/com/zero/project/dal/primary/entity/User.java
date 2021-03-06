package com.zero.project.dal.primary.entity;

import com.zero.common.base.model.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Description:
 */

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "user")
public class User extends BaseEntity {

    private String openId;

    private String username;

    private String password;
}
