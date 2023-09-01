package com.rohitchouhan35.hiringmadeeasy.controller;

import com.rohitchouhan35.hiringmadeeasy.dto.AuthResponse;
import com.rohitchouhan35.hiringmadeeasy.dto.LoginRequest;
import com.rohitchouhan35.hiringmadeeasy.dto.SignUpRequest;
import com.rohitchouhan35.hiringmadeeasy.exception.DuplicatedUserInfoException;
import com.rohitchouhan35.hiringmadeeasy.model.User;
import com.rohitchouhan35.hiringmadeeasy.security.TokenProvider;
import com.rohitchouhan35.hiringmadeeasy.security.WebSecurityConfig;
import com.rohitchouhan35.hiringmadeeasy.service.UserService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    @PostMapping("/authenticate")
    public AuthResponse login(@RequestBody @Valid LoginRequest loginRequest) {
        log.info("Received login request for user: {}", loginRequest.getUsername());
        String token = authenticateAndGetToken(loginRequest.getUsername(), loginRequest.getPassword());
        log.info("User {} authenticated successfully.", loginRequest.getUsername());
        return new AuthResponse(loginRequest.getUsername(), token);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/signup")
    public AuthResponse signUp(@Valid @RequestBody SignUpRequest signUpRequest) {
        log.info("Received signup request for user: {}", signUpRequest.getUsername());

        if (userService.hasUserWithUsername(signUpRequest.getUsername())) {
            log.error("Username {} is already in use.", signUpRequest.getUsername());
            throw new DuplicatedUserInfoException(String.format("Username %s already been used", signUpRequest.getUsername()));
        }
        if (userService.hasUserWithEmail(signUpRequest.getEmail())) {
            log.error("Email {} is already in use.", signUpRequest.getEmail());
            throw new DuplicatedUserInfoException(String.format("Email %s already been used", signUpRequest.getEmail()));
        }

        User user = mapSignUpRequestToUser(signUpRequest);
        userService.saveUser(user);

        log.info("User {} registered successfully.", signUpRequest.getUsername());
        String token = authenticateAndGetToken(signUpRequest.getUsername(), signUpRequest.getPassword());
        return new AuthResponse(signUpRequest.getUsername(), token);
    }

    private String authenticateAndGetToken(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        return tokenProvider.generate(authentication);
    }

    private User mapSignUpRequestToUser(SignUpRequest signUpRequest) {
        User user = new User();
        user.setUsername(signUpRequest.getUsername());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setRole(WebSecurityConfig.USER);
        return user;
    }
}
