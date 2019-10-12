package com.zero.project.dal.primary.dao;

import com.zero.project.dal.primary.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Description ：
 * @Date ： 2019/10/12
 */

@Repository
public interface RoleDAO extends JpaRepository<Role, Long> {
}
