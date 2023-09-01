package com.rohitchouhan35.hiringmadeeasy.service;

import com.rohitchouhan35.hiringmadeeasy.exception.PasswordChangeException;
import com.rohitchouhan35.hiringmadeeasy.exception.UserNotFoundException;
import com.rohitchouhan35.hiringmadeeasy.model.User;
import com.rohitchouhan35.hiringmadeeasy.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public boolean hasUserWithUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean hasUserWithEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public void changePassword(String username, String currentPassword, String newPassword) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (!optionalUser.isPresent()) {
            throw new UserNotFoundException("User not found");
        }
        User user = optionalUser.get();
        if (!passwordEncoder.matches(currentPassword, user.getPassword())) {
            throw new PasswordChangeException("Incorrect current password");
        }

        String newPasswordEncoded = passwordEncoder.encode(newPassword);
        user.setPassword(newPasswordEncoded);

        userRepository.save(user);
    }

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


}
