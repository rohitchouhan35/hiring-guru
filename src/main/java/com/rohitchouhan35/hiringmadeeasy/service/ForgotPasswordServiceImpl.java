package com.rohitchouhan35.hiringmadeeasy.service;

import com.rohitchouhan35.hiringmadeeasy.exception.EmailSendingException;
import com.rohitchouhan35.hiringmadeeasy.exception.InvalidTokenException;
import com.rohitchouhan35.hiringmadeeasy.exception.UserNotFoundException;
import com.rohitchouhan35.hiringmadeeasy.model.PasswordResetToken;
import com.rohitchouhan35.hiringmadeeasy.model.User;
import com.rohitchouhan35.hiringmadeeasy.repository.PasswordResetTokenRepository;
import com.rohitchouhan35.hiringmadeeasy.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

@Service
public class ForgotPasswordServiceImpl implements ForgotPasswordService {

    private static final Logger log = LoggerFactory.getLogger(ForgotPasswordServiceImpl.class);

    private final UserRepository userRepository;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    @Value("${email.validity.hours}")
    private int emailValidityHours;

    @Autowired
    public ForgotPasswordServiceImpl(UserRepository userRepository, PasswordResetTokenRepository passwordResetTokenRepository, EmailService emailService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
        this.emailService = emailService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void initiatePasswordReset(String email) {
        log.info("Initiating password reset for email: {}", email);

        // 1. Check if the user with the given email exists.
        Optional<User> userOptional = userRepository.findByEmail(email);
        if (userOptional.isEmpty()) {
            log.error("User with email {} not found.", email);
            throw new UserNotFoundException("User with email " + email + " not found.");
        }

        User user = userOptional.get();

        // 2. Generate a secure reset token (e.g., UUID).
        String resetToken = UUID.randomUUID().toString();

        // 3. Save the token in the database with an expiration time.
        Date expirationDate = calculateExpirationDate();
        PasswordResetToken tokenEntity = new PasswordResetToken();
        tokenEntity.setToken(resetToken);
        tokenEntity.setExpirationDate(expirationDate);
        tokenEntity.setUser(user);
        passwordResetTokenRepository.save(tokenEntity);

        // 4. Send an email to the user with a link to reset the password, including the token.
        String resetLink = "http://localhost:3512/password/reset-password?token=" + resetToken; // Adjust the URL accordingly
        String emailContent = "Click the following link to reset your password: " + resetLink;

        try {
            emailService.sendEmail(user.getEmail(), "Password Reset", emailContent);
            log.info("Password reset email sent successfully to {}", email);
        } catch (Exception e) {
            log.error("Failed to send the password reset email to {}", email, e);
            throw new EmailSendingException("Failed to send the password reset email.");
        }
    }

    private Date calculateExpirationDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, emailValidityHours);
        return calendar.getTime();
    }

    @Override
    public boolean isTokenValid(String token) {
        log.info("Validating reset token: {}", token);

        // 1. Retrieve the token entity from the database based on the token.
        Optional<PasswordResetToken> tokenEntityOptional = passwordResetTokenRepository.findByToken(token);

        // 2. Check if the token entity exists and is not expired.
        if (!tokenEntityOptional.isEmpty()) {
            PasswordResetToken tokenEntity = tokenEntityOptional.get();
            Date expirationDate = tokenEntity.getExpirationDate();
            Date now = new Date();

            // Calculate the time difference in milliseconds
            long timeDifference = expirationDate.getTime() - now.getTime();

            // Check if the token is not expired
            if (timeDifference > 0) {
                log.info("Token {} is valid.", token);
                return true;
            }
        }

        log.error("Token {} is invalid or expired.", token);
        return false;
    }

    @Override
    public void resetPassword(String token, String newPassword) {
        log.info("Resetting password for token: {}", token);

        // 1. Retrieve the token entity from the database based on the token.
        Optional<PasswordResetToken> tokenEntityOptional = passwordResetTokenRepository.findByToken(token);

        // 2. Check if the token entity exists and is not expired.
        if (tokenEntityOptional.isPresent() && isTokenValid(tokenEntityOptional.get().getToken())) {
            PasswordResetToken tokenEntity = tokenEntityOptional.get();
            User user = tokenEntity.getUser();

            // 3. Update the user's password with the new password.
            user.setPassword(passwordEncoder.encode(newPassword));
            userRepository.save(user);

            // 4. Delete the used token entity from the database.
            passwordResetTokenRepository.delete(tokenEntity);

            log.info("Password reset successful for token {}", token);
        } else {
            // Token is invalid or expired, throw an exception or handle as needed.
            log.error("Invalid or expired token {}", token);
            throw new InvalidTokenException("Invalid or expired token");
        }
    }
}
