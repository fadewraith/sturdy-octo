package com.springsecuritytutorial.service;

import com.springsecuritytutorial.dto.UserRequest;
import com.springsecuritytutorial.model.UserDetail;

import java.util.List;

public interface UserService {
    String login(UserRequest userRequest);

    public List<UserDetail> getUserDetails();
}
