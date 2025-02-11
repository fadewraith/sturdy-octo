package com.example.ss_2022_c2_e1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

//method1 - not recommended to avoid complexibility
//@Configuration
//@AllArgsConstructor
//public class SecurityConfig {
//
//    private final UserRepository userRepository;
//
//    @Bean
//    public UserDetailsService userDetailsService() {
//        return new JpaUserDetailsService();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return NoOpPasswordEncoder.getInstance();
//    }
//}

//method2, user @Service in JpaUSerDetailsService
@Configuration
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}