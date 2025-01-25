package com.socialapp.book.config;


import com.socialapp.book.user.User;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

//here AuditorAware type is Integer, coz in User id is of Integer type
//public class ApplicationAuditAware implements AuditorAware<Integer> {
public class ApplicationAuditAware implements AuditorAware<String> {

//    @Override
//    public Optional<Integer> getCurrentAuditor() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if(authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
//            return Optional.empty();
//        }
//        User userPrincipal = (User) authentication.getPrincipal();
//        return Optional.ofNullable(userPrincipal.getId());
//    }

    @Override
    public Optional<String> getCurrentAuditor() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken) {
            return Optional.empty();
        }

        return Optional.ofNullable(authentication.getName());
    }
}
