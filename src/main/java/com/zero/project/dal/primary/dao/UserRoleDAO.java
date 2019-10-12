package com.zero.project.dal.primary.dao;

import com.zero.project.dal.primary.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description ：
 * @date ： 2019/10/12
 */

@Repository
public interface UserRoleDAO extends JpaRepository<UserRole, Long> {

    List<UserRole> findAllByUserId(Long userId);
}
