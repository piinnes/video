package com.mju.video.utils;


import com.mju.video.dto.Result;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;

public class Base64Util {

    public static Result saveBase64(String imagePath,String base64Str){
        StringBuffer fileName = new StringBuffer();
        SimpleDateFormat s = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        fileName.append(s.format(new Date()));
        if (StringUtils.isBlank(base64Str)) {
            return new Result.Builder<String>(-1,"file不可缺省").build();
        } else if (base64Str.indexOf("data:image/png;") != -1) {
            base64Str = base64Str.replace("data:image/png;base64,", "");
            fileName.append(".png");
        } else if (base64Str.indexOf("data:image/jpeg;") != -1) {
            base64Str = base64Str.replace("data:image/jpeg;base64,", "");
            fileName.append(".jpeg");
        } else {
            return new Result.Builder<String>(-1,"请选择.png.jpg格式的图片").build();
        }
        File file = new File(imagePath, fileName.toString());
        byte[] fileBytes = Base64.getDecoder().decode(base64Str);
        try {
            FileUtils.writeByteArrayToFile(file, fileBytes);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result.Builder<String>(-1,"保存失败").build();
        }
        return new Result.Builder<String>(0,"保存成功",imagePath + fileName.toString()).build();
    }

    public static String baseImagePath(){
        String osname = System.getProperty("os.name");
        String imagePath = "";
        if(osname.toLowerCase().startsWith("win")){
            imagePath = "D:/image/";
        }else {
            imagePath = "/Users/work/images/";
        }
        return imagePath;
    }
}

