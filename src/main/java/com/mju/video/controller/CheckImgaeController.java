package com.mju.video.controller;

import com.mju.video.domain.Image;
import com.mju.video.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CheckImgaeController {
    @Autowired
    private ImageService imageService;

    /**
     * 审核图片页面
     * @param collectId
     * @param model
     * @return
     */
    @RequestMapping("/checkImagePage")
    public String checkImagePage(@RequestParam("id") Integer collectId, Model model){
        List<Image> imageList = imageService.findImagesByCollectId(collectId);
        model.addAttribute("imageList",imageList);
        return "/checkImage";
    }

    /**
     * 删除图片
     * @param imgId
     * @return
     */
    @RequestMapping("/delImage")
    @ResponseBody
    public String delImage(@RequestParam(name = "imgId") Integer imgId){
        try {
            if (imgId!=null){
                imageService.delImageById(imgId);
                }
                return "删除成功";
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "删除失败";
    }

    @RequestMapping("/getImageInfo")
    @ResponseBody
    public Image getImageInfo(Integer imageId){
        Image image = imageService.findOne(imageId);
        return image;
    }
}
