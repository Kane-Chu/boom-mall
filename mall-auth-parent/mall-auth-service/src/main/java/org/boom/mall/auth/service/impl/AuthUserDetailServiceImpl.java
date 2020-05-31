package org.boom.mall.auth.service.impl;

import org.boom.mall.auth.api.bean.AuthUser;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * ${@link org.springframework.security.core.userdetails.UserDetailsService} 实现类
 *
 * @author kane
 */
@Service
public class AuthUserDetailServiceImpl implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // TODO Mock Data
        return new AuthUser("1", "kane", "{noop}pass1234",
                true, true, true, true,
                AuthorityUtils.createAuthorityList("admin"));
    }
}