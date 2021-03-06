package com.mju.video.controller;

import cn.hutool.core.codec.Base64;
import com.mju.video.domain.RabbishImage;
import com.mju.video.service.RabbishImageService;
import com.mju.video.utils.Base64Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class RabbishImageController {
    @Autowired
    private RabbishImageService rabbishImageService;
    /**
     * 获取该垃圾类别图片
     * @param id
     * @return
     * @throws Exception
     */
    @RequestMapping("/getRabbishImageList")
    @ResponseBody
    public Map<String,Object> getRabbishImage(Integer id) throws Exception {
        Map<String,Object> map = new HashMap<>();
        Map<String,Object> temp = null;
        List<RabbishImage> rabbishImageList = rabbishImageService.findAllByRabbishId(id);
        if (rabbishImageList!=null&&rabbishImageList.size()>0){
            List<Map<String,Object>> encodeList = new ArrayList<>();
//        FileInputStream fis = null;
//        for (RabbishImage item : rabbishImageList){
//            fis = new FileInputStream("D:"+item.getUrl());
//            System.out.println(fis);
//        }
            File file = null;
            FileInputStream fis = null;
            byte[] date = null;
            for (RabbishImage item : rabbishImageList){
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
    public Map<String,Object> delRabbishImage(Integer imgId){
        Map<String,Object> map = new HashMap<>();
        try {
            if (imgId!=null){
                rabbishImageService.delectRabbishImageById(imgId);
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
}
