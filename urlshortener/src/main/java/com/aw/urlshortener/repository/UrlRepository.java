package com.aw.urlshortener.repository;

import com.aw.urlshortener.entity.Url;
import com.aw.urlshortener.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url, Long> {
    Optional<Url> findByShortCode(String shortCode);
    List<Url> findByUserOrderByCreatedAtDesc(User user);
}