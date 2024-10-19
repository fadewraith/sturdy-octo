package com.springsecuritytutorial.service;

import com.springsecuritytutorial.dto.UserRequest;
import com.springsecuritytutorial.model.UserDetail;
import com.springsecuritytutorial.repository.UserDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;


    @Autowired
    private UserDetailRepository userDetailRepository;

    @Override
    public String login(UserRequest userRequest) {
        Authentication authenticate = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                userRequest.getUserName(),
                                userRequest.getPassword()
                        )
                );

//        if(authenticate.isAuthenticated()) {
//            return "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c";
//        }

        if(authenticate.isAuthenticated()) {
            return jwtService.generateToken(userRequest.getUserName());
        }

        return null;
    }

    @Override
    public List<UserDetail> getUserDetails() {
        return userDetailRepository.findAll();
    }
}
