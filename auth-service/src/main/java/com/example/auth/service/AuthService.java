package com.example.auth.service;

import com.example.auth.model.User;
import com.example.auth.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthService {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final TokenService tokenService;

  public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, TokenService tokenService) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.tokenService = tokenService;
  }

  public void register(String email, String rawPassword) {
    String hash = passwordEncoder.encode(rawPassword);
    User user = new User();
    user.setEmail(email);
    user.setPasswordHash(hash);
    user.setRoles("USER");
    userRepository.insert(user);
  }

  public String login(String email, String rawPassword) {
    User user = userRepository.findByEmail(email)
        .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));
    if (!passwordEncoder.matches(rawPassword, user.getPasswordHash())) {
      throw new IllegalArgumentException("Invalid credentials");
    }
    return tokenService.generate(String.valueOf(user.getId()), Map.of("email", user.getEmail(), "roles", user.getRoles()));
  }
}
