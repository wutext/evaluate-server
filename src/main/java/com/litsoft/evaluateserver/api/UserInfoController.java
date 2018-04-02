package com.litsoft.evaluateserver.api;

import com.litsoft.evaluateserver.entity.User;
import com.litsoft.evaluateserver.service.UserService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/userInfo")
public class UserInfoController {

    @Autowired
    private UserService userService;
    /**
     * 用户查询.
     * @return
     */
    @RequestMapping("/userList")
    /*@RequiresPermissions("userInfo:view")//权限管理;*/
    public String userInfo(Model model){
        User user = (User) userService.findAll();
        model.addAttribute("user", user);
        return "userInfo";
    }

    /**
     * 用户添加;
     * @return
     */
    @RequestMapping("/userAdd")
    @RequiresPermissions("userInfo:add")//权限管理;
    public String userInfoAdd(){

        return "/view/front/admin-add";
    }

    /**
     * 用户删除;
     * @return
     */
    @RequestMapping("/userDel")
    @RequiresPermissions("userInfo:del")//权限管理;
    public String userDel(){
        return "userInfoDel";
    }
}