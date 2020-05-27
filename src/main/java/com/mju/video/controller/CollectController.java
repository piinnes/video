package com.mju.video.controller;

import com.github.pagehelper.PageInfo;
import com.mju.video.domain.Collect;
import com.mju.video.domain.Rabbish;
import com.mju.video.service.CollectImageService;
import com.mju.video.service.CollectService;
import com.mju.video.service.RabbishImageService;
import com.mju.video.service.RabbishService;
import com.mju.video.utils.Base64Util;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

@Controller
public class CollectController {
    @Autowired
    private CollectService collectService;
    @Autowired
    private RabbishService rabbishService;
    @Autowired
    private CollectImageService collectImageService;
    @Autowired
    private RabbishImageService rabbishImageService;

    /**
     * 采集信息列表分页
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping("/collect")
    @ResponseBody
    public PageInfo<Collect> collect(
                          @RequestParam(required = false,defaultValue="1",value="pageNum")Integer pageNum,
                          @RequestParam(required = false,defaultValue="10",value="pageSize")Integer pageSize) throws ParseException {
        if(pageNum == null){
            pageNum = 1;   //设置默认当前页
        }
        if(pageNum <= 0){
            pageNum = 1;
        }
        if(pageSize == null){
            pageSize = 10;    //设置默认每页显示的数据数
        }
            PageInfo<Collect> pageInfo = collectService.findAll(pageNum,pageSize);
//            List<Collect> collectList = collectService.selectAll();
//        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//        for (Collect collect: collectList){
//            String format = simpleDateFormat.format(collect.getCreateTime());
//            Date parse = simpleDateFormat.parse(format);
//            collect.setCreateTime(parse);
//        }
            return pageInfo;
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
     * 采集信息列表
     * @return
     */
    @RequestMapping("/CollectList")
    @ResponseBody
    public List<Collect> getCollectList(){
            List<Collect> collectList = collectService.selectAll();
        return collectList;
    }

    @RequestMapping("/search")
    @ResponseBody
    public String search(Model model,
                          @RequestParam(required = false,defaultValue="1",value="pageNum")Integer pageNum,
                          @RequestParam(defaultValue="5",value="pageSize")Integer pageSize,
                          @RequestParam(value = "searchName") String searchName,
                          RedirectAttributes redirectAttributes){
        if(pageNum == null){
            pageNum = 1;   //设置默认当前页
        }
        if(pageNum <= 0){
            pageNum = 1;
        }
        if(pageSize == null){
            pageSize = 5;    //设置默认每页显示的数据数
        }
        PageInfo<Collect> pageInfo = collectService.findAllByLikeName(pageNum,pageSize,searchName);
        List<Collect> collectList = collectService.selectAll();
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("collectList",collectList);
        model.addAttribute("searchName",searchName);
        return "collect::search";
    }


    /**
     * 获取采集信息
     * @param collectId
     * @return
     */
    @RequestMapping("/getCollectInfo")
    @ResponseBody
    public Collect getCollectInfo(Integer collectId){
        Collect collect = collectService.findOne(collectId);
        return collect;
    }

    /**
     * 添加采集信息
     * @param collect
     * @param redirectAttributes
     * @return
     */
    @RequestMapping("collect_add")
    @ResponseBody
    public Map<String,Object> collect_add(@RequestBody Collect collect,Integer pageSzie,Integer pageNum,RedirectAttributes redirectAttributes){
        Map<String,Object> map = new HashMap<>();
        Collect one = collectService.findOneByName(collect.getName());
        if (one==null){
            boolean isSuccess = collectService.addOne(collect);
            map.put("success", isSuccess);
            return map;
        }
        map.put("success", false);
        map.put("errMsg", "采集名称已存在！");
        return map;
    }

    /**
     * 编辑采集信息页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/collect_getInfo")
    @ResponseBody
    public Collect collect_editPage(Integer id, Model model){
        Collect collect = collectService.findOne(id);
        return collect;
    }

    /**
     * 编辑采集信息
     * @param collect
     * @return
     */
    @RequestMapping("/collect_edit")
    @ResponseBody
    public Map<String,Object> collect_edit(@RequestBody Collect collect,RedirectAttributes attributes){
        Map<String,Object> map = new HashMap<>();
        Collect collect1 = collectService.findOne(collect.getId());
        if (collect1.getName().equals(collect.getName())){
            collectService.updateCollect(collect);
            map.put("success", true);
            return map;
        }
        Collect one = collectService.findOneByName(collect.getName());
        if (one !=null) {
            map.put("success", false);
            map.put("errMsg", "采集名称已存在！");
            return map;
        }
        collectService.updateCollect(collect);
        map.put("success", true);
        return map;
    }

    /**
     * 删除采集信息
     * @param id
     * @param redirectAttributes
     * @return
     */
    @RequestMapping("collect_del")
    @ResponseBody
    public Map<String,Object> collect_del(Integer id, RedirectAttributes redirectAttributes){
        Map<String,Object> map = new HashMap<>();
        boolean isSuccess = collectService.delCollect(id);
        map.put("success",isSuccess);
        return map;
    }


    /**
     * 转入采集信息
     * @param srcCollectId
     * @param destCollectId
     * @return
     */
    @RequestMapping("/changTo")
    @ResponseBody
    public Map<String,Object> changTo(Integer srcCollectId,Integer destCollectId) {
        Map<String,Object> map = new HashMap<>();
        try {
            Collect srcCollect = collectService.findOne(srcCollectId);
            Collect destCollect = collectService.findOne(destCollectId);
            //指定源数据
            File srcFile = new File(Base64Util.baseImagePath()+"collect/"+srcCollect.getId());
            //指定目的地
            File destFile = new File(Base64Util.baseImagePath()+"collect/"+destCollect.getId());
            //调用方法
            copyFolder(srcFile, destFile);
            //删除源数据
            File[] files = srcFile.listFiles();
            for (File file : files) {
                FileUtils.forceDelete(file);
                //更新数据库字段
                if (Base64Util.isWin()){
                    collectImageService.update("/image/collect/"+srcCollect.getId()+"/"+file.getName(),"/image/collect/"+destCollect.getId()+"/"+file.getName(),destCollectId);
                    rabbishImageService.update(srcCollectId,destCollectId);
                }else {
                    collectImageService.update(Base64Util.baseImagePath()+"collect/"+srcCollect.getId()+"/"+file.getName(),Base64Util.baseImagePath()+"collect/"+destCollect.getId()+"/"+file.getName(),destCollectId);
                    rabbishImageService.update(srcCollectId,destCollectId);
                }

            }
            map.put("success",true);
            map.put("msg","转入成功");
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success",false);
            map.put("msg","转入失败");
            return map;
        }
    }

    /**
     * 模糊搜索
     * @return
     */
    @RequestMapping("/collectByCondition")
    @ResponseBody
    public PageInfo<Collect> getCollectByCondition(@RequestParam(required = false,defaultValue="1",value="pageNum")Integer pageNum,
                                               @RequestParam(required = false,defaultValue="10",value="pageSize")Integer pageSize,String name,long[] timestamp,Integer operate,Integer total){
        PageInfo<Collect> pageInfo = collectService.getCollectByCondition(pageNum, pageSize, name, timestamp, operate, total);
        return pageInfo;
    }

    public static void copyFolder(File srcFile,File destFile ) throws Exception {
        //列出全部文件
              File[] list = srcFile.listFiles();
        if (list==null){
            throw new Exception("当前文件夹为空");
        }
        for(File files:list){
            //获取文件名字
            String str = files.getName();
            //判断是目录还是文件
            if(files.isDirectory()){
                //指定创建路径
                File f = new File(destFile+str);
                //生成文件夹
                f.mkdir();
            }else{
				 /*
				  * 读写文件
				  */
                FileInputStream is = new FileInputStream(files);
                if (!destFile.exists()){
                    destFile.mkdir();
                }
                File file = new File(destFile+"\\"+str);
                FileOutputStream os = new FileOutputStream(file);
                //高效流
                BufferedInputStream bis = new BufferedInputStream(is);
                BufferedOutputStream bos = new BufferedOutputStream(os);
                int len;
                byte[] bytes = new byte[1024];
                while((len=bis.read(bytes)) != -1){
                    bos.write(bytes);
                }
                //释放资源
                bis.close();
                bos.close();
            }
        }
    }

    /**
     * 压缩采集信息文件夹
     * @throws Exception
     */
    @RequestMapping(value = "/zipFile")
    @ResponseBody
    public void zipFile(HttpServletResponse response,Integer collectId,Integer rabbishId) throws Exception {
        Map<String,Object> map = new HashMap<>();
        if (collectId!=null){
            Collect collect = collectService.findOne(collectId);
            //这个是文件夹的绝对路径，如果想要相对路径就自行了解写法
            String sourceDir = Base64Util.baseImagePath()+"collect/" + collect.getId();
            //这个是压缩之后的文件绝对路径
            try {
                FileOutputStream fos = new FileOutputStream(
                        Base64Util.baseImagePath()+"collect/"+collect.getId()+".zip");
                ZipOutputStream zipOut = new ZipOutputStream(fos);
                File fileToZip = new File(sourceDir);
                zipFile(fileToZip, collect.getName(), zipOut);
                zipOut.close();
                fos.close();
                // 把压缩文件流返回到前端
                InputStream inputData = new FileInputStream(Base64Util.baseImagePath()+"collect/"+collect.getId()+".zip");
                com.mju.video.utils.FileUtils.downloadFile(response,collect.getName(),inputData);
                File file = new File(Base64Util.baseImagePath()+"collect/"+collect.getId()+".zip");
                FileUtils.forceDelete(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            Rabbish rabbish = rabbishService.findOne(rabbishId);
            //这个是文件夹的绝对路径，如果想要相对路径就自行了解写法
            String sourceDir = Base64Util.baseImagePath()+"rubbish/" + rabbish.getId();
            //这个是压缩之后的文件绝对路径
            try {
                FileOutputStream fos = new FileOutputStream(
                        Base64Util.baseImagePath()+"rubbish/"+rabbish.getId()+".zip");
                ZipOutputStream zipOut = new ZipOutputStream(fos);
                File fileToZip = new File(sourceDir);
                zipFile(fileToZip, rabbish.getName(), zipOut);
                zipOut.close();
                fos.close();
                // 把压缩文件流返回到前端
                InputStream inputData = new FileInputStream(Base64Util.baseImagePath()+"rubbish/"+rabbish.getId()+".zip");
                com.mju.video.utils.FileUtils.downloadFile(response,rabbish.getName(),inputData);
                File file = new File(Base64Util.baseImagePath()+"rubbish/"+rabbish.getId()+".zip");
                FileUtils.forceDelete(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
