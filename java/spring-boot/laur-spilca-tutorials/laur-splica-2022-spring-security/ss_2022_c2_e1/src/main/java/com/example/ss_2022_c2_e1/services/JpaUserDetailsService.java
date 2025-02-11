package com.example.ss_2022_c2_e1.services;

import com.example.ss_2022_c2_e1.entities.User;
import com.example.ss_2022_c2_e1.repositories.UserRepository;
import com.example.ss_2022_c2_e1.securities.SecurityUser;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

//either mark this component as Service or use it, as it is used inside Bean method in SecurityConfig - method1
@Service // method2 - SecurityConfig
@AllArgsConstructor
public class JpaUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    public UserDetails loadUserByUsername(String username) {
        Optional<User> u = userRepository.findUserByUsername(username);

        return u.map(SecurityUser::new)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found " + username));
    }
}
