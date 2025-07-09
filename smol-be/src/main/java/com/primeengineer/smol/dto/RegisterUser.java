package com.primeengineer.smol.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
public class RegisterUser {
    private String username;
    private String email;
    private String password;
}
