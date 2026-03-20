package com.aw.urlshortener.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

// ShortenRequest.java
@Data
public class ShortenRequest {
    @NotBlank
    @URL
    private String originalUrl;
}
