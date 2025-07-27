package com.primeengineer.smol.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMailMessage;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

/**
 * Service class responsible for sending emails asynchronously.
 * <p>
 * Uses {@link JavaMailSender} to send simple text-based emails, and delegates execution to a provided {@link Executor}
 * so that email sending does not block the main application thread.
 */
@Service
public class EmailService {
    private JavaMailSender javaMailSender;
    private Executor executor;
    @Value("${spring.mail.username}")
    private String emailId;

    @Autowired
    EmailService(JavaMailSender javaMailSender, Executor executor) {
        this.javaMailSender = javaMailSender;
        this.executor = executor;
    }

    /**
     * Sends a verification email to the specified recipient asynchronously.
     * <p>
     * The method prepares a {@link SimpleMailMessage} using the provided subject and body, and sends it via
     * {@link JavaMailSender}. The operation runs on a separate thread using the injected {@link Executor}.
     *
     * @param toAddress the email address of the recipient
     * @param subject the subject of the email
     * @param body the body content of the email
     * @return a {@link CompletableFuture} that completes with {@code true} upon successful email sending
     */
    public CompletableFuture<?> sendVerificationEmail(String toAddress, String subject, String body) {
        return CompletableFuture.supplyAsync(() -> {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            try {
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
                helper.setTo(toAddress);
                helper.setFrom(emailId);
                helper.setSubject(subject);
                helper.setText(body, true);
                javaMailSender.send(mimeMessage);
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
            return true;
        }, executor);
    }
}
