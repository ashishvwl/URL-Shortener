package com.aw.urlshortener.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity @Table(name = "urls")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Url {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String originalUrl;

    @Column(unique = true, nullable = false)
    private String shortCode;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    private long clickCount = 0;

    @CreationTimestamp
    private LocalDateTime createdAt;
}