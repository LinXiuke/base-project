package com.zero.project.dal.primary.mapper;

import com.zero.project.dal.primary.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Description:
 * @Author: Hogwarts
 * @Date: 2018/10/25
 */
@Mapper
public interface UserMapper {

    List<User> selectAll();
}
