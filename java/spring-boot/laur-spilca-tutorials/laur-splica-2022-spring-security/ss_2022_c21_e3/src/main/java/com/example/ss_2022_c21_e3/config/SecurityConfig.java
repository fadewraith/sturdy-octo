package com.example.ss_2022_c21_e3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.httpBasic();

        http.authorizeHttpRequests().requestMatchers("/hello").hasAuthority("read")
                .anyRequest().authenticated();

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails u1 = User.withUsername("bill")
                .password(passwordEncoder().encode("12345"))
                .authorities("read")
                .build();

        UserDetails u2 = User.withUsername("josh")
                .password(passwordEncoder().encode("12345"))
                .authorities("write")
                .build();

        return new InMemoryUserDetailsManager(u1, u2);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}