package com.usermanagementsystem.service;

import com.usermanagementsystem.model.User;
import com.usermanagementsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User saveUser(User user) {
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    @Override
    public List<User> getAllUser() {
        List<User> allUsers = userRepository.findAll();
        return allUsers;
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Integer id) {
//        userRepository.findById(id).ifPresent(user -> userRepository.delete(user));
        Optional<User> findUserById = userRepository.findById(id);
        if (findUserById.isPresent()) {
            User user = findUserById.get();
            userRepository.delete(user);
        }

//        User user = getUserById(id);
//        if (user != null) {
//            userRepository.delete(user);
//        }
    }

    @Override
    public boolean deleteUserById(Integer id) {
        Optional<User> findUserById = userRepository.findById(id);
        if (findUserById.isPresent()) {
            User user = findUserById.get();
            userRepository.delete(user);
            return true;
        } else {
            return false;
        }
    }


    @Override
    public User getUserById(Integer userId) {
        Optional<User> findByUserId = userRepository.findById(userId);
//        if(findByUserId.isPresent()) {
//            return findByUserId.get();
//        }
//
//        return null;

        return findByUserId.orElse(null);
    }
}
