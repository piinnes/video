package com.mju.video.controller;

import com.mju.video.domain.User;
import com.mju.video.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
//@RequestMapping("/user")
public class UserController {
    /**
     * 登录
     */
    @Autowired
    private UserService userService;

    @RequestMapping("/login")
    public Map<String, Object> login(@RequestBody User user, HttpSession session, Model model, RedirectAttributes redirect) {
        Map<String, Object> map = new HashMap<>();
        User loginUser = userService.login(user);
        if (loginUser != null) {
            if (user.getUsername().equals("admin")){
                map.put("data", new HashMap<String, Object>() {
                    {
                        put("token", "admin-token");
                    }
                });
            }else{
                map.put("data", new HashMap<String, Object>() {
                    {
                        put("token", "editor-token");
                    }
                });
            }
            map.put("success", true);
            map.put("code", 20000);
            return map;
        }
        map.put("success", false);
        map.put("error", "用户名或密码错误");
        return map;
    }

    /**
     * 注册
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/register")
    @ResponseBody
    public Map<String, Object> register(String username, String password) {
        Map<String, Object> map = new HashMap<>();
        Integer findNum = 0;
        if (!StringUtils.isBlank(username) && !StringUtils.isBlank(password)) {
            List<User> userList = userService.findUserByUserName(username);
            if (userList.size() == 1) {
                map.put("success", false);
                map.put("msg", "用户名已存在");
                return map;
            } else {
                findNum = userService.register(username, password);
            }
        }
        if (findNum == 1) {
            map.put("success", true);
            map.put("msg", "注册成功");
            return map;
        } else {
            map.put("success", false);
            map.put("msg", "注册失败");
            return map;
        }
    }

    /**
     * 退出
     *
     * @return
     */
    @RequestMapping("/logout")
    @ResponseBody
    public Map<String,Object> logout() {
        Map<String,Object> result = new HashMap<>();
        result.put("code", 20000);
        result.put("success", true);
        return result;
    }

    @RequestMapping("/getInfo")
    @ResponseBody
    public Map<String,Object> getInfo(String token) {
        Map<String,Object> result = new HashMap<>();
        Map<String,Object> map = new HashMap<>();
        if ("admin-token".equals(token)){
            result.put("code", 20000);
            map.put("roles", new ArrayList<String>(){{add("admin");}});
            map.put("introduction", "I am a super administrator");
            map.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
            map.put("name", "Super Admin");

            result.put("data",map);
        }else if ("editor-token".equals(token)){
            result.put("code", 20000);

            map.put("roles", new ArrayList<String>(){{add("editor");}});
            map.put("introduction", "I am a super administrator");
            map.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
            map.put("name", "Normal Editor");

            result.put("data",map);
        }else {
            result.put("code", -1);
            result.put("msg", "系统错误！");
        }
        return result;
    }

    @RequestMapping("/forget")
    @ResponseBody
    public Map<String, Object> forget(String username, String password) {
        Map<String, Object> map = new HashMap<>();
        List<User> userList = userService.findUserByUserName(username);
        if (userList.size() > 0) {
            userService.resetPassword(userList.get(0), password);
            map.put("success", true);
            map.put("msg", "修改成功");
            return map;
        }
        map.put("success", false);
        map.put("msg", "用户不存在");
        return map;
    }

}
