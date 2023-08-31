package com.rohitchouhan35.hiringmadeeasy.service;

import com.rohitchouhan35.hiringmadeeasy.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    @Async
    public void sendBulkEmails(List<User> users, String subject, String body) {
        logger.info("Bulk email sending started for {} users", users.size());

        for (User user : users) {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(user.getEmail());
            message.setSubject(subject);
            message.setText(body);
            javaMailSender.send(message);
            logger.info("Email sent to {}", user.getEmail());
        }

        logger.info("Bulk email sending completed for {} users", users.size());
    }

    @Override
    public void sendEmail(String emailAddress, String subject, String body) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(emailAddress);
            message.setSubject(subject);
            message.setText(body);
            javaMailSender.send(message);
            logger.info("Email sent to {}", emailAddress);
        } catch (Exception e) {
            logger.error("Error sending email to {}: {}", emailAddress, e.getMessage());
        }
    }
}
