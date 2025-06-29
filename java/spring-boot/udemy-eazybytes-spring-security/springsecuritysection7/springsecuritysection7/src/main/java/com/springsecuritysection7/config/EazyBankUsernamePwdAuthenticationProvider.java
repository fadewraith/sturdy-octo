package com.springsecuritysection7.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@Profile("!prod")
@RequiredArgsConstructor
public class EazyBankUsernamePwdAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName();
        String pwd = authentication.getCredentials().toString();
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        if(passwordEncoder.matches(pwd, userDetails.getPassword())) {
            return new UsernamePasswordAuthenticationToken(userName, pwd, userDetails.getAuthorities());
        } else {
            throw new BadCredentialsException("Invalid password!");
        }
    }

    /**
     * The supports method in this Spring Security authentication provider class is a crucial part of the authentication flow. Here's what it does:
     *
     * It overrides the supports method from the AuthenticationProvider interface.
     * It checks if the provided Authentication object is of type UsernamePasswordAuthenticationToken.
     * It returns true if the authentication object is a UsernamePasswordAuthenticationToken, indicating that this provider can handle this type of authentication.
     * It returns false if the authentication object is of a different type, indicating that this provider cannot handle it.
     * This method is essential because:
     *
     * It ensures type safety in the authentication process.
     * It allows Spring Security to route authentication requests to the correct provider based on the type of authentication token being used.
     * It prevents the provider from attempting to handle unsupported authentication types.
     * In this case, since it's a username/password authentication provider, it only supports UsernamePasswordAuthenticationToken objects, which is the standard token type for username/password authentication in Spring Security.
     *
     * Feedback submitted
     * Generating
     * */
    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
