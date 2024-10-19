package com.usermanagementsystem.service;

import com.usermanagementsystem.model.User;

import java.util.List;

public interface UserService {

    public User saveUser(User user);

    public List<User> getAllUser();

    public User updateUser(User user);

    public void deleteUser(Integer id);

    public boolean deleteUserById(Integer id);

    public User getUserById(Integer userId);
}
