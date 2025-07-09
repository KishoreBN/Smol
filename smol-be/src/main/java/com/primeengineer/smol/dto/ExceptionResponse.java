package com.primeengineer.smol.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class ExceptionResponse {
    private LocalDateTime timestamp;
    private Long status;
    private String error;
}
