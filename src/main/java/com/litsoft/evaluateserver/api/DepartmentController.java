package com.litsoft.evaluateserver.api;

import com.litsoft.evaluateserver.entity.Department;
import com.litsoft.evaluateserver.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @RequestMapping("/departList")
    public String findDepartList(Model model) {

        List<Department> departments = departmentService.findAll();
        model.addAttribute("departments", departments);
        return "/view/front/depart-list";
    }

    @RequestMapping("/addDepartView")
    public String addPermView(Model model, @RequestParam("id") String departId,
                              @RequestParam("type") String type) {

        model.addAttribute("departId", departId);
        return "/view/front/depart-add";
    }

    @ResponseBody
    @RequestMapping("/addDepartDo")
    public String addPermDo(@RequestBody Department departmentVo) {

        Department department = departmentService.savePermission(departmentVo);

        return !ObjectUtils.isEmpty(department)? "success":"filed";
    }

    @RequestMapping("/departEdit")
    public String permEdit(Model model, @RequestParam("id") Integer id) {

        Department department = departmentService.findById(id);
        model.addAttribute("department", department);
        return "/view/front/depart-add";
    }

    @ResponseBody
    @RequestMapping("/deleteSingleDepart")
    public String deletePerm(@RequestParam("id") Integer id) {
        return departmentService.deleteDepartment(id) ? "success": "filed";
    }

}
