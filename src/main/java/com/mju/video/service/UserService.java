package com.mju.video.service;

import com.mju.video.domain.User;

import java.util.List;

public interface UserService {
    public User login(User user);

    Integer register(String username, String password);

    List<User> findUserByUserName(String username);
}
