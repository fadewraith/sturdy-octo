package com.springsecuritysection3.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> requests
                .requestMatchers("/myAccount", "/myBalance", "/myLoans", "/myCards").authenticated()
                .requestMatchers("/notices", "/contact", "/error").permitAll());
        http.formLogin(withDefaults());
        http.httpBasic(withDefaults());
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("user").password("{noop}User@12345").authorities("read").build();
        // BCrypt@12345 is password
        UserDetails admin = User.withUsername("admin").password("{bcrypt}$2a$12$IfJFsU3DtnR4udYO.N.fLOQSGPH2N5TLbY6F0zH7XxJaAclEwB5j.").authorities("admin").build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Use DelegatingPasswordEncoder to support multiple encoding formats (e\.g\., bcrypt, noop, pbkdf2)\.
        // This is better than using a direct bcrypt encoder because it allows seamless migration from legacy password hashes to modern ones\.
        // It enables applications to handle users with old and new password encodings without forcing all users to reset their passwords at once\.
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * Compromised password checker for {@link org.springframework.security.authentication.dao.DaoAuthenticationProvider}.
     * <p>
     * This bean is used by {@link org.springframework.security.config.annotation.authentication.builders.UserDetailsManagerConfigurer} to construct
     * a {@link org.springframework.security.authentication.dao.DaoAuthenticationProvider}.
     * <p>
     * The {@link org.springframework.security.crypto.password.HaveIBeenPwnedRestApiPasswordChecker} is used to check if the user's password is in the
     * {@external-link https://haveibeenpwned.com/Passwords Have I Been Pwned?} database.
     * available from v6.3
     * @return the compromised password checker
     */
    @Bean
    public CompromisedPasswordChecker compromisedPasswordChecker() {
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }

}
