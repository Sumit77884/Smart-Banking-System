package com.banking.controller;

import com.banking.config.JwtUtil;
import com.banking.model.User;
import com.banking.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.*;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private UserDetailsService userDetailsService;
    @Autowired private UserRepository userRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> request) {
        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.get("username"), request.get("password"))
            );
            UserDetails userDetails = userDetailsService.loadUserByUsername(request.get("username"));
            String token = jwtUtil.generateToken(userDetails);
            User user = userRepository.findByUsername(request.get("username")).orElseThrow();
            return ResponseEntity.ok(Map.of(
                "token", token,
                "username", user.getUsername(),
                "email", user.getEmail(),
                "role", user.getRole().name(),
                "fullName", user.getFullName() != null ? user.getFullName() : ""
            ));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(401).body(Map.of("error", "Invalid username or password"));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Map<String, String> request) {
        if (userRepository.existsByUsername(request.get("username"))) {
            return ResponseEntity.badRequest().body(Map.of("error", "Username already exists"));
        }
        if (userRepository.existsByEmail(request.get("email"))) {
            return ResponseEntity.badRequest().body(Map.of("error", "Email already exists"));
        }

        User user = User.builder()
                .username(request.get("username"))
                .email(request.get("email"))
                .password(passwordEncoder.encode(request.get("password")))
                .fullName(request.get("fullName"))
                .phone(request.get("phone"))
                .address(request.get("address"))
                .role(User.Role.USER)
                .build();

        userRepository.save(user);
        return ResponseEntity.ok(Map.of("message", "User registered successfully"));
    }
}
