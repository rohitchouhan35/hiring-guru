package com.rohitchouhan35.hiringmadeeasy.service;

import com.rohitchouhan35.hiringmadeeasy.model.User;

import java.util.List;

public interface EmailService {

    public void sendBulkEmails(List<User> users, String subject, String body);
    public void sendEmail(String emailAddress, String subject, String body);

}
