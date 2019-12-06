package com.mju.video.controller;

import com.mju.video.domain.User;
import com.mju.video.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("/login")
    public String login(User user, HttpSession session, Model model, RedirectAttributes redirect){
        User loginUser = userService.login(user);
        if (loginUser!=null){
            session.setAttribute("user", loginUser);
            if (loginUser.getUsername().equals("admin")){
                return "redirect:/collect";
            }
            return "redirect:/";
        }
        redirect.addFlashAttribute("err", "用户名或密码错误");
        return "redirect:/loginpage";
    }

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

    @RequestMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "/loginpage";
    }
}
