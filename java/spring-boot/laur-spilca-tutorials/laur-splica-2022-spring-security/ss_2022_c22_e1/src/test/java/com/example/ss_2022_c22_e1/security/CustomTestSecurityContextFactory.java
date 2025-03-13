package com.example.ss_2022_c22_e1.security;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithSecurityContextFactory;

import java.util.List;

public class CustomTestSecurityContextFactory implements WithSecurityContextFactory<WithCustomMockUser> {

    @Override
    public SecurityContext createSecurityContext(WithCustomMockUser annotation) {
        String priority = annotation.priority();
//        take your custom values from the annotation you used on the test method
//        var a = new UsernamePasswordAuthenticationToken("", "", List.of(() -> "read"));
        var a = new CustomAuthentication(priority);

        SecurityContext testSecurityContext = SecurityContextHolder.createEmptyContext();
        testSecurityContext.setAuthentication(a);

        return testSecurityContext;
    }
}
