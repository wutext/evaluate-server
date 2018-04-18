package com.litsoft.evaluateserver.api;

import com.litsoft.evaluateserver.entity.Permission;
import com.litsoft.evaluateserver.entity.sysVo.MenuTree;
import com.litsoft.evaluateserver.service.PermissionService;
import com.litsoft.evaluateserver.service.RoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/tree")
public class MenuTreeController {

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RoleService roleService;

    @RequestMapping("/menuTreeView")
    public String getMenuTreeView(Model model, @RequestParam("id") String roleId, @RequestParam("attr") String attr) {

        model.addAttribute("attr", attr);
        model.addAttribute("roleId", roleId);
        return "/view/include/menuTree";
    }

    @ResponseBody
    @RequestMapping(value = "/menuTreeList")
    public Map<String, List> getMenuTreePermission(Model model, @RequestParam("roleId") String roleId) {

        //查询出之前选择的菜单
        List<Integer> selectIds = findRolePermission(roleId);
        //查询出全部菜单
        List<Permission> permissionList = permissionService.findAllMenu();
        List<MenuTree> menuTrees = roleService.getViewMenu(permissionList);
        Map<String, List> map = new HashMap<>();
        map.put("menu", menuTrees);
        map.put("selectIds", selectIds);
        return map;
    }

    @ResponseBody
    @RequestMapping(value = "/backMenuId", method = RequestMethod.GET)
    public String backMenuId(String menuIds) {
        System.out.println("dddddd");
        return menuIds;
    }

    private List<Integer> findRolePermission(String roleId) {
        List<Integer> integers = new ArrayList<>();
        if(StringUtils.isNotEmpty(roleId)) {
            return permissionService.findRolePermission(Integer.valueOf(roleId).intValue());
        }else {
            return integers;
        }

    }
}
