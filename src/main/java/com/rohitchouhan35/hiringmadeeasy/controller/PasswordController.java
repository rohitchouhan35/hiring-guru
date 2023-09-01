package com.rohitchouhan35.hiringmadeeasy.controller;

import com.rohitchouhan35.hiringmadeeasy.dto.PasswordChangeRequest;
import com.rohitchouhan35.hiringmadeeasy.exception.PasswordChangeException;
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

    @Autowired
    public void PasswordController(UserService userService) {
        this.userService = userService;
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