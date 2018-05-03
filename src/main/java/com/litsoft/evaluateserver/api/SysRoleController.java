package com.litsoft.evaluateserver.api;

import com.alibaba.fastjson.JSON;
import com.litsoft.evaluateserver.entity.Permission;
import com.litsoft.evaluateserver.entity.Role;
import com.litsoft.evaluateserver.entity.User;
import com.litsoft.evaluateserver.entity.basic.BasicAttribute;
import com.litsoft.evaluateserver.entity.sysVo.RoleVo;
import com.litsoft.evaluateserver.entity.sysVo.UserVo;
import com.litsoft.evaluateserver.service.PageQueryService;
import com.litsoft.evaluateserver.service.PermissionService;
import com.litsoft.evaluateserver.service.RoleService;
import com.litsoft.evaluateserver.service.UserService;
import com.litsoft.evaluateserver.util.LayUiData;
import com.litsoft.evaluateserver.util.PageInfo;
import com.litsoft.evaluateserver.util.QueryParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.aspectj.lang.annotation.SuppressAjWarnings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/role")
public class SysRoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    public PageQueryService pageQueryService;

    @ResponseBody
    /*@RequiresPermissions("sys:menu")*/
    @RequestMapping("/roleList")
    public LayUiData adminList(@RequestParam Map<String, Object> params) {
        QueryParam param = new QueryParam(params);
        Page<Role> pageRole = pageQueryService.findRolePageSearch(param);
        List<RoleVo> roles = new ArrayList<>();
        pageRole.getContent().forEach(role -> {
            RoleVo roleVo = new RoleVo(role.getId(), role.getRole(), role.getDescription());
            roles.add(roleVo);
        });
        PageInfo<Role> pageInfo = new PageInfo((int) pageRole.getTotalElements(), param.getPage(),
            param.getLimit(), roles);
        return LayUiData.data(pageInfo.getTotalSize(), pageInfo.getPageList());
    }

    @RequestMapping("/adminRoleView")
    public String adminRole(Model model, String roleSearch) {
        model.addAttribute("roleSearch", roleSearch);
        return "/view/front/admin-role";
    }

    @RequestMapping("/addRoleView")
    public String addRole(Model model) {

        List<Permission> permissions = permissionService.findAll();

        model.addAttribute("permissions", permissions);
        model.addAttribute("attr", BasicAttribute.BASIC_ADD);
        return "/view/front/role-add";
    }

    @ResponseBody
    @RequestMapping(value = "/addRoleDo", method = RequestMethod.POST)
    public String addRoleDo(@RequestBody RoleVo roleVo) {
        Role role = roleService.getRoleFromRoleVo(roleVo);
        String back = roleService.insert(role) ? "success" : "filed";
        return back;
    }

    @RequestMapping("/roleEdit")
    public String roleEdit(Model model, @RequestParam("id") Integer id) {

        List<Permission> permissions = permissionService.findAll();
        RoleVo role = roleService.findById(id);
        model.addAttribute("permissions", permissions);
        model.addAttribute("role", role);
        model.addAttribute("attr", BasicAttribute.BASIC_EDIT);
        return "/view/front/role-add";
    }

    @ResponseBody
    @RequestMapping(value = "/deleteRole", method = RequestMethod.GET)
    public String deleteRole(@RequestParam("ids") List<Long> ids) {
        roleService.deleteArrayIds(ids);
        return "";
    }

    @RequestMapping("/roleDetail")
    public String roleDetail(Model model, @RequestParam("id") Integer id) {
        RoleVo role = roleService.findById(id);
        model.addAttribute("role", role);
        return "/view/front/role-detail";
    }

    @ResponseBody
    @RequestMapping(value = "/deleteSingleRole", method = RequestMethod.POST)
    public void deleteSingleRole(@RequestBody RoleVo roleVo) {
        roleService.delete(roleVo.getId());
    }

    @ResponseBody
    @RequestMapping("/verifyRoleName")
    public boolean verifyRoleName(@RequestBody String name) {
        String roleName = (String) JSON.parse(name);
        return !ObjectUtils.isEmpty(roleService.findByRole(roleName))? true:false;
    }
}
