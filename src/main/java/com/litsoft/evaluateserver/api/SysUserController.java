package com.litsoft.evaluateserver.api;

import com.litsoft.evaluateserver.entity.Batch;
import com.litsoft.evaluateserver.entity.Department;
import com.litsoft.evaluateserver.entity.Role;
import com.litsoft.evaluateserver.entity.User;
import com.litsoft.evaluateserver.entity.sysVo.UserVo;
import com.litsoft.evaluateserver.exception.MessageException;
import com.litsoft.evaluateserver.exception.NotFoundException;
import com.litsoft.evaluateserver.service.BatchService;
import com.litsoft.evaluateserver.service.DepartmentService;
import com.litsoft.evaluateserver.service.PageQueryService;
import com.litsoft.evaluateserver.service.RoleService;
import com.litsoft.evaluateserver.service.UserScoreService;
import com.litsoft.evaluateserver.service.UserService;
import com.litsoft.evaluateserver.util.LayUiData;
import com.litsoft.evaluateserver.util.PageInfo;
import com.litsoft.evaluateserver.util.QueryParam;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/sys")
public class SysUserController {

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private PageQueryService pageQueryService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private BatchService batchService;

    @Autowired
    private UserScoreService userScoreService;

    @RequestMapping("/desktop")
    public String desktop() {
        return "/view/front/desktop";
    }

    @RequestMapping("/adminView")
    public String adminView(Model model, Integer departmentId, String username, Integer departUtilId, Integer batchId) {

        List<Department> departmentList = departmentService.findAll();
        List<Batch> batchList = batchService.findBatchList();
        batchId = batchId==null? batchList.get(0).getId() : batchId;
        model.addAttribute("departmentId", departmentId);
        model.addAttribute("departUtilId", departUtilId);
        model.addAttribute("username", username);
        model.addAttribute("departments", departmentList);
        model.addAttribute("batchList", batchList);
        model.addAttribute("batchId", batchId);
        return "/view/front/admin-list";
    }

    @ResponseBody
    @SuppressWarnings("unchecked")
    @RequestMapping("/adminList")
    public LayUiData adminList(@RequestParam Map<String, Object> params){
        QueryParam param = new QueryParam(params);
        Page<User> pageUser = pageQueryService.findUserPageSearch(param);

        String batchNumber = getBatchName(param.getBatchId());

        List<UserVo> users = new ArrayList<>();
        pageUser.getContent().forEach(user -> {
            UserVo userVo = new UserVo(user.getId(), user.getUsername(), String.valueOf(user.getState()), user.getPhone(), user.getEmail());
            userVo.setRaters(getUserScoreStatus(user.getId(), batchNumber));
            users.add(userVo);
        });
        PageInfo<User> pageInfo = new PageInfo((int) pageUser.getTotalElements(), param.getPage(),
            param.getLimit(), users);
        return LayUiData.data(pageInfo.getTotalSize(), pageInfo.getPageList());
    }

    private String getBatchName(String batchId) {

        List<Batch> batchList = batchService.findBatchList();

        if(StringUtils.isEmpty(batchId)) {
            return batchList.get(0).getBatchNumber();
        }else {
            Batch batch = batchService.findOne(Integer.valueOf(batchId));
            return batch.getBatchNumber();
        }
    }

    private List<Integer> getUserScoreStatus(Integer id, String batchNumber) {
        return userScoreService.getUserScoreStatusByUserId(id, batchNumber);
    }

    @RequestMapping("/addUserView")
    public String addUser(Model model) {
        List<Role> roles = roleService.findAll();
        model.addAttribute("roles", roles);
        return "/view/front/admin-add";
    }

    @ResponseBody
    @RequestMapping(value = "/addUserDo")
    public String addUserDo(@RequestBody UserVo user) throws Exception {

        User u = userService.saveUser(user);
        if(!ObjectUtils.isEmpty(u)) {
            return "success";
        }else {
            return "failed";
        }
    }

    @RequestMapping("/userEdit")
    public String userEdit(Model model, @RequestParam("id") Integer id,
                           @RequestParam("operate") String operate) {

        List<Role> roles = roleService.findAll();
        User user = userService.findById(id);
        model.addAttribute("roles", roles);
        model.addAttribute("user", user);
        model.addAttribute("operate", operate);
        return "/view/front/admin-edit";
    }

    @ResponseBody
    @RequestMapping("/deleteUsers")
    public String deleteUsers(@RequestBody String ids) {

        userService.deleteIds(ids);
        return "success";
    }

    @ResponseBody
    @RequestMapping("/deleteSingleUser")
    public String deleteSingleUser(@RequestBody User user) {

        userService.deleteSingleUser(user.getId());
        return "success";
    }
}
