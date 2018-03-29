package com.litsoft.evaluateserver.api;

import com.litsoft.evaluateserver.model.SysUser;
import com.litsoft.evaluateserver.service.CustomUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/sys")
public class SysUserController {

    @Autowired
    private CustomUserService customUserService;

    @RequestMapping("/")
    public ModelAndView index(Model model, ModelAndView mav) {

        mav.setViewName("/login");
        return mav;
    }

    @RequestMapping("/login")
    public ModelAndView login(String username, String password, ModelAndView mav) throws Exception {
        SysUser sysUser = customUserService.loadUserByUsername(username, password);
        mav.setViewName("/view/research");
        return mav;
    }

    @GetMapping("/register")
    public ModelAndView register(ModelAndView mav) {
        mav.setViewName("/register");
        return mav;
    }

    @GetMapping("/index")
    public ModelAndView index(ModelAndView mav) {
        mav.setViewName("/index");
        return mav;
    }

}
