package com.litsoft.evaluateserver.api;

import com.litsoft.evaluateserver.entity.Permission;
import com.litsoft.evaluateserver.entity.Role;
import com.litsoft.evaluateserver.entity.User;
import com.litsoft.evaluateserver.entity.sysVo.PerVo;
import com.litsoft.evaluateserver.service.PageQueryService;
import com.litsoft.evaluateserver.service.PermissionService;
import com.litsoft.evaluateserver.service.RoleService;
import com.litsoft.evaluateserver.service.UserService;
import com.litsoft.evaluateserver.util.LayUiData;
import com.litsoft.evaluateserver.util.PageInfo;
import com.litsoft.evaluateserver.util.QueryParam;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/per")
public class SysPermissionController {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    public PageQueryService pageQueryService;

    @SuppressWarnings("unchecked")
    @RequiresPermissions("sys:menu")
    @RequestMapping("/perList")
    public String adminList(Model model, @RequestParam Map<String, Object> params){

        List<Permission> perList = permissionService.findMenuByUserId();
        model.addAttribute("perList", perList);
        return "/view/sys/admin-rule";
    }

    @RequestMapping("/perListView")
    public String addPerm(Model model) {
        List<Permission> perList = permissionService.findMenuByUserId();
        model.addAttribute("perList",perList);
        return  "/view/front/admin-rule";
    }

    @RequestMapping("/addPermView")
    public String addPermView(Model model,Integer id) {

        Permission permission = permissionService.findById(id);
        model.addAttribute("permission", permission);
        return "/view/front/per-add";
    }

    @ResponseBody
    @RequestMapping("/addPermDo")
    public String addPermDo(@RequestBody PerVo perVo) {
        System.out.println("come in");
        Permission permission = permissionService.savePermission(perVo);

        return !ObjectUtils.isEmpty(permission)? "success":"filed";
    }

    @RequestMapping("roleDetail")
    public String permDetail(Model model, @RequestParam("id") Integer id) {
        Permission perm = permissionService.findById(id);
        model.addAttribute("perm", perm);
        return "/view/front/per-detail";
    }

    @RequestMapping("/permEdit")
    public String permEdit(Model model, @RequestParam("id") Integer id) {

        Permission perm = permissionService.findById(id);
        model.addAttribute("perm", perm);
        return "/view/front/per-edit";
    }

    @RequestMapping("/deletePerm")
    public String deletePerms(@RequestParam("ids") String[] ids) {
        System.out.println("delete");
        return "";
    }

    @ResponseBody
    @RequestMapping("/deleteSinglePerm")
    public String deletePerm(@RequestParam("id") Integer id) {
        return permissionService.deletePermission(id) ? "success": "filed";
    }
}
