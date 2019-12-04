package com.mju.video.service.impl;

import com.mju.video.domain.User;
import com.mju.video.mapper.UserMapper;
import com.mju.video.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User login(User user) {
        Example example = new Example(User.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("username", user.getUsername());
        criteria.andEqualTo("password", user.getPassword());
        List<User> users = userMapper.selectByExample(example);
        if (users.size()!=0){
           return users.get(0);
        }
        return null;
    }

    @Override
    @Transactional
    public boolean register(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        int effectNum = userMapper.insert(user);
        if (effectNum>0){
            return true;
        }
        return false;
    }
}
