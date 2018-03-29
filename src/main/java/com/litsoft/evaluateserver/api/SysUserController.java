package com.litsoft.evaluateserver.api;

import com.litsoft.evaluateserver.model.SysUser;
import com.litsoft.evaluateserver.service.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/sys")
public class SysUserController {

    @RequestMapping("/desktop")
    public String desktop() {
        return "/view/front/desktop";
    }

    @RequestMapping("/adminLilst")
    public String adminList() {
        return "/view/front/admin-list";
    }

    @RequestMapping("/adminRole")
    public String adminRole() {
        return "/view/front/admin-role";
    }

    @RequestMapping("/adminCate")
    public String adminCate() {
        return "/view/front/admin-cate";
    }

    @RequestMapping("/adminRule")
    public String adminRule() {
        return "/view/front/admin-rule";
    }

    @RequestMapping("/register")
    public String register() {
        return "/register";
    }

}
