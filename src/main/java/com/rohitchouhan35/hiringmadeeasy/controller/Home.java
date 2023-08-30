package com.rohitchouhan35.hiringmadeeasy.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@CrossOrigin("*")
@RestController
@RequestMapping("/")
public class Home {

    @GetMapping
    public String home(){
        return "Hi, time now is: " + LocalDateTime.now();
    }

}
