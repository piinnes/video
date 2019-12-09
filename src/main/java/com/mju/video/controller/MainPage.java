package com.mju.video.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class MainPage {
    /**
     * 登录页面跳转
     * @return
     */
    @RequestMapping("/loginpage")
    public String login(){
        return "loginpage";
    }
}
