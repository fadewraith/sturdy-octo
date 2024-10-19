package com.usermanagementsystem.controller;

import com.usermanagementsystem.model.User;
import com.usermanagementsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
//@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/save")
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        User saveUser = userService.saveUser(user);
        if(ObjectUtils.isEmpty(saveUser)) {
            return new ResponseEntity<>("User not saved", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(saveUser, HttpStatus.CREATED);
    }

    @GetMapping("/getUsers")
    public ResponseEntity<?> getAllUsers() {
        List<User> allUsers = userService.getAllUser();
        if(allUsers.isEmpty()) {
            return new ResponseEntity<>("no users found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(allUsers, HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody User user) {
//        User saveUser = userService.saveUser(user);
        User saveUser = userService.updateUser(user);
        if(ObjectUtils.isEmpty(saveUser)) {
            return new ResponseEntity<>("User not updated", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(saveUser, HttpStatus.OK);
    }

    @GetMapping("/getUser/{id}")
    public ResponseEntity<?> findUserById(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        if(ObjectUtils.isEmpty(user)) {
            return new ResponseEntity<>("user not found", HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

//    @DeleteMapping("/delete/{id}")
//    public ResponseEntity<?> deleteUserById(@PathVariable Integer id) {
//        userService.deleteUser(id);
//        return new ResponseEntity<>("user deleted successfully", HttpStatus.OK);
//    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable Integer id) {
        if (userService.deleteUserById(id)) {
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
        }
    }

}
