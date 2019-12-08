package com.mju.video.controller;

import com.mju.video.domain.Rabbish;
import com.mju.video.service.RabbishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

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
    public String rabbish_category_list(Model model){
        List<Rabbish> rabbishList = rabbishService.findAll();
        model.addAttribute("rabbishList",rabbishList);
        return "rabbish_category_list";
    }

    /**
     * 类别管理页面编辑页面
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/rabbish_category_editPage")
    public String rabbish_category_editPage(Integer id,Model model){
        Rabbish rabbish = rabbishService.findOne(id);
        model.addAttribute("rabbish", rabbish);
        return "rabbish_category_edit";
    }

    /**
     * 类别管理页面编辑类别
     * @param rabbish
     * @param redirectAttributes
     * @return
     */
    @RequestMapping("/rabbish_category_edit")
    public String rabbish_category_edit(Rabbish rabbish, RedirectAttributes redirectAttributes){
        boolean isSuccess = rabbishService.updateRabbish(rabbish);
        if (isSuccess){
            return "redirect:/rabbish_category_list";
        }
        redirectAttributes.addFlashAttribute("errMsg", "更新失败");
        return "redirect:/rabbish_category_editPage";
    }

    /**
     * 类别管理页面添加类别
     * @param rabbish
     * @param redirectAttributes
     * @return
     */
    @RequestMapping("/rabbish_category_add")
    public String rabbish_category_add(Rabbish rabbish, RedirectAttributes redirectAttributes){
        boolean isSuccess = rabbishService.add(rabbish);
        if (isSuccess){
            return "redirect:/rabbish_category_list";
        }
        redirectAttributes.addFlashAttribute("errMsg", "添加失败");
        return "redirect:/rabbish_category_list";
    }

    /**
     * 类别管理页面删除类别
     * @param rabbish
     * @param redirectAttributes
     * @return
     */
    @RequestMapping("/rabbish_category_del")
    public String rabbish_category_del(Rabbish rabbish, RedirectAttributes redirectAttributes){
        boolean isSuccess = rabbishService.del(rabbish);
        if (isSuccess){
            return "redirect:/rabbish_category_list";
        }
        redirectAttributes.addFlashAttribute("delerrMsg", "删除失败");
        return "redirect:/rabbish_category_list";
    }

}
