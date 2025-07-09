package com.primeengineer.smol.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class CompressRequest {
    private Long id;
    private String longUrl;
    private String title;
    private String description;
    private Long maxClick;
    private LocalDateTime expirationDate;
}
