package com.example.ss_2022_c7_e1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

// EnableGlobalMethodSecurity applicable to 3 kinds of annotations
//    out of 3 generally prePostEnabled is used
//    @PreAuthorize, @PostAuthorize, @PreFilter, @PostFilter
//    @Secured()
//   @RolesAllowed()

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic()
                .and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .build();

    }

    @Bean
    public UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager uds = new InMemoryUserDetailsManager();

        UserDetails u1 = User
                .withUsername("john")
                .password(passwordEncoder().encode("12345"))
                .authorities("read")
                .build();

        UserDetails u2 = User
                .withUsername("bill")
                .password(passwordEncoder().encode("12345"))
                .authorities("write")
                .build();

        uds.createUser(u1);
        uds.createUser(u2);
        return uds;

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
