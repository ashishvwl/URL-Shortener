package com.aw.urlshortener.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

// AuthResponse.java
@Data
@AllArgsConstructor
public class AuthResponse {
    private String token;
    private String email;
}