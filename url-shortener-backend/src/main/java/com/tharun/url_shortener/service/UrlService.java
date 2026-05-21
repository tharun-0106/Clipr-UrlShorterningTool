package com.tharun.url_shortener.service;

import com.tharun.url_shortener.exception.UrlNotFoundException;
import com.tharun.url_shortener.models.Url;
import com.tharun.url_shortener.repository.UrlRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UrlService {
    private final UrlRepository urlRepository;

    public Url saveUrl(String fullUrl){
        String shortCode = UUID.randomUUID().toString().substring(0, 8);
        Url url = new Url();
        url.setOriginalUrl(fullUrl);
        url.setShortCode(shortCode);
        url.setCreatedAt(LocalDateTime.now());
        urlRepository.save(url);
        return url;
    }

    public String getOriginalUrl(String shortCode){
        Url url = urlRepository.findByShortCode(shortCode).orElseThrow(()->new UrlNotFoundException("Short Code not found!"));
        url.incrementClickCount();
        urlRepository.save(url);
        return url.getOriginalUrl();
    }

    public List<Url> getAllUrls(){
        return urlRepository.findAll();
    }

    public void deleteUrl(String shortCode){
        Url url = urlRepository.findByShortCode(shortCode).orElseThrow(()->new UrlNotFoundException("Short Code not found!"));
        urlRepository.delete(url);
    }
}
