package com.mju.video.controller;

import com.mju.video.domain.Rabbish;
import com.mju.video.service.RabbishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
     * @param model
     * @return
     */
    @RequestMapping("/rabbish_category_list")
    @ResponseBody
    public List<Rabbish> rabbish_category_list(Model model){
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
        boolean isSuccess = rabbishService.updateRabbish(rabbish);
        map.put("success",isSuccess);
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
        boolean isSuccess = rabbishService.add(rabbish);
            map.put("success",isSuccess);
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
        boolean isSuccess = rabbishService.del(rabbish);
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
    public List<Rabbish> rabbish_category_like( String likeName){
        List<Rabbish> rabbishList = rabbishService.findByLikeName(likeName);
        return rabbishList;
    }

}
