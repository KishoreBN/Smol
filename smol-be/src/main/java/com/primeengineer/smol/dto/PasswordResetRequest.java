package com.primeengineer.smol.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class PasswordResetRequest {
    private String email;
    private Integer token;
    private String password;
}
