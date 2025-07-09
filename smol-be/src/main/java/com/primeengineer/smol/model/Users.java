package com.primeengineer.smol.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;

@Entity
@Table(name = "users")
@Getter
@Data
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String email;
    private String password;
    private String role;
    private String username;
}
