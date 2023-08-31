package com.rohitchouhan35.hiringmadeeasy.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SingleEmailRequest {

    @NotBlank(message = "The email address is required.")
    private String emailAddress;

    @NotBlank(message = "Subject of email is required.")
    private String subject;

    @NotBlank(message = "Body cannot be empty.")
    private String body;

}
