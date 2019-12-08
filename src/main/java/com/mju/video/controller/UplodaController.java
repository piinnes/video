package com.mju.video.controller;

import com.mju.video.domain.*;
import com.mju.video.dto.Result;
import com.mju.video.service.*;
import com.mju.video.utils.Base64Util;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;


@Controller
public class UplodaController {
    @Autowired
    private CollectImageService collectImageService;
    @Autowired
    private RabbishImageService rabbishImageService;
    @Autowired
    private RabbishService rabbishService;
    @Autowired
    private CollectService collectService;

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
        Rabbish rabbish = rabbishService.findOne(rab_id);
        Collect collect = collectService.findOne(collectId);
        String imagePath = Base64Util.baseImagePath();
        String collectImagePath = imagePath +"collect/"+collect.getName() +"/";
        String rabbishImagePath = imagePath+"rabbish/" + rabbish.getName() + "/";
        Result collectResult = Base64Util.saveBase64(collectImagePath, base64Str);
        Result rabbishResult = Base64Util.saveBase64(rabbishImagePath, base64Str);
        String collectImageUrl = (String) collectResult.getData();
        String rabbishImageUrl = (String) rabbishResult.getData();
        CollectImage collectImage = new CollectImage();
        collectImage.setUrl(collectImageUrl.substring(2));
        collectImage.setCreateTime(new Date());
        collectImage.setState(0);
        collectImage.setCollectId(collectId);
        boolean isSuccess = collectImageService.save(collectImage);
        RabbishImage rabbishImage = new RabbishImage();
        rabbishImage.setUrl(rabbishImageUrl.substring(2));
        rabbishImage.setCreateTime(new Date());
        rabbishImage.setRabbishId(rab_id);
        rabbishImage.setState(0);
        boolean isSuccess2 = rabbishImageService.save(rabbishImage);
        if (isSuccess&&isSuccess2){
            return "保存成功";
        }
        return "保存失败";
    }


}
