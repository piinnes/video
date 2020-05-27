package com.mju.video.controller;

import cn.hutool.core.codec.Base64;
import com.mju.video.domain.CollectImage;
import com.mju.video.service.CollectImageService;
import com.mju.video.utils.Base64Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class CollectImgaeController {
    @Autowired
    private CollectImageService collectImageService;

    /**
     * 审核图片页面
     * @param collectId
     * @return
     */
    @RequestMapping("/getCollectImageList")
    @ResponseBody
    public Map<String,Object> getCollectImageList(Integer collectId) throws Exception{
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> temp = null;
        List<CollectImage> collectImageList = collectImageService.findImagesByCollectId(collectId);
        if (collectImageList!=null&&collectImageList.size()>0){
            List<Map<String,Object>> encodeList = new ArrayList<>();
            File file = null;
            FileInputStream fis = null;
            byte[] date = null;
            for (CollectImage item : collectImageList){
                temp = new HashMap<>();
                if (Base64Util.isWin()){
                    file = new File("D:"+item.getUrl());
                }else {
                    file = new File(item.getUrl());
                }
                fis = new FileInputStream(file);
                date = new byte[fis.available()];
                fis.read(date);
                fis.close();
                String encode = Base64.encode(date);
                temp.put("id",item.getId());
                temp.put("encode",encode);
                encodeList.add(temp);
            }
            map.put("encodeList",encodeList);
            map.put("success",true);
            return map;
        }
        map.put("success",false);
        return map;
    }

    /**
     * 删除图片
     * @param imgId
     * @return
     */
    @RequestMapping("/delCollectImage")
    @ResponseBody
    public Map<String,Object> delImage(Integer imgId){
        Map<String,Object> map = new HashMap<>();
        try {
            if (imgId!=null){
                collectImageService.delImageById(imgId);
                }
            map.put("success", true);
            map.put("msg","删除成功");
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("success", false);
        map.put("msg","删除失败");
        return map;
    }

    @RequestMapping("/getImageInfo")
    @ResponseBody
    public CollectImage getImageInfo(Integer imageId){
        CollectImage collectImage = collectImageService.findOne(imageId);
        return collectImage;
    }
}
