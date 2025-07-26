package com.primeengineer.smol.repository;

import com.primeengineer.smol.model.PasswordResetTokens;
import com.primeengineer.smol.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetTokens, Long> {
    Optional<PasswordResetTokens> findByUser(Users user);
}
