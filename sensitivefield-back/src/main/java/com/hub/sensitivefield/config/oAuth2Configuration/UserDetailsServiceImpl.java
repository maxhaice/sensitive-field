package com.hub.sensitivefield.config.oAuth2Configuration;

import com.hub.sensitivefield.model.User;
import com.hub.sensitivefield.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    UserService userService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userService.getUserByLogin(username).orElseThrow(
                () -> new UsernameNotFoundException("User Not Found with -> username : " + username));

        return UserPrinciple.build(user);
    }
}
