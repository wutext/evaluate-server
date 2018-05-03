package com.litsoft.evaluateserver.api;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.litsoft.evaluateserver.entity.DepartUtil;
import com.litsoft.evaluateserver.entity.Department;
import com.litsoft.evaluateserver.entity.basic.BasicAttribute;
import com.litsoft.evaluateserver.entity.vo.DepartmentVo;
import com.litsoft.evaluateserver.service.DepartmentService;
import com.litsoft.evaluateserver.service.DepartmentUtilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private DepartmentUtilService departUtilService;

    @RequestMapping("/departList")
    public String findDepartList(Model model) {

        List<Department> departments = departmentService.findAll();
        model.addAttribute("departments", departments);
        return "/view/front/depart-list";
    }

    @RequestMapping("/addDepartView")
    public String addPermView(Model model, Department department,
                              @RequestParam("id") Integer id,
                              @RequestParam("type") String type) {


        //添加部门，处
        if(type.equals(BasicAttribute.DEPART_ADDNEXT)) {
            if(id!=null) {
                department = departmentService.findById(Integer.valueOf(id));
            }
        }
        //修改部门
        if(type.equals(BasicAttribute.DEPART_EDIT_DEPART)) {
            department = departmentService.findById(Integer.valueOf(id));
        }
        //修改处
        if(type.equals(BasicAttribute.DEPART_EDIT_UTIL)) {
            DepartUtil departUtil = departUtilService.findDepartUtil(id);
            department = departUtil.getDepartment();
            model.addAttribute("departUtil", departUtil);
        }

        model.addAttribute("department", department);
        model.addAttribute("type", type);
        model.addAttribute("departId", id);
        return "/view/front/depart-add";
    }

    @ResponseBody
    @RequestMapping("/addDepartDo")
    public String addPermDo(@RequestBody DepartmentVo departmentVo) {

        Department department = new Department();
        DepartUtil departUtil = new DepartUtil();
        if(StringUtils.isEmpty(departmentVo.getUtilName())) {
            department = departmentService.savePermission(departmentVo);
            return !ObjectUtils.isEmpty(department)? "success":"filed";
        }else{
            departUtil = departUtilService.savePermission(departmentVo);
            return !ObjectUtils.isEmpty(departUtil)? "success":"filed";
        }
    }

    @RequestMapping("/departEdit")
    public String permEdit(Model model, @RequestParam("id") Integer id) {

        Department department = departmentService.findById(id);
        model.addAttribute("department", department);
        model.addAttribute("type", BasicAttribute.DEPART_EDIT_DEPART);
        return "/view/front/depart-add";
    }

    @ResponseBody
    @RequestMapping("/deleteSingleDepart")
    public String deletePerm(@RequestParam("id") Integer id) {
        return departmentService.deleteDepartment(id) ? "success": "filed";
    }

    @ResponseBody
    @RequestMapping("/deleteSingleUtil")
    public String deleteSingleUtil(@RequestParam("id") Integer id) {
        return departUtilService.deleteUtil(id)? "success": "filed";
    }

    @ResponseBody
    @RequestMapping("/findUtil")
    public JSONArray findDepartment(@RequestParam("id") Integer id) {

        List<DepartUtil> departUtils = new ArrayList<>();
        Map<String, List> map = new HashMap<>();
        Department department = new Department();
        if(id!=null) {
            department = departmentService.findById(id);
        }
        JSONArray json = new JSONArray();
        if(!CollectionUtils.isEmpty(department.getDepartUtil())) {
            department.getDepartUtil().forEach(departUtil -> {
                JSONObject jo = new JSONObject();
                jo.put("id", departUtil.getId());
                jo.put("name", departUtil.getName());
                json.add(jo);
            });
        }
        return json;
    }

    @ResponseBody
    @RequestMapping("/verifyDepartmentName")
    public boolean verifyDepartmentName(@RequestBody String name) {
        String deptame = (String) JSON.parse(name);
        return !ObjectUtils.isEmpty(departmentService.findByName(deptame))? true:false;
    }

    @ResponseBody
    @RequestMapping("/verifyDeptUtilName")
    public boolean verifyDeptUtilName(@RequestBody String name) {
        String deptame = (String) JSON.parse(name);
        return !ObjectUtils.isEmpty(departUtilService.findByName(deptame))? true:false;
    }
}
