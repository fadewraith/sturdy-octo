package com.springauditappdemo.config;

import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorConfig implements AuditorAware<Integer> {

//    used for implementing auditor createdBy and updatedBy
    @Override
    public Optional<Integer> getCurrentAuditor() {
        return Optional.of(2);
    }
}
