package com.aw.urlshortener.controller;

import com.aw.urlshortener.dto.ShortenRequest;
import com.aw.urlshortener.dto.UrlResponse;
import com.aw.urlshortener.entity.Url;
import com.aw.urlshortener.service.UrlService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class UrlController {
    private final UrlService urlService;

    @PostMapping("/api/urls/shorten")
    public ResponseEntity<UrlResponse> shorten(
            @RequestBody @Valid ShortenRequest req,
            @AuthenticationPrincipal UserDetails userDetails) {
        log.info("UsrDetails: {}", userDetails);
        return ResponseEntity.ok(urlService.shorten(req, userDetails.getUsername()));
    }

    @GetMapping("/{code}")
    public ResponseEntity<Void> redirect(@PathVariable String code) {
        String originalUrl = urlService.resolve(code);
        return ResponseEntity.status(HttpStatus.FOUND)
            .header("Location", originalUrl)
            .build();
    }

    @GetMapping("/api/urls")
    public ResponseEntity<List<Url>> myUrls(
            @AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(urlService.getUserUrls(userDetails.getUsername()));
    }
}