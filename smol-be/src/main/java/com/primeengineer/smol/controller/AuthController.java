package com.primeengineer.smol.controller;

import com.primeengineer.smol.dto.JwtResponse;
import com.primeengineer.smol.dto.LoginUser;
import com.primeengineer.smol.dto.RegisterUser;
import com.primeengineer.smol.model.Users;
import com.primeengineer.smol.service.AuthService;
import com.primeengineer.smol.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterUser registerUser) {
        Users register = authService.register(registerUser);
        if (register != null) return new ResponseEntity<>(Constants.USER_CREATED, HttpStatus.CREATED);
        return new ResponseEntity<>(Constants.ACCOUNT_EXISTS, HttpStatus.BAD_REQUEST);
    }

    @PostMapping("/login")
    public ResponseEntity<JwtResponse> login(@RequestBody LoginUser loginUser) {
        return new ResponseEntity<>(authService.login(loginUser), HttpStatus.OK);
    }

}
