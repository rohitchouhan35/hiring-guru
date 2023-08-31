package com.rohitchouhan35.hiringmadeeasy.dto;

import com.rohitchouhan35.hiringmadeeasy.model.User;
import lombok.Data;
import org.thymeleaf.context.Context;

import java.util.List;

@Data
public class BulkEmailRequest {

    private String subject;
    private String Body;
    private List<User> users;
//    private Context context; // Thymeleaf context for template

}
