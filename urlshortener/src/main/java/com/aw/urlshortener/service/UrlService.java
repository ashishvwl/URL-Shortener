package com.aw.urlshortener.service;

import com.aw.urlshortener.dto.ShortenRequest;
import com.aw.urlshortener.dto.UrlResponse;
import com.aw.urlshortener.entity.Url;
import com.aw.urlshortener.entity.User;
import com.aw.urlshortener.repository.UrlRepository;
import com.aw.urlshortener.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UrlService {
    private final UrlRepository urlRepository;
    private final UserRepository userRepository;
    private static final String CHARS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";

    public UrlResponse shorten(ShortenRequest req, String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
        String code = generateCode();
        Url url = Url.builder()
            .originalUrl(req.getOriginalUrl())
            .shortCode(code)
            .user(user)
            .build();
        urlRepository.save(url);
        // Cache it immediately
//        redisTemplate.opsForValue().set("url:" + code, req.getOriginalUrl(),
//            Duration.ofDays(7));
        return new UrlResponse(code, req.getOriginalUrl(), "http://localhost:8080/" + code);
    }

    public String resolve(String code) {
        // Check Redis first
//        String cached = redisTemplate.opsForValue().get("url:" + code);
//        if (cached != null) return cached;

        // Cache miss — go to DB
        Url url = urlRepository.findByShortCode(code)
            .orElseThrow(() -> new RuntimeException("URL not found"));
        // Populate cache for next time
//        redisTemplate.opsForValue().set("url:" + code, url.getOriginalUrl(),
//            Duration.ofDays(7));
        // Increment click count
        url.setClickCount(url.getClickCount() + 1);
        urlRepository.save(url);
        return url.getOriginalUrl();
    }

    public List<Url> getUserUrls(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("User not found"));
        return urlRepository.findByUserOrderByCreatedAtDesc(user);
    }

    private String generateCode() {
        String code;
        do {
            code = RandomStringUtils.randomAlphanumeric(6);
        } while (urlRepository.findByShortCode(code).isPresent());
        return code;
    }
}