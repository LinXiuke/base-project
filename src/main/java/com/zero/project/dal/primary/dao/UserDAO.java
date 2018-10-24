package com.zero.project.dal.primary.dao;

import com.zero.project.dal.primary.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Description:
 */

@Repository
public interface UserDAO extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
