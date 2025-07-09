package com.primeengineer.smol.dto;

import com.primeengineer.smol.model.Users;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Collections;

@Component
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsImpl implements UserDetails {
    private String username;
    private String password;
    private String email;
    private Collection<? extends GrantedAuthority> roles;
    private Long id;

    public static UserDetailsImpl build(Users users) {
        GrantedAuthority roles = new SimpleGrantedAuthority(users.getRole());
        return new UserDetailsImpl(users.getUsername(), users.getPassword(), users.getEmail(), Collections.singleton(roles), users.getId());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }
}
