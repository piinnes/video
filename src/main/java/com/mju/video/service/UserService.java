package com.mju.video.service;

import com.mju.video.domain.User;

public interface UserService {
    public User login(User user);

    boolean register(String username, String password);
}
