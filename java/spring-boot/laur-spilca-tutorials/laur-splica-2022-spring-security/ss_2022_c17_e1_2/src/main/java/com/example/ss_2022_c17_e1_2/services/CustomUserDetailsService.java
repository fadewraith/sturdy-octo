package com.example.ss_2022_c17_e1_2.services;

import com.example.ss_2022_c17_e1_2.model.SecurityUser;
import com.example.ss_2022_c17_e1_2.entities.User;
import com.example.ss_2022_c17_e1_2.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

  private final UserRepository userRepository;

  public CustomUserDetailsService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> user = userRepository.findByUsername(username);
    return user.map(SecurityUser::new).orElseThrow(() -> new UsernameNotFoundException(":("));
  }
}