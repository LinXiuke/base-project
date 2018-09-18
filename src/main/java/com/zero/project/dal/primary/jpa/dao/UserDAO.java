package com.zero.project.dal.primary.jpa.dao;

import com.zero.project.dal.primary.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Description:
 */

@Repository
public interface UserDAO extends JpaRepository<User, Long> {
}
