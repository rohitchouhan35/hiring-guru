package com.rohitchouhan35.hiringmadeeasy.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PasswordChangeRequest {

    @NotBlank(message = "The username is required.")
    private String username;

    @NotBlank(message = "Current password is required.")
    private String currentPassword;

    @NotBlank(message = "New password is required.")
    private String newPassword;

}