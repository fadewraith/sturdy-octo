package com.example.ss_2022_c19_e1.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Configuration
public class SecurityConfig {

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.authorizeHttpRequests().anyRequest().permitAll();
    http.cors(c -> c.configurationSource(
            r -> {
              CorsConfiguration corsConfiguration = new CorsConfiguration();
//              corsConfiguration.setAllowedOrigins(List.of("*"));
              corsConfiguration.setAllowedOrigins(List.of("http://localhost:8080"));
              return corsConfiguration;
            }
    ));
    return http.build();
  }

}