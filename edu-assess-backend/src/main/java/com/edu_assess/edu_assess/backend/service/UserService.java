package com.edu_assess.edu_assess.backend.service;


import com.edu_assess.edu_assess.backend.entity.User;
import com.edu_assess.edu_assess.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Additional user-related methods...
}
