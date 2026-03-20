package com.aw.urlshortener.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

// LoginRequest.java
@Data
public class LoginRequest {
    @Email
    @NotBlank
    private String email;
    @NotBlank private String password;
}