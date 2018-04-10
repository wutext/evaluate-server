package com.litsoft.evaluateserver.api;

import com.litsoft.evaluateserver.entity.Permission;
import com.litsoft.evaluateserver.entity.Role;
import com.litsoft.evaluateserver.entity.User;
import com.litsoft.evaluateserver.service.PageQueryService;
import com.litsoft.evaluateserver.service.PermissionService;
import com.litsoft.evaluateserver.service.RoleService;
import com.litsoft.evaluateserver.service.UserService;
import com.litsoft.evaluateserver.util.LayUiData;
import com.litsoft.evaluateserver.util.PageInfo;
import com.litsoft.evaluateserver.util.QueryParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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
        List<Permission> perList = permissionService.findAll();
        model.addAttribute("perList", perList);
        return "/view/sys/admin-rule";
      /*  QueryParam param = new QueryParam(params);
        Page<Permission> pagePermission = pageQueryService.findPermissionNoCriteria(param);

        PageInfo<Permission> pageInfo = new PageInfo((int) pagePermission.getTotalElements(), param.getPage(),
            param.getLimit(), pagePermission.getContent());
        return LayUiData.data(pageInfo.getTotalSize(), pageInfo.getPageList());*/
    }

    @RequestMapping("/perListView")
    public String addPerm() {
        List<Permission> perList = permissionService.findAll();
        return  "/view/front/perm-add";
    }

    @ResponseBody
    @RequestMapping("/addPermDo")
    public String addPermDo(@RequestBody Permission permParam) {
        return permissionService.insert(permParam)? "success" : "filed";
    }

    @RequestMapping("roleDetail")
    public String permDetail(Model model, @RequestParam("id") Integer id) {
        Permission perm = permissionService.findById(id);
        model.addAttribute("perm", perm);
        return "/view/front/perm-detail";
    }

    @RequestMapping("/permEdit")
    public String permEdit(Model model, @RequestParam("id") Integer id) {

        Permission perm = permissionService.findById(id);
        model.addAttribute("perm", perm);
        return "/view/front/perm-edit";
    }

    @RequestMapping("/deletePerm")
    public String deletePerms(@RequestParam("ids") String[] ids) {
        System.out.println("delete");
        return "";
    }

    @RequestMapping("/deleteOnePerm")
    public String deletePerm(@RequestParam("id") Integer id) {
        return permissionService.delete(id) ? "success": "filed";
    }
}
