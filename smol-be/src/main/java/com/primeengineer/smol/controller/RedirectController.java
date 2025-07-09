package com.primeengineer.smol.controller;

import com.primeengineer.smol.service.UrlMappingService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class RedirectController {
    @Autowired
    private UrlMappingService urlMappingService;

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirectUrl(@PathVariable String shortUrl) throws IOException {
        String originalUrl = urlMappingService.getOriginalUrl(shortUrl);
        if (originalUrl != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", originalUrl);
            return ResponseEntity.status(302).headers(headers).build();
        }
        return ResponseEntity.notFound().build();
    }
}
