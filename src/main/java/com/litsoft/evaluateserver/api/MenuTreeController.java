package com.litsoft.evaluateserver.api;

import com.alibaba.fastjson.JSON;
import com.litsoft.evaluateserver.entity.Permission;
import com.litsoft.evaluateserver.entity.sysVo.MenuTree;
import com.litsoft.evaluateserver.service.PermissionService;
import com.litsoft.evaluateserver.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    public String getMenuTreeView() {

        return "/view/include/menuTree";
    }

    @ResponseBody
    @RequestMapping(value = "/menuTreeList")
    public Map<String, List> getMenuTreePermission(Model model) {

        List<Integer> selectIds = findSelectedPermission();
        List<Permission> permissionList = permissionService.findAllMenu();
        List<MenuTree> menuTrees = roleService.getViewMenu(permissionList);
        Map<String, List> map = new HashMap<>();
        map.put("menu", menuTrees);
        map.put("selectIds", selectIds);
        return map;
    }

    private List<Integer> findSelectedPermission() {
        List<Permission> permissionList = permissionService.findMenuByUserId();
        List<Integer> integers = new ArrayList<>();
        permissionList.forEach(permission -> integers.add(permission.getId()));
        return integers;
    }
}
