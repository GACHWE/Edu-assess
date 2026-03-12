package com.edu_assess.edu_assess.backend.controller;


import com.edu_assess.edu_assess.backend.config.JWTTokenHelper;
import com.edu_assess.edu_assess.backend.dto.AuthResponse;
import com.edu_assess.edu_assess.backend.dto.LoginRequest;
import com.edu_assess.edu_assess.backend.dto.RegisterRequest;
import com.edu_assess.edu_assess.backend.entity.User;
import com.edu_assess.edu_assess.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTTokenHelper jwtTokenHelper;
    private final UserDetailsService userDetailsService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email already exists");
        }

        User user = User.builder()
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .role(request.getRole())
                .build();
        userRepository.save(user);

        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        String token = jwtTokenHelper.generateToken(userDetails);

        return ResponseEntity.ok(new AuthResponse(token, user.getRole().name(), user.getFullName()));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        UserDetails userDetails = userDetailsService.loadUserByUsername(request.getEmail());
        String token = jwtTokenHelper.generateToken(userDetails);

        User user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        return ResponseEntity.ok(new AuthResponse(token, user.getRole().name(), user.getFullName()));
    }
}