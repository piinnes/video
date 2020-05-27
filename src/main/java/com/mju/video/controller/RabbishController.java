package com.mju.video.controller;

import com.github.pagehelper.PageInfo;
import com.mju.video.domain.Collect;
import com.mju.video.domain.Rabbish;
import com.mju.video.service.RabbishService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class RabbishController {
    @Autowired
    private RabbishService rabbishService;
    /**
     * 类别管理页面垃圾列表
     * @return
     */
    @RequestMapping("/rabbish_category_list")
    @ResponseBody
    public PageInfo<Rabbish> rabbish_category_list(@RequestParam(required = false,defaultValue="1",value="pageNum")Integer pageNum,
                                               @RequestParam(required = false,defaultValue="10",value="pageSize")Integer pageSize){
        if(pageNum == null){
            pageNum = 1;   //设置默认当前页
        }
        if(pageNum <= 0){
            pageNum = 1;
        }
        if(pageSize == null){
            pageSize = 10;    //设置默认每页显示的数据数
        }
        PageInfo<Rabbish> pageInfo = rabbishService.getList(pageNum,pageSize);
//        List<Rabbish> rabbishList = rabbishService.findAll();
        return pageInfo;
    }

    @RequestMapping("/rabbishList")
    @ResponseBody
    public List<Rabbish> rabbishList(){
        List<Rabbish> rabbishList = rabbishService.findAll();
        return rabbishList;
    }

    /**
     * 类别管理页面编辑页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/rabbish_category_getInfo")
    @ResponseBody
    public Rabbish rabbish_category_editPage(Integer id,Model model){
        Rabbish rabbish = rabbishService.findOne(id);
        return rabbish;
    }

    /**
     * 类别管理页面编辑类别
     * @param rabbish
     * @param redirectAttributes
     * @return
     */
    @RequestMapping("/rabbish_category_edit")
    @ResponseBody
    public Map<String,Object> rabbish_category_edit(@RequestBody Rabbish rabbish, RedirectAttributes redirectAttributes){
        Map<String,Object> map = new HashMap<>();
        Rabbish rabbish1 = rabbishService.findOne(rabbish.getId());
        if (rabbish1.getName().equals(rabbish.getName())){
            rabbishService.updateRabbish(rabbish);
            map.put("success", true);
            return map;
        }
        Rabbish one = rabbishService.findOneByName(rabbish.getName());
        if (one !=null) {
            map.put("success", false);
            map.put("errMsg", "类别已存在！");
            return map;
        }
        rabbishService.updateRabbish(rabbish);
        map.put("success", true);
        return map;
    }

    /**
     * 类别管理页面添加类别
     * @param rabbish
     * @param redirectAttributes
     * @return
     */
    @RequestMapping("/rabbish_category_add")
    @ResponseBody
    public Map<String,Object> rabbish_category_add(@RequestBody Rabbish rabbish, RedirectAttributes redirectAttributes){
        Map<String,Object> map = new HashMap<>();
        Rabbish one = rabbishService.findOneByName(rabbish.getName());
        if (one==null){
            boolean isSuccess = rabbishService.addRabbish(rabbish);
            map.put("success",isSuccess);
            return map;
        }
        map.put("success", false);
        map.put("errMsg", "类别已存在！");
        return map;
        }


    /**
     * 类别管理页面删除类别
     * @param rabbish
     * @param redirectAttributes
     * @return
     */
    @RequestMapping("/rabbish_category_del")
    @ResponseBody
    public Map<String,Object> rabbish_category_del(@RequestBody Rabbish rabbish, RedirectAttributes redirectAttributes){
        Map<String,Object> map = new HashMap<>();
        boolean isSuccess = rabbishService.delRabbish(rabbish);
        map.put("success",isSuccess);
        return map;
    }

    /**
     * 模糊搜索
     * @param likeName
     * @return
     */
    @RequestMapping("/rabbish_category_like_name")
    @ResponseBody
    public List<Rabbish> rabbish_category_like(String likeName){
        if (StringUtils.isBlank(likeName)){
            List<Rabbish> all = rabbishService.findAll();
            return all;
        }
        List<Rabbish> rabbishList = rabbishService.findByLikeName(likeName);
        return rabbishList;
    }

    /**
     * 模糊搜索
     * @return
     */
    @RequestMapping("/rubbishByCondition")
    @ResponseBody
    public PageInfo<Rabbish> getRubbishByCondition(@RequestParam(required = false,defaultValue="1",value="pageNum")Integer pageNum,
                                                   @RequestParam(required = false,defaultValue="10",value="pageSize")Integer pageSize,String name,Integer operate,Integer total){
        PageInfo<Rabbish> pageInfo = rabbishService.getRubbishByCondition(pageNum, pageSize, name, operate, total);
        return pageInfo;
    }

}
