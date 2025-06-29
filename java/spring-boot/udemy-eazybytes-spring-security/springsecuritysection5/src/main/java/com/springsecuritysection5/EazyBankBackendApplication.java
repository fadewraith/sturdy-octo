package com.springsecuritysection5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
/*@EnableJpaRepositories("com.springsecuritysection5.repository")
@EntityScan("com.springsecuritysection5.model")*/
public class EazyBankBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(EazyBankBackendApplication.class, args);
    }

}
