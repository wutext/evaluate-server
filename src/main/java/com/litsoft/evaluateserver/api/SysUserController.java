package com.litsoft.evaluateserver.api;

import com.litsoft.evaluateserver.entity.User;
import com.litsoft.evaluateserver.model.SysUser;
import com.litsoft.evaluateserver.service.CustomUserService;
import com.litsoft.evaluateserver.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/sys")
public class SysUserController {

    @Autowired
    private UserService userService;

    @RequestMapping("/desktop")
    public String desktop() {
        return "/view/front/desktop";
    }

    @RequestMapping("/adminList")
   /* @RequiresPermissions("userInfo:view")//权限管理;*/
    public String adminList(Model model){
        List<User> users = userService.findAll();
        model.addAttribute("users", users);
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
