package com.springsecuritytutorial.config;

import com.springsecuritytutorial.model.UserDetail;
import com.springsecuritytutorial.repository.UserDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private UserDetailRepository userDetailRepository;

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {

        UserDetail byUsername = userDetailRepository.findByUserName(userName);

        if(byUsername == null) {
            throw new UsernameNotFoundException("user not found");
        }

        return new MyUserDetails(byUsername);
    }
}
