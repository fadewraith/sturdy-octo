package com.eazybytes.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests((requests) -> requests.requestMatchers("/secure").authenticated().anyRequest().permitAll())
                .formLogin(Customizer.withDefaults())
                .oauth2Login(Customizer.withDefaults());
        return httpSecurity.build();
    }

    /*@Bean // Authorization Server - there are many of them, but commonly used is InMemoryClientRegistrationRepository
    ClientRegistrationRepository clientRegistrationRepository() {
        // explore there are many more like GOOGLE
        ClientRegistration github = githubClientRegistration();
//        ClientRegistration facebook = facebookClientRegistration();
//        return new InMemoryClientRegistrationRepository(github, facebook);
        return new InMemoryClientRegistrationRepository(github);
    }

    private ClientRegistration githubClientRegistration() {
        return CommonOAuth2Provider.GITHUB.getBuilder("github").clientId("yours")
                .clientSecret("yours").build();
    }

//    private ClientRegistration facebookClientRegistration() {
//        return CommonOAuth2Provider.FACEBOOK.getBuilder("facebook").clientId("yours")
//                .clientSecret("yours").build();
//    } */

}
