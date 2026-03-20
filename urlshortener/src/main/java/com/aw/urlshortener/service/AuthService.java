package com.aw.urlshortener.service;

import com.aw.urlshortener.dto.AuthResponse;
import com.aw.urlshortener.dto.LoginRequest;
import com.aw.urlshortener.dto.RegisterRequest;
import com.aw.urlshortener.entity.User;
import com.aw.urlshortener.repository.UserRepository;
import com.aw.urlshortener.utils.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthResponse register(@Valid  RegisterRequest req) {
        if (userRepository.findByEmail(req.getEmail()).isPresent())
            throw new RuntimeException("Email already in use");
        User user = User.builder()
            .email(req.getEmail())
            .password(passwordEncoder.encode(req.getPassword()))
            .name(req.getName())
            .build();
        userRepository.save(user);
        return new AuthResponse(jwtUtil.generateToken(user.getEmail()), user.getEmail());
    }

    public AuthResponse login(@Valid LoginRequest req) {
        User user = userRepository.findByEmail(req.getEmail())
            .orElseThrow(() -> new RuntimeException("Invalid credentials"));
        if (!passwordEncoder.matches(req.getPassword(), user.getPassword()))
            throw new RuntimeException("Invalid credentials");
        return new AuthResponse(jwtUtil.generateToken(user.getEmail()), user.getEmail());
    }
}