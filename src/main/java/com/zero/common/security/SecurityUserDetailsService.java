package com.zero.common.security;

import com.zero.project.dal.primary.dao.RoleDAO;
import com.zero.project.dal.primary.dao.UserDAO;
import com.zero.project.dal.primary.dao.UserRoleDAO;
import com.zero.project.dal.primary.entity.User;
import com.zero.project.dal.primary.entity.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description ：
 * @Date ： 2019/10/12
 */

@Service
public class SecurityUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDAO userDAO;

    @Autowired
    private UserRoleDAO userRoleDAO;

    @Autowired
    private RoleDAO roleDAO;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userDAO.findByUsername(username);
        if (user == null) {
            return null;
        }

        List<UserRole> userRoles = userRoleDAO.findAllByUserId(user.getId());
        SecurityUserDetails securityUser = new SecurityUserDetails(user.getId(), user.getOpenId(), username, user.getPassword(), getUserAuthorities(userRoles));

        return securityUser;
    }

    private List<GrantedAuthority> getUserAuthorities(List<UserRole> userRoles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (UserRole userRole : userRoles) {
            authorities.add(new SimpleGrantedAuthority(userRole.getRoleCode()));
        }
        return authorities;
    }
}
