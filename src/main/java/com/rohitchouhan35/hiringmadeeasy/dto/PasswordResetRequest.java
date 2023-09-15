package com.rohitchouhan35.hiringmadeeasy.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PasswordResetRequest {

    @NotBlank(message = "Token is empty, please resend the reset request.")
    private String token;

    @NotBlank(message = "Please enter new password before proceeding further.")
    private String password;

}
