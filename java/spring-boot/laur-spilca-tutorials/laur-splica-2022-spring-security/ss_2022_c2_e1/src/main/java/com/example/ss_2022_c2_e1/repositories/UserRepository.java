package com.example.ss_2022_c2_e1.repositories;

import com.example.ss_2022_c2_e1.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

// @Repository is not to be used in interfaces, there purpose is to create an instance of type in contexts
// https://youtu.be/dFvbHZ8CuKM?list=PLEocw3gLFc8X_a8hGWGaBnSkPFJmbb8QP&t=1477
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("""
            SELECT u FROM User u WHERE u.username = :username
            """)
    Optional<User> findUserByUsername(String username);
}
