package com.example.ss_2022_c4_e1.config.filters;

import com.example.ss_2022_c4_e1.config.authentications.ApiKeyAuthentication;
import com.example.ss_2022_c4_e1.config.managers.CustomAuthenticationManager;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@AllArgsConstructor
public class ApiKeyFilter extends OncePerRequestFilter {

    private final String key;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        CustomAuthenticationManager manager = new CustomAuthenticationManager(key);

        String requestKey = request.getHeader("x-api-key");

        if (requestKey == null || "null".equals(requestKey)) {
            filterChain.doFilter(request, response);
        }

        ApiKeyAuthentication apiKeyAuthentication = new ApiKeyAuthentication(requestKey);

        try {
            Authentication authenticate = manager.authenticate(apiKeyAuthentication);
            if(authenticate.isAuthenticated()) {
                SecurityContextHolder.getContext().setAuthentication(authenticate);
                filterChain.doFilter(request, response);
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            }
        } catch (AuthenticationException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }

    }
}
