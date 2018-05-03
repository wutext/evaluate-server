package com.litsoft.evaluateserver.api;

import com.alibaba.fastjson.JSON;
import com.litsoft.evaluateserver.entity.Batch;
import com.litsoft.evaluateserver.entity.DepartUtil;
import com.litsoft.evaluateserver.entity.Department;
import com.litsoft.evaluateserver.entity.Role;
import com.litsoft.evaluateserver.entity.Staff;
import com.litsoft.evaluateserver.entity.User;
import com.litsoft.evaluateserver.entity.sysVo.UserVo;
import com.litsoft.evaluateserver.service.BatchService;
import com.litsoft.evaluateserver.service.DepartmentService;
import com.litsoft.evaluateserver.service.PageQueryService;
import com.litsoft.evaluateserver.service.RoleService;
import com.litsoft.evaluateserver.service.StaffService;
import com.litsoft.evaluateserver.service.UserScoreService;
import com.litsoft.evaluateserver.service.UserService;
import com.litsoft.evaluateserver.util.LayUiData;
import com.litsoft.evaluateserver.util.PageInfo;
import com.litsoft.evaluateserver.util.QueryParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/staff")
public class StaffController {
    @Autowired
    private MessageSource messageSource;

    @Autowired
    private PageQueryService pageQueryService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private BatchService batchService;

    @Autowired
    private UserScoreService userScoreService;


    @RequestMapping("/staffView")
    public String staffView(Model model, Integer departmentId, String staffName, Integer departUtilId, Integer batchId) {

        List<Department> departmentList = departmentService.findAll();
        List<Batch> batchList = batchService.findBatchList();
        batchId = batchId == null ? batchList.get(0).getId() : batchId;
        model.addAttribute("departmentId", departmentId);
        model.addAttribute("departUtilId", departUtilId);
        model.addAttribute("staffName", staffName);
        model.addAttribute("departments", departmentList);
        model.addAttribute("batchList", batchList);
        model.addAttribute("batchId", batchId);
        return "/view/front/staff-list";
    }

    @ResponseBody
    @SuppressWarnings("unchecked")
    @RequestMapping("/staffList")
    public LayUiData staffList(@RequestParam Map<String, Object> params) {
        QueryParam param = new QueryParam(params);
        if (!StringUtils.isEmpty(param.getDepartment()) && StringUtils.isEmpty(param.getDepartUtil())) {
            param.setUtilIds(departmentService.getUtilIds(param.getDepartment(), param.getDepartUtil()));
        }

        Page<Staff> pageUser = pageQueryService.findUserBySelect(param);
        String batchNumber = getBatchName(param.getBatchId());

        List<UserVo> users = new ArrayList<>();
        pageUser.getContent().forEach(user -> {
            String departUtilName = "";
            if (!ObjectUtils.isEmpty(user.getDepartUtil())) {
                departUtilName = user.getDepartUtil().getName();
            }

            UserVo userVo = new UserVo(user.getId(), user.getStaffName(), user.getCompany(), user.getProject(), departUtilName, user.getStaffNo());
            userVo.setRaters(getUserScoreStatus(user.getId(), batchNumber));
            users.add(userVo);
        });
        PageInfo<Staff> pageInfo = new PageInfo((int) pageUser.getTotalElements(), param.getPage(),
            param.getLimit(), users);
        return LayUiData.data(pageInfo.getTotalSize(), pageInfo.getPageList());
    }

    private String getBatchName(String batchId) {

        List<Batch> batchList = batchService.findBatchList();

        if (StringUtils.isEmpty(batchId)) {
            return batchList.get(0).getBatchNumber();
        } else {
            Batch batch = batchService.findOne(Integer.valueOf(batchId));
            return batch.getBatchNumber();
        }
    }

    private List<Integer> getUserScoreStatus(Integer id, String batchNumber) {
        return userScoreService.getUserScoreStatusByUserId(id, batchNumber);
    }

    @RequestMapping("/addStaffView")
    public String addStaffView(Model model) {
        List<Role> roles = roleService.findAll();
        List<Department> departmentList = departmentService.findAll();
        model.addAttribute("roles", roles);
        model.addAttribute("departmentList", departmentList);
        return "/view/front/staff-add";
    }

    @ResponseBody
    @RequestMapping(value = "/addStaffDo")
    public String addStaffDo(@RequestBody UserVo user) throws Exception {

        Staff u = staffService.saveUser(user);
        if (!ObjectUtils.isEmpty(u)) {
            return "success";
        } else {
            return "failed";
        }
    }

    @RequestMapping("/staffEdit")
    public String staffEdit(Model model, @RequestParam("id") Integer id,
                            @RequestParam("operate") String operate) {

        List<Department> departmentList = departmentService.findAll();
        Staff user = staffService.findById(id);
        DepartUtil util = new DepartUtil();
        if (!ObjectUtils.isEmpty(user.getDepartUtil())) {
            util = departmentService.findDepartUtilById(user.getDepartUtil().getId());
            model.addAttribute("departmentId", util.getDepartment().getId());
            model.addAttribute("departUtilId", util.getId());
        }

        model.addAttribute("user", user);
        model.addAttribute("operate", operate);
        model.addAttribute("departmentList", departmentList);

        return "/view/front/staff-edit";
    }

    @ResponseBody
    @RequestMapping("/deleteStaffs")
    public String deleteStaffs(@RequestBody String ids) {
        staffService.deleteIds(ids);
        return "success";
    }

    @ResponseBody
    @RequestMapping("/deleteSingleStaff")
    public String deleteSingleStaff(@RequestBody User user) {

        staffService.deleteSingleUser(user.getId());
        return "success";
    }


    @ResponseBody
    @RequestMapping("/verifyStaff")
    public boolean uniqueStaff(@RequestBody String name) {
        String username = (String) JSON.parse(name);
        return !ObjectUtils.isEmpty(staffService.findByUsername(username)) ? true : false;
    }
}
