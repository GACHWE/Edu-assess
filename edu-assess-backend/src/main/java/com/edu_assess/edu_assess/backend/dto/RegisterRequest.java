package com.edu_assess.edu_assess.backend.dto;

import com.edu_assess.edu_assess.backend.entity.Role;
import lombok.Data;

@Data
public class RegisterRequest {
    private String email;
    private String password;
    private String fullName;
    private Role role;
}