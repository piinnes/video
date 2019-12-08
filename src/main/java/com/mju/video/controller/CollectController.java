package com.mju.video.controller;

import com.github.pagehelper.PageInfo;
import com.mju.video.domain.Collect;
import com.mju.video.domain.Rabbish;
import com.mju.video.service.CollectService;
import com.mju.video.service.ImageService;
import com.mju.video.service.RabbishService;
import com.mju.video.utils.Base64Util;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Controller
public class CollectController {
    @Autowired
    private CollectService collectService;
    @Autowired
    private RabbishService rabbishService;

    /**
     * 采集信息列表
     * @param model
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/collect")
    public String collect(Model model, @RequestParam(required = false,defaultValue="1",value="pageNum")Integer pageNum,
                        @RequestParam(defaultValue="5",value="pageSize")Integer pageSize){
        if(pageNum == null){
            pageNum = 1;   //设置默认当前页
        }
        if(pageNum <= 0){
            pageNum = 1;
        }
        if(pageSize == null){
            pageSize = 5;    //设置默认每页显示的数据数
        }
        PageInfo<Collect> pageInfo = collectService.findAll(pageNum,pageSize);
        model.addAttribute("pageInfo",pageInfo);
        return "collect";
    }
//    @RequestMapping("/approval")
//    public String approval(@RequestParam(name = "imgId") Integer[] imgId, RedirectAttributes redirect){
//        boolean success=false;
//        if (imgId.length>0){
//            success = imageService.updateState(imgId);
//        }
//        if (success){
//            return "redirect:/admin";
//        }
//        redirect.addFlashAttribute("err","操作失败");
//        return "redirect:/admin";
//    }

    /**
     * 添加采集信息
     * @param collect
     * @param redirectAttributes
     * @return
     */
    @RequestMapping("collect_add")
    public String collect_add(Collect collect,Integer pageSzie,Integer pageNum,RedirectAttributes redirectAttributes){
        boolean isSuccess = collectService.addOne(collect);
        if (isSuccess){
            return "redirect:/collect?pageNum="+(pageNum+1)+"&pageSize="+pageSzie;
        }
        redirectAttributes.addFlashAttribute("errMsg", "添加失败");
        return "redirect:/collect";
    }

    /**
     * 编辑采集信息页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/collect_editPage")
    public String collect_editPage(Integer id, Model model){
        Collect collect = collectService.findOne(id);
        model.addAttribute("collect",collect);
        return "/collect_edit";
    }

    /**
     * 编辑采集信息
     * @param collect
     * @return
     */
    @RequestMapping("/collect_edit")
    public String collect_edit(Collect collect){
        collectService.updateCollect(collect);
        return "redirect:/collect";
    }

    /**
     * 删除采集信息
     * @param id
     * @param redirectAttributes
     * @return
     */
    @RequestMapping("collect_del")
    public String collect_del(Integer id, RedirectAttributes redirectAttributes){
        boolean isSuccess = collectService.delCollect(id);
        if (isSuccess){
            return "redirect:/collect";
        }
        redirectAttributes.addFlashAttribute("delerrMsg", "删除失败");
        redirectAttributes.addFlashAttribute("id", id);
        return "redirect:/collect";
    }

    /**
     * 压缩采集信息文件夹
     * @throws Exception
     */
    @GetMapping("/zipFile")
    @ResponseBody
    public String zipFile(Integer collectId,Integer rabbishId) throws Exception {
        if (collectId!=null){
            Collect collect = collectService.findOne(collectId);
            //这个是文件夹的绝对路径，如果想要相对路径就自行了解写法
            String sourceDir = Base64Util.baseImagePath()+"collect/" + collect.getName();
            //这个是压缩之后的文件绝对路径
            try {
                FileOutputStream fos = new FileOutputStream(
                        Base64Util.baseImagePath()+"collect/"+collect.getName()+".zip");
                ZipOutputStream zipOut = new ZipOutputStream(fos);
                File fileToZip = new File(sourceDir);

                zipFile(fileToZip, fileToZip.getName(), zipOut);
                zipOut.close();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
                return "导出失败";
            }
            return "导出成功";
        }
        Rabbish rabbish = rabbishService.findOne(rabbishId);
        //这个是文件夹的绝对路径，如果想要相对路径就自行了解写法
        String sourceDir = Base64Util.baseImagePath()+"rabbish/" + rabbish.getName();
        //这个是压缩之后的文件绝对路径
        try {
            FileOutputStream fos = new FileOutputStream(
                    Base64Util.baseImagePath()+"rabbish/"+rabbish.getName()+".zip");
            ZipOutputStream zipOut = new ZipOutputStream(fos);
            File fileToZip = new File(sourceDir);

            zipFile(fileToZip, fileToZip.getName(), zipOut);
            zipOut.close();
            fos.close();
        } catch (Exception e) {
            e.printStackTrace();
            return "导出失败";
        }
        return "导出成功";
    }

    private void zipFile(File fileToZip, String fileName, ZipOutputStream zipOut) throws Exception {

            if (fileToZip.isHidden()) {
                return;
            }
            if (fileToZip.isDirectory()) {
                if (fileName.endsWith("/")) {
                    zipOut.putNextEntry(new ZipEntry(fileName));
                    zipOut.closeEntry();
                } else {
                    zipOut.putNextEntry(new ZipEntry(fileName + "/"));
                    zipOut.closeEntry();
                }
                File[] children = fileToZip.listFiles();
                for (File childFile : children) {
                    zipFile(childFile, fileName + "/" + childFile.getName(), zipOut);
                }
                return;
            }
            FileInputStream fis = new FileInputStream(fileToZip);
            ZipEntry zipEntry = new ZipEntry(fileName);
            zipOut.putNextEntry(zipEntry);
            byte[] bytes = new byte[1024];
            int length;
            while ((length = fis.read(bytes)) >= 0) {
                zipOut.write(bytes, 0, length);
            }
            fis.close();

    }
}
