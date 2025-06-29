package com.springsecuritysection4;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity // optional, for spring apps it is needed
// Enables JPA repositories in the specified package for CRUD operations.
// Specifies the package to scan for JPA entity classes.
//@EnableJpaRepositories("com.springsecuritysection4.repository")
//@EntityScan("com.springsecuritysection4.model")
public class EazyBankBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EazyBankBackendApplication.class, args);
    }

}
