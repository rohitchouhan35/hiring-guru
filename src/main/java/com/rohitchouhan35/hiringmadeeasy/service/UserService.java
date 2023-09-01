package com.rohitchouhan35.hiringmadeeasy.service;

import com.rohitchouhan35.hiringmadeeasy.model.User;

import java.util.List;
import java.util.Optional;


public interface UserService {

    List<User> getUsers();
    User saveUser(User user);
    Optional<User> getUserByUsername(String username);
    boolean hasUserWithUsername(String username);
    boolean hasUserWithEmail(String email);
    public void changePassword(String username, String currentPassword, String newPassword);

}
