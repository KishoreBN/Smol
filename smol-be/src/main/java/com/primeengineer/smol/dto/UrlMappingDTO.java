package com.primeengineer.smol.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
public class UrlMappingDTO {
    private Long id;
    private String originalUrl;
    private String shortUrl;
    private String title;
    private String description;
    private Long clickCount;
    private Long maxClick;
    private LocalDateTime expirationDate;
    private LocalDateTime createdDate;
}
