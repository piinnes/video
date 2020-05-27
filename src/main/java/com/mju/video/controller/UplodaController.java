package com.mju.video.controller;

import com.mju.video.domain.*;
import com.mju.video.dto.Result;
import com.mju.video.service.*;
import com.mju.video.utils.Base64Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;


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
     * @param upload
     * @return
     */
    @RequestMapping("/upload")
    @ResponseBody
    public Map<String,Object> saveBase64(@RequestBody Upload upload){

        Map<String,Object> map = new HashMap<>();
        Rabbish rabbish = rabbishService.findOne(upload.getRab_id());
        Collect collect = collectService.findOne(upload.getCollectId());
        String imagePath = Base64Util.baseImagePath();
        boolean isSuccess = true;
        if(collect!=null){
            String collectImagePath = imagePath +"collect/"+collect.getId() +"/";
            Result collectResult = Base64Util.saveBase64(collectImagePath, upload.getBase64Str());
            String collectImageUrl = (String) collectResult.getData();

            CollectImage collectImage = new CollectImage();
            if (Base64Util.isWin()){
                collectImage.setUrl(collectImageUrl.substring(2));
            }else {
                collectImage.setUrl(collectImageUrl);
            }
            collectImage.setCreateTime(new Date());
            collectImage.setState(0);
            collectImage.setCollectId(upload.getCollectId());
            collectImage.setRabbishId(upload.getRab_id());
            isSuccess = collectImageService.save(collectImage);
        }
        String rabbishImagePath = imagePath+"rubbish/" + rabbish.getId() + "/";
        Result rabbishResult = Base64Util.saveBase64(rabbishImagePath, upload.getBase64Str());
        String rabbishImageUrl = (String) rabbishResult.getData();

        RabbishImage rabbishImage = new RabbishImage();
        if (Base64Util.isWin()){
            rabbishImage.setUrl(rabbishImageUrl.substring(2));
        }else {
            rabbishImage.setUrl(rabbishImageUrl);
        }
        rabbishImage.setCreateTime(new Date());
        rabbishImage.setRabbishId(upload.getRab_id());
        rabbishImage.setState(0);
        rabbishImage.setCollectId(upload.getCollectId());
        boolean isSuccess2 = rabbishImageService.save(rabbishImage);

        if (isSuccess&&isSuccess2){
            map.put("success", true);
            return map;
        }
        map.put("success", false);
        return map;
    }


}
