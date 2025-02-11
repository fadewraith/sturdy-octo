package com.example.ss_2022_c3_e1.config.security.providers;

import com.example.ss_2022_c3_e1.config.security.authentication.CustomAuthentication;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

//step 5
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    @Value("${my.custom.secret.key}")
    private String key;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        CustomAuthentication ca = (CustomAuthentication) authentication;
        String headerKey = ca.getKey();

        if(key.equals(headerKey)) {
            return new CustomAuthentication(true, null);
        }

        throw new BadCredentialsException("Not authenticated");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CustomAuthentication.class.equals(authentication);
    }
}
