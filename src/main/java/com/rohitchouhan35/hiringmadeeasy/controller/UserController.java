package com.rohitchouhan35.hiringmadeeasy.controller;

import com.rohitchouhan35.hiringmadeeasy.dto.UserDto;
import com.rohitchouhan35.hiringmadeeasy.mapper.UserMapper;
import com.rohitchouhan35.hiringmadeeasy.model.User;
import com.rohitchouhan35.hiringmadeeasy.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public List<UserDto> getUsers() {
        logger.info("Fetching list of users...");
        List<UserDto> usersDto = userService.getUsers().stream()
                .map(userMapper::toUserDto)
                .collect(Collectors.toList());
        logger.info("Fetched {} users.", usersDto.size());
        return usersDto;
    }

    @PostMapping("/saveUser")
    public ResponseEntity<String> saveUser(@RequestBody User user) {
        logger.info("Received request to save user: {}", user);
        userService.saveUser(user);
        logger.info("Saved user with ID: {}", user.getId());
        return new ResponseEntity<>("User saved successfully.", HttpStatus.OK);
    }
}
