package com.rohitchouhan35.hiringmadeeasy.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ForgotPasswordRequest {

    @NotBlank(message = "email is required.")
    private String email;

}
