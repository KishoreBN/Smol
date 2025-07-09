package com.primeengineer.smol.service;

import com.primeengineer.smol.dto.JwtResponse;
import com.primeengineer.smol.dto.LoginUser;
import com.primeengineer.smol.dto.RegisterUser;
import com.primeengineer.smol.exception.UserAlreadyExists;
import com.primeengineer.smol.model.Users;
import com.primeengineer.smol.repository.UserRepository;
import com.primeengineer.smol.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * Service responsible for handling user authentication and registration.
 *
 * <p>This class provides methods to register new users and authenticate existing
 * users using Spring Security. Upon successful authentication, it issues a JWT token
 * for session management.</p>
 *
 * @author — bnk
 */
@Service
public class AuthService {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtil jwtUtil;

    /**
     * Registers a new user with the provided registration details.
     * <p>
     * The password is encoded before saving the user. If a user with the same username
     * already exists, registration fails and returns {@code null}.
     * </p>
     *
     * @param registerUser the user registration request containing username, email, and password
     * @return the created {@link Users} object if registration is successful, otherwise {@code null}
     */
    public Users register(RegisterUser registerUser) {
        Optional<Users> existingUser = userRepository.findByEmail(registerUser.getEmail());
        if (existingUser.isPresent()) throw new UserAlreadyExists("Account already exist with provided email.");
        Users users = new Users();
        users.setEmail(registerUser.getEmail());
        users.setPassword(passwordEncoder.encode(registerUser.getPassword()));
        users.setUsername(registerUser.getUsername());
        users.setRole("USER");
        return userRepository.save(users);
    }

    /**
     * Authenticates a user using email and password credentials.
     * <p>
     * If authentication is successful, returns a JWT token to be used in subsequent requests.
     * </p>
     *
     * @param loginUser the login request containing email and password
     * @return a {@link JwtResponse} containing the generated JWT token
     */
    public JwtResponse login(LoginUser loginUser) {
        Authentication authenticate = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginUser.getEmail(), loginUser.getPassword()));
        UserDetails principal = (UserDetails) authenticate.getPrincipal();
        return new JwtResponse(jwtUtil.generateToken(principal));
    }
}
