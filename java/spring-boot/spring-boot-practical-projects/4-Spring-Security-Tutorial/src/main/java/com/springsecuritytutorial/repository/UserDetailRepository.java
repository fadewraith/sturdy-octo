package com.springsecuritytutorial.repository;

import com.springsecuritytutorial.model.UserDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDetailRepository extends JpaRepository<UserDetail, Integer> {
    UserDetail findByUserName(String userName);
}
