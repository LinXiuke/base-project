package com.zero.project.dal.primary.jpa.entity;

import com.zero.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @Description:
 * @Author: LXK
 * @Date: 2018/9/17
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
