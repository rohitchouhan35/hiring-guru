package com.rohitchouhan35.hiringmadeeasy.service;

import com.rohitchouhan35.hiringmadeeasy.annotations.MeasureExecutionTime;
import com.rohitchouhan35.hiringmadeeasy.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    private JavaMailSender javaMailSender;

    private final ExecutorService executorService = Executors.newFixedThreadPool(20);

    @Override
    @Async
    @MeasureExecutionTime
    public void sendBulkEmails(List<User> users, String subject, String body) {
        logger.info("Bulk email sending started for {} users", users.size());

        List<CompletableFuture<Void>> futures = users.stream()
                .map(user -> CompletableFuture.runAsync(() -> sendEmail(user.getEmail(), subject, body), executorService))
                .collect(Collectors.toList());

        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));

        try {
            allOf.get(); // Wait for all tasks to complete
        } catch (InterruptedException | ExecutionException e) {
            logger.error("Error waiting for email sending tasks: {}", e.getMessage());
        }

        logger.info("Bulk email sending completed for {} users", users.size());

//        CountDownLatch latch = new CountDownLatch(users.size());
//
//        for (User user : users) {
//            executorService.execute(() -> {
//                sendEmail(user.getEmail(), subject, body);
//                latch.countDown();
//            });
//        }
//
//        try {
//            latch.await(); // Wait for all tasks to complete
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//            logger.error("Error waiting for email sending tasks: {}", e.getMessage());
//        }

    }

    @Override
    @MeasureExecutionTime
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
