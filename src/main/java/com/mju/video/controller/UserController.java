package com.mju.video.controller;

import com.mju.video.domain.User;
import com.mju.video.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class UserController {
    /**
     * 登录
     */
    @Autowired
    private UserService userService;
    @RequestMapping("/login")
    @ResponseBody
    public Map<String,Object> login(@RequestBody User user, HttpSession session, Model model, RedirectAttributes redirect){
        Map<String,Object> map = new HashMap<>();
        User loginUser = userService.login(user);
        if (loginUser!=null){
            session.setAttribute("user", loginUser);
            map.put("success", true);
            return map;
        }
        redirect.addFlashAttribute("err", "用户名或密码错误");
        map.put("success", false);
        return map;
    }

    /**
     * 注册
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/register")
    @ResponseBody
    public String register(String username,String password){
        Integer findNum=0;
        if (!StringUtils.isBlank(username)&&!StringUtils.isBlank(password)){
            List<User> userList = userService.findUserByUserName(username);
            if (userList.size()==1){
                return "用户名已存在";
            }else {
                findNum = userService.register(username, password);
            }
        }
        if (findNum==1){
            return "注册成功";
        }else{
            return "注册失败";
        }
    }

    /**
     * 退出
     * @param session
     * @return
     */
    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "/loginpage";
    }

    @RequestMapping("/forget")
    @ResponseBody
    public String forget(String username,String password){
        List<User> userList = userService.findUserByUserName(username);
        if (userList.size() > 0) {
            userService.resetPassword(userList.get(0),password);
            return "重置成功";
        }
        return "用户不存在";
    }
}
