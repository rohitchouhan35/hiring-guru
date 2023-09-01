package com.rohitchouhan35.hiringmadeeasy.service;

public interface ForgotPasswordService {

    void initiatePasswordReset(String email);

    boolean isTokenValid(String token);

    void resetPassword(String token, String newPassword);
}
