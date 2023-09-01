package com.rohitchouhan35.hiringmadeeasy.service;

import com.rohitchouhan35.hiringmadeeasy.exception.PasswordChangeException;
import com.rohitchouhan35.hiringmadeeasy.exception.UserNotFoundException;
import com.rohitchouhan35.hiringmadeeasy.model.User;
import com.rohitchouhan35.hiringmadeeasy.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl() {
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Override
    public List<User> getUsers() {
        log.info("Fetching all users.");
        return userRepository.findAll();
    }

    @Override
    public User saveUser(User user) {
        log.info("Saving user: {}", user.getUsername());
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        log.info("Fetching user by username: {}", username);
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean hasUserWithUsername(String username) {
        log.info("Checking if user with username exists: {}", username);
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean hasUserWithEmail(String email) {
        log.info("Checking if user with email exists: {}", email);
        return userRepository.existsByEmail(email);
    }

    @Override
    public void changePassword(String username, String currentPassword, String newPassword) {
        log.info("Changing password for user: {}", username);
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (!optionalUser.isPresent()) {
            log.error("User not found: {}", username);
            throw new UserNotFoundException("User not found");
        }
        User user = optionalUser.get();
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            log.error("Incorrect current password for user: {}", username);
            throw new PasswordChangeException("Incorrect current password");
        }

        String newPasswordEncoded = passwordEncoder.encode(newPassword);
        user.setPassword(newPasswordEncoded);

        userRepository.save(user);
        log.info("Password changed successfully for user: {}", username);
    }

}
