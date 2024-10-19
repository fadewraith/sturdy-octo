package com.rolebasedauthdemo.config;

import com.rolebasedauthdemo.model.Privilege;
import com.rolebasedauthdemo.model.User;
import com.rolebasedauthdemo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if(user == null) {
            throw new UsernameNotFoundException("user not found");
        }

        UserDetailsImpl userDetails = new UserDetailsImpl(user);

//        List<SimpleGrantedAuthority> authorities = user.getRole().getPrivileges()
//                .stream()
//                .map(
//                        privilege ->
//                                new SimpleGrantedAuthority(privilege.getName()))
//                .collect(Collectors.toList());

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Privilege privilege : user.getRole().getPrivileges()) {
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(privilege.getName());
            authorities.add(simpleGrantedAuthority);
        }

        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole().getName()));
        userDetails.setAuthorities(authorities);

        return userDetails;
    }
}
