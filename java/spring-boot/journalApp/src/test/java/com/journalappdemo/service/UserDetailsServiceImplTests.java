package com.journalappdemo.service;

import com.journalappdemo.entity.User;
import com.journalappdemo.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

//@SpringBootTest
public class UserDetailsServiceImplTests {

//    @Autowired
    @InjectMocks
    private UserDetailsServiceImpl userDetailsService;

//    @MockBean // when using autowired & springboottest, then used this one
    @Mock
    private UserRepository userRepository;

//    in case of redis cache, need to use , autowiredm mockbean

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Disabled
    @Test
    void loadUserByUsernameTest() {
        when(userRepository.findByUserName(ArgumentMatchers.anyString())).thenReturn(User.builder().userName("").password("").roles(new ArrayList<>()).build());
        UserDetails user = userDetailsService.loadUserByUsername("");
        Assertions.assertNotNull(user);

    }
}
