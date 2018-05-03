package com.litsoft.evaluateserver.api;

import com.litsoft.evaluateserver.entity.Permission;
import com.litsoft.evaluateserver.entity.User;
import com.litsoft.evaluateserver.service.PermissionService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    @Autowired
    private PermissionService permissionService;

    @RequestMapping({"/", "/index"})
    public String index(Model model) {
        User user = permissionService.getShiroUser();
        List<Permission> permissionList = permissionService.findMenuByUserId();
        List<Integer> ids = permissionList.stream().
            filter(s -> s.getId() != 1 && s.getPar().getId() != 1).
            map(s -> s.getId()).
            collect(Collectors.toList());
        permissionList.stream().forEach(s -> {
            List<Permission> permissionListChild = s.getPermissionListChild();
            Iterator<Permission> iterator = permissionListChild.iterator();
            while (iterator.hasNext()) {
                if (!ids.contains(iterator.next().getId())) {
                    iterator.remove();
                }
            }
        });
        model.addAttribute("permissionList", permissionList);
        model.addAttribute("user", user);
        return "/view/front/index";
    }

   /* @RequestMapping("/urlChild")
    public String findUrlChild(Model model) {
        List<Permission> permissionList = permissionService.findMenuByUserId();
        return model;
    }*/

    @RequestMapping("/login")
    public String login(HttpServletRequest request, Map<String, Object> map) throws Exception {
        System.out.println("HomeController.login()");
        // 登录失败从request中获取shiro处理的异常信息。
        // shiroLoginFailure:就是shiro异常类的全类名.
        String exception = (String) request.getAttribute("shiroLoginFailure");
        System.out.println("exception=" + exception);
        String msg = "";
        if (exception != null) {
            if (UnknownAccountException.class.getName().equals(exception)) {
                System.out.println("UnknownAccountException -- > 账号不存在：");
                msg = "UnknownAccountException -- > 账号不存在：";
            } else if (IncorrectCredentialsException.class.getName().equals(exception)) {
                System.out.println("IncorrectCredentialsException -- > 密码不正确：");
                msg = "IncorrectCredentialsException -- > 密码不正确：";
            } else if ("kaptchaValidateFailed".equals(exception)) {
                System.out.println("kaptchaValidateFailed -- > 验证码错误");
                msg = "kaptchaValidateFailed -- > 验证码错误";
            } else {
                msg = "else >> " + exception;
                System.out.println("else -- >" + exception);
            }
        }
        map.put("msg", msg);
        // 此方法不处理登录成功,由shiro进行处理
        return "/login";
    }

    @RequestMapping("/403")
    public String unauthorizedRole() {
        System.out.println("------没有权限-------");
        return "403";
    }

}
