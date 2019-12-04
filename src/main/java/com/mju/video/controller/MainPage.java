package com.mju.video.controller;

import com.github.pagehelper.PageInfo;
import com.mju.video.domain.Image;
import com.mju.video.domain.Rabbish;
import com.mju.video.service.ImageService;
import com.mju.video.service.RabbishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

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
