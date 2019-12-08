package com.mju.video.controller;

import com.mju.video.domain.RabbishImage;
import com.mju.video.service.RabbishImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class RabbishImageController {
    @Autowired
    private RabbishImageService rabbishImageService;
    /**
     * 获取该垃圾类别图片
     */
    @RequestMapping("/rabbish_imagePage")
    public String getRabbishImage(Integer id, Model model){
        List<RabbishImage> rabbishImageList = rabbishImageService.findAllByRabbishId(id);
        model.addAttribute("imageList",rabbishImageList);
        return "rabbish_Image";
    }

    /**
     * 获取该垃圾图片的信息
     * @param imageId
     * @return
     */
    @RequestMapping("/getRabbishImageInfo")
    @ResponseBody
    public RabbishImage getRabbishImageInfo(Integer imageId){
        RabbishImage rabbishImage = rabbishImageService.findOne(imageId);
        return rabbishImage;
    }

    @RequestMapping("/delRabbishImage")
    @ResponseBody
    public String delRabbishImage(Integer imgId){
        try {
            if (imgId!=null){
                rabbishImageService.delectRabbishImageById(imgId);
            }
            return "删除成功";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "删除失败";
    }
}
