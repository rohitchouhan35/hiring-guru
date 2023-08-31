package com.rohitchouhan35.hiringmadeeasy.service;

import com.rohitchouhan35.hiringmadeeasy.model.User;

import java.util.List;


public interface UserService {

    List<User> getUsers();
    User saveUser(User user);

}
