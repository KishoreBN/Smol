package com.primeengineer.smol.service;

import com.primeengineer.smol.dto.JwtResponse;
import com.primeengineer.smol.dto.LoginUser;
import com.primeengineer.smol.dto.PasswordResetRequest;
import com.primeengineer.smol.dto.RegisterUser;
import com.primeengineer.smol.exception.EmailDoesNotExist;
import com.primeengineer.smol.exception.InvalidPasswordResetToken;
import com.primeengineer.smol.exception.UserAlreadyExists;
import com.primeengineer.smol.exception.UserDoesNotExist;
import com.primeengineer.smol.model.PasswordResetTokens;
import com.primeengineer.smol.model.Users;
import com.primeengineer.smol.repository.PasswordResetTokenRepository;
import com.primeengineer.smol.repository.UrlMappingRepository;
import com.primeengineer.smol.repository.UserRepository;
import com.primeengineer.smol.security.JwtUtil;
import com.primeengineer.smol.util.Constants;
import com.primeengineer.smol.util.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    @Autowired
    private JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String emailId;
    @Autowired
    private Utility utility;
    @Autowired
    private PasswordResetTokenRepository passwordResetTokenRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private OTPService otpService;

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


    /**
     * Sends a password reset verification email to the user with the given registered email address.
     *
     * <p>This method performs the following steps:
     * <ol>
     *     <li>Finds the user by email. If the user does not exist, throws {@link EmailDoesNotExist}.</li>
     *     <li>Generates a unique password reset token and saves it in the database with an expiration time.</li>
     *     <li>Composes a reset message and sends it via the configured email service.</li>
     * </ol>
     *
     * <p>The token is valid for 10 minutes and can be used only once.
     *
     * @param registeredEmail the email address of the user requesting password reset
     * @return a constant string confirming that the verification email has been sent
     * @throws EmailDoesNotExist if no user is found with the given email
     */
    @Transactional
    public String sendVerificationEmail(String registeredEmail) {
        Users user = userRepository.findByEmail(registeredEmail).orElseThrow(()-> new EmailDoesNotExist());
        int token = otpService.getOtp();
        Optional<PasswordResetTokens> userToken = passwordResetTokenRepository.findByUser(user);
        if (userToken.isPresent()) {
            PasswordResetTokens passwordResetTokens = userToken.get();
            passwordResetTokens.setToken(token);
            passwordResetTokens.setUsed(false);
            passwordResetTokenRepository.save(passwordResetTokens);
        } else {
            PasswordResetTokens passwordResetToken = PasswordResetTokens
                    .builder()
                    .user(user)
                    .used(false)
                    .token(token)
                    .expireAt(LocalDateTime.now().plusMinutes(10))
                    .build();
            passwordResetTokenRepository.save(passwordResetToken);
        }
        String message = utility.getPasswordResetMessage(token, registeredEmail);
        emailService.sendVerificationEmail(registeredEmail, Constants.PASSWORD_RESET, message);
        return Constants.VERIFICATION_EMAIL_SENT;
    }

    @Transactional
    public String passwordReset(PasswordResetRequest passwordResetRequest) {
        Users user = userRepository.findByEmail(passwordResetRequest.getEmail()).orElseThrow(() -> new EmailDoesNotExist());
        PasswordResetTokens token = passwordResetTokenRepository.findByUser(user).orElseThrow(() -> new InvalidPasswordResetToken());
        if (!otpService.validateOtp(passwordResetRequest.getToken(), token)) throw new InvalidPasswordResetToken();
        user.setPassword(passwordEncoder.encode(passwordResetRequest.getPassword()));
        userRepository.save(user);
        token.setUsed(true);
        passwordResetTokenRepository.save(token);
        return "Password successfully reset";
    }
}
