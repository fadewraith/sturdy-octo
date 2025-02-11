package com.example.ss_2022_c3_e1.config.security.filters;

import com.example.ss_2022_c3_e1.config.security.authentication.CustomAuthentication;
import com.example.ss_2022_c3_e1.config.security.managers.CustomAuthenticationManager;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@AllArgsConstructor
public class CustomAuthenticationFilter extends OncePerRequestFilter {
    //    step 2
//    in filter chain, there is no guarantee that it will be called once, in place of Filter use extends OncePerRequestFilter

    private final CustomAuthenticationManager customAuthenticationManager;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException
    {
//        1. create an authentication object which is not yet authenticated
//        2. delegate the authentication object to the manager
//        3. get back the authentication from the manager
//        4. if the object is authenticated, then send request to the next filter in the chain

        String key = String.valueOf(request.getHeader("key"));
        CustomAuthentication customAuthentication = new CustomAuthentication(false, key);

        Authentication authenticate = customAuthenticationManager.authenticate(customAuthentication);

        if(authenticate.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            filterChain.doFilter(request, response); // only when authentication works
        }

    }
}
