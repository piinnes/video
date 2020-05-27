package com.mju.video.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

public class FileUtils {
    /**
     * 下载文件流
     * @param response
     * @param fileName
     * @param inputData
     * @throws IOException
     */
    public static void downloadFile(HttpServletResponse response, String fileName, InputStream inputData) throws IOException {
        OutputStream outs=response.getOutputStream();//获取文件输出IO流
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        response.setHeader("Content-disposition","attachment;filename="+ URLEncoder.encode(fileName+".zip","utf-8"));
        int bytesRead = 0;
        byte[] buffer = new byte[1024];
        //开始向网络传输文件流
        while ((bytesRead = inputData.read(buffer)) != -1) {
            outs.write(buffer, 0, bytesRead);
        }
        outs.flush();//这里一定要调用flush()方法
        inputData.close();
        outs.close();
    }
}
