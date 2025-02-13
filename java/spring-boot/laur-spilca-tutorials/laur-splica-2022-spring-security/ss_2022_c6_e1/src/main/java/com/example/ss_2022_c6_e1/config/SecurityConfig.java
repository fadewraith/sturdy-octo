package com.example.ss_2022_c6_e1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

//    at endpoint level authorization, we can use filters but for methods aspect or any method bean
//    401 Unauthorized -> Authentication
//    403 Forbidden -> Authorization
//    recommended is to use .roles("") instead of .authorities("") because it is more readable
//    mvc matchers is recommended for web applications
//    if there is an endpoint authorization which doesnt exist in code, then the user will get method not allowed
//    /demo/anything/*/something --> /demo/anything/abc/something

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .httpBasic()
                .and()
                .authorizeRequests()
//                .mvcMatchers("/demo/demo1").authenticated()
//                .mvcMatchers("/demo/**").hasAuthority("read")
//                .mvcMatchers(HttpMethod.GET, "/demo/**").hasAuthority("read")
//                .mvcMatchers("/test2").permitAll() // anyone can access
//                .mvcMatchers("/demo/demo2").hasAuthority("read") // only user with read authority can access
//                .anyRequest().authenticated()
                /**
                 * using antMatchers -
                 * .antMatchers("/demo/demo1").authenticated()
                 * we have to configure all endpoints like this - /demo/demo1, /demo/demo1/, /demo/demo1/abc
                 * .antMatchers("/demo/demo1", "demo/demo1").authenticated()
                 *                 .anyRequest().permitAll()
                 *                 .and()
                 *                 .csrf().disable()
                 *                 .build();
                 *
                 * {{url}}:{{port}}/demo/demo1 -> unauthenticated
                 * {{url}}:{{port}}/demo/demo1/ -> authenticated
                 * */
//                .antMatchers("/demo/demo1").authenticated()
                .mvcMatchers("/demo/demo1").authenticated()
//                .regexMatchers("regex").authenticated()
                .anyRequest().permitAll()
                .and()
                .csrf().disable()
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
                .authorities("write", "delete")
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
