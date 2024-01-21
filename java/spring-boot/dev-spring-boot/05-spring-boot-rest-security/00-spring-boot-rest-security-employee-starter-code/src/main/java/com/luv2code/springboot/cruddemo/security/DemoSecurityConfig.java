package com.luv2code.springboot.cruddemo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
public class DemoSecurityConfig {

//    add support for JDBC.. no more hardcoded users
//    inject DataSource auto-configured by spring boot
//    default pas is fun123
//    it used default table schemas - authorities, users are default schemas for authorization
//    @Bean
//    public UserDetailsManager userDetailsManager(DataSource dataSource) {
////        tell spring security to use JDBC authentication with our data source
//        return new JdbcUserDetailsManager(dataSource);
//    }

//    for custom schema for role based authorization - members, roles
    @Bean
    public UserDetailsManager userDetailsManager(DataSource dataSource) {

        JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager(dataSource);

        //define query to retrieve a user by username
//        ? will be the user name parameter from login
        jdbcUserDetailsManager.setUsersByUsernameQuery(
                "select user_id, pw, active from members where user_id=?"
        );

        //define query to retrieve the authorities/roles by username
        jdbcUserDetailsManager.setAuthoritiesByUsernameQuery(
                "select user_id, role from roles where user_id=?"
        );


        return jdbcUserDetailsManager;
    }

//    @Bean
//    public InMemoryUserDetailsManager userDetailsManager() {
//        UserDetails john = User.builder().username("john").password("{noop}john").roles("EMPLOYEE").build();
//
//        UserDetails mary = User.builder().username("mary").password("{noop}mary").roles("EMPLOYEE", "MANAGER").build();
//
//        UserDetails susan = User.builder().username("susan").password("{noop}susan").roles("EMPLOYEE", "MANAGER", "ADMIN").build();
//
//        return new InMemoryUserDetailsManager(john, mary, susan);
//    }


//    for role based api's, .hasRole for single role and.hasAnyRole for multiple roles
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(configurer ->
                configurer
                        .requestMatchers(HttpMethod.GET, "/api/employees").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.GET, "/api/employees/**").hasRole("EMPLOYEE")
                        .requestMatchers(HttpMethod.POST, "/api/employees").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.PUT, "/api/employees").hasRole("MANAGER")
                        .requestMatchers(HttpMethod.DELETE, "/api/employees/**").hasRole("ADMIN")
        );

//        use HTTP basic authentication
        http.httpBasic(Customizer.withDefaults());

//        disable Cross Site Reuqest Forgery (CSRF) -
//        in general, not requried for stateless REST API's that use POST, PUT, DELETE and/or PATCH
        http.csrf(csrf -> csrf.disable());

        return http.build();
    }

}
