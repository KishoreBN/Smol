package com.primeengineer.smol.controller;

import com.primeengineer.smol.dto.ClickEventResponse;
import com.primeengineer.smol.dto.UrlMappingDTO;
import com.primeengineer.smol.service.AnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/analytics")
public class AnalyticsController {
    @Autowired
    private AnalyticsService analyticsService;

    @GetMapping("/urlActivity/{shortUrl}")
    public ResponseEntity<List<ClickEventResponse>> urlActivity(@PathVariable String shortUrl,
                                                                @RequestParam String start,
                                                                @RequestParam String end) {
        return new ResponseEntity<>(analyticsService.getUrlAnalytics(shortUrl, LocalDateTime.parse(start), LocalDateTime.parse(end)), HttpStatus.OK);
    }

    @GetMapping("/urlActivity/user")
    public ResponseEntity<List<ClickEventResponse>> userUrlActivity(Principal principal,
                                                                    @RequestParam String start,
                                                                    @RequestParam String end) {
        return new ResponseEntity<>(analyticsService.getUserUrlAnalytics(principal, LocalDateTime.parse(start), LocalDateTime.parse(end)), HttpStatus.OK);
    }

    @GetMapping("/user/list")
    public ResponseEntity<List<UrlMappingDTO>> userMapping(Principal principal) {
        return new ResponseEntity<>(analyticsService.userMapping(principal), HttpStatus.OK);
    }

    @GetMapping("/user/overview")
    public ResponseEntity<List<UrlMappingDTO>> userOverview(Principal principal) {
        return new ResponseEntity<>(analyticsService.userOverview(principal), HttpStatus.OK);
    }
}
