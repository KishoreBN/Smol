package com.primeengineer.smol.service;

import com.primeengineer.smol.exception.InvalidPasswordResetToken;
import com.primeengineer.smol.model.PasswordResetTokens;
import com.primeengineer.smol.model.Users;
import com.primeengineer.smol.repository.PasswordResetTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;

@Component
public class OTPService {
    private SecureRandom secureRandom;
    private PasswordResetTokenRepository passwordResetTokenRepository;
    @Value("${secure.otp.maxlength}")
    private int maxLength;

    @Autowired
    OTPService(SecureRandom secureRandom, PasswordResetTokenRepository passwordResetTokenRepository) {
        this.secureRandom = secureRandom;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }

    public int getOtp() {
        return secureRandom.nextInt(this.maxLength);
    }

    public boolean validateOtp(Users user, Integer otp) {
        PasswordResetTokens issuedOtp = passwordResetTokenRepository.findByUser(user).orElseThrow(() -> new InvalidPasswordResetToken());
        return issuedOtp.getToken().equals(otp);
    }

    public boolean validateOtp(Integer otp, PasswordResetTokens passwordResetToken) {
        return passwordResetToken.getToken().equals(otp) && !passwordResetToken.getUsed();
    }
}
