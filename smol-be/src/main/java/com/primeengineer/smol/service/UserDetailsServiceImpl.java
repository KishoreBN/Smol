package com.primeengineer.smol.service;

import com.primeengineer.smol.dto.UserDetailsImpl;
import com.primeengineer.smol.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


@Component
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return UserDetailsImpl.build(userRepository.findByEmail(email)
                .orElseThrow(()-> new UsernameNotFoundException("User not found for : " + email)));
    }
}
