package com.edu_assess.edu_assess.backend.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String password;
}