package com.rolebasedauthdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity // this authorization is used on the method name, then import this
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(bCryptPasswordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        httpSecurity.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(req -> req
                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET).hasAuthority("READ")
                        .requestMatchers(HttpMethod.POST).hasAuthority("CREATE")
                        .requestMatchers(HttpMethod.PUT).hasAuthority("UPDATE")
                        .requestMatchers(HttpMethod.DELETE).hasAuthority("DELETE")

//                        method2 - used in the controller on the methods, below is method1
//                        .requestMatchers("/api/product/**").hasAnyRole("ADMIN", "SELLER", "CUSTOMER")
//                        .requestMatchers(HttpMethod.GET).hasAuthority("READ")
//                        .requestMatchers(HttpMethod.POST).hasAuthority("CREATE")
//                        .requestMatchers(HttpMethod.PUT).hasAuthority("UPDATE")
//                        .requestMatchers(HttpMethod.DELETE).hasAuthority("DELETE")
                )
                .httpBasic(Customizer.withDefaults());
//                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
//                .addFilterBefore(UsernamePasswordAuthenticationFilter.class);

        return httpSecurity.build();

    }
}
