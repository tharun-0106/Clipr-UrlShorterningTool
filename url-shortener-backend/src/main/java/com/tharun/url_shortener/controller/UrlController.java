package com.tharun.url_shortener.controller;

import com.tharun.url_shortener.models.Url;
import com.tharun.url_shortener.service.UrlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/urlShortener")
@RequiredArgsConstructor
public class UrlController {
    private final UrlService urlService;

    @PostMapping("/shorten")
    public ResponseEntity<Url> shortenUrl(@RequestBody String fullUrl){
        Url savedUrl = urlService.saveUrl(fullUrl);
        return new ResponseEntity<>(savedUrl,HttpStatus.CREATED);
    }

    @GetMapping("/{shortCode}")
    public ResponseEntity<Void> redirectToUrl(@PathVariable String shortCode){
        String originalUrl = urlService.getOriginalUrl(shortCode);
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(originalUrl)).build();
    }

    @GetMapping("/all")
    public ResponseEntity<List<Url>> getAllUrls(){
        return new ResponseEntity<>(urlService.getAllUrls(),HttpStatus.OK);
    }

    @DeleteMapping("/{shortCode}")
    public ResponseEntity<Void> deleteUrl(@PathVariable String shortCode){
        urlService.deleteUrl(shortCode);
        return ResponseEntity.noContent().build();
    }
}
