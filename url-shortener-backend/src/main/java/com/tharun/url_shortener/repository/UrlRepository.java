package com.tharun.url_shortener.repository;

import com.tharun.url_shortener.models.Url;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UrlRepository extends JpaRepository<Url,Long> {
    Optional<Url> findByShortCode(String shortCode);
}
