package com.rohitchouhan35.hiringmadeeasy.controller;

import com.rohitchouhan35.hiringmadeeasy.dto.ForgotPasswordRequest;
import com.rohitchouhan35.hiringmadeeasy.dto.PasswordChangeRequest;
import com.rohitchouhan35.hiringmadeeasy.dto.PasswordResetRequest;
import com.rohitchouhan35.hiringmadeeasy.exception.PasswordChangeException;
import com.rohitchouhan35.hiringmadeeasy.service.ForgotPasswordService;
import com.rohitchouhan35.hiringmadeeasy.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/password")
public class PasswordController {

    private static final Logger log = LoggerFactory.getLogger(EmailController.class);

    private UserService userService;
    private ForgotPasswordService forgotPasswordService;

    @Autowired
    public void PasswordController(UserService userService, ForgotPasswordService forgotPasswordService) {
        this.userService = userService;
        this.forgotPasswordService = forgotPasswordService;
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(@RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        String email = forgotPasswordRequest.getEmail();
        log.info("Received forgot password request for email: {}", email);

        try {
            forgotPasswordService.initiatePasswordReset(email);
//            log.info("Password reset email sent successfully to {}", email);
            return ResponseEntity.ok("Password reset email sent successfully.");
        } catch (Exception e) {
            log.error("Failed to initiate password reset for email {}: {}", email, e.getMessage());
            return ResponseEntity.badRequest().body("Failed to initiate password reset.");
        }
    }

//    @RequestParam("token") String token, @RequestParam("password") String newPassword
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody PasswordResetRequest passwordResetRequest) {
        String token = passwordResetRequest.getToken();
        String newPassword = passwordResetRequest.getPassword();

        if (forgotPasswordService.isTokenValid(token)) {
            try {
                forgotPasswordService.resetPassword(token, newPassword);
                return ResponseEntity.ok("Password reset successfully");
            } catch (Exception e) {
                // Handle exceptions that might occur during password reset
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Password reset failed.");
            }
        } else {
            return ResponseEntity.badRequest().body("Invalid or expired token");
        }
    }

    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody PasswordChangeRequest requestDTO) {

        String username = requestDTO.getUsername();
        String currentPassword = requestDTO.getCurrentPassword();
        String newPassword = requestDTO.getNewPassword();

        log.info("Received request to change password for user: {}", username);

        try {
            userService.changePassword(username, currentPassword, newPassword);
            log.info("Password changed successfully for user: {}", username);
            return new ResponseEntity<>("Password changed successfully.", HttpStatus.OK);
        } catch (PasswordChangeException e) {
            log.error("Error changing password for user {}: {}", username, e.getMessage());
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
