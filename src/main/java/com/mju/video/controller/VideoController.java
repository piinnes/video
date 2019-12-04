package com.mju.video.controller;

import com.mju.video.domain.Rabbish;
import com.mju.video.service.RabbishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class VideoController {
    @Autowired
    private RabbishService rabbishService;
    /**
     * 拍摄图片页面的垃圾类别列表
     * @param model
     * @return
     */
    @RequestMapping("/")
    public String index(Model model){
        List<Rabbish> rabbishList = rabbishService.findAll();
        model.addAttribute("rabbishList",rabbishList);
        return "video";
    }
}
