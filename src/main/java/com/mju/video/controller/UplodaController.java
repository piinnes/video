package com.mju.video.controller;

import com.mju.video.domain.Image;
import com.mju.video.dto.Result;
import com.mju.video.service.ImageService;
import com.mju.video.utils.Base64Util;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
public class UplodaController {
    @Autowired
    private ImageService imageService;

    /**
     * 拍摄图片页面上传图片
     * @param base64Str
     * @param rab_id
     * @return
     */
    @RequestMapping("/upload")
    @ResponseBody
    public String saveBase64(@RequestParam(value = "canvas") String base64Str,
                             @RequestParam(value = "rab_id") Integer rab_id,
                             @RequestParam(value = "collectId")Integer collectId){
        String imagePath = Base64Util.baseImagePath();
        imagePath = imagePath + collectId +"/";
        Result result = Base64Util.saveBase64(imagePath, base64Str);
        String imageUrl = (String) result.getData();
        Image image = new Image();
        image.setUrl(imageUrl.substring(2));
        image.setRab_id(rab_id);
        image.setState(0);
        image.setCollectId(collectId);
        boolean success = imageService.save(image);
        if (success&&result.getCode()==0){
            return result.getMsg();
        }
        return "保存失败";
    }


}
