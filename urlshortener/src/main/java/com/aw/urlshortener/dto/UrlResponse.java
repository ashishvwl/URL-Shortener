package com.aw.urlshortener.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

// UrlResponse.java
@Data
@AllArgsConstructor
public class UrlResponse {
    private String shortCode;
    private String originalUrl;
    private String shortUrl;
}