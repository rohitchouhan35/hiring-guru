package com.rohitchouhan35.hiringmadeeasy.controller;

import com.rohitchouhan35.hiringmadeeasy.dto.BulkEmailRequest;
import com.rohitchouhan35.hiringmadeeasy.dto.SingleEmailRequest;
import com.rohitchouhan35.hiringmadeeasy.service.EmailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/email")
public class EmailController {

    private static final Logger log = LoggerFactory.getLogger(EmailController.class);

    @Autowired
    private EmailService emailService;

    @PostMapping("/sendEmail")
    public ResponseEntity<String> sendIndividualEmail(@RequestBody SingleEmailRequest emailRequest) {
        long startTime = System.currentTimeMillis();
        String emailAddress = emailRequest.getEmailAddress();
        String subject = emailRequest.getSubject();
        String body = emailRequest.getBody();

        try {
            emailService.sendEmail(emailAddress, subject, body);
            log.info("Individual email sent to: {}", emailAddress);
            log.info("sending individual emails completed: " + (System.currentTimeMillis() - startTime));
            return ResponseEntity.ok("Individual email sent.");
        } catch (Exception e) {
            log.error("Error sending individual email to {}: {}", emailAddress, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending individual email.");
        }
    }

    @PostMapping("/sendBulkEmails")
    public ResponseEntity<String> sendBulkEmails(@RequestBody BulkEmailRequest bulkEmailRequest) {
        long startTime = System.currentTimeMillis();
        String subject = bulkEmailRequest.getSubject();
        String body = bulkEmailRequest.getBody();
        // You might want to validate and process template context here

        try {
            emailService.sendBulkEmails(bulkEmailRequest.getUsers(), subject, body);
            log.info("Bulk emails sent to {} users", bulkEmailRequest.getUsers().size());
            log.info("sending bulk emails completed: " + (System.currentTimeMillis() - startTime));
            return ResponseEntity.ok("Bulk emails sending is done.");
        } catch (Exception e) {
            log.error("Error sending bulk emails: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending bulk emails.");
        }
    }

}
