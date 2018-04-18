package com.litsoft.evaluateserver.api;

import com.litsoft.evaluateserver.entity.Department;
import com.litsoft.evaluateserver.service.DepartmentService;
import com.litsoft.evaluateserver.util.LayUiData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @RequestMapping("/departList")
    public String findDepartList(Model model) {

        /*List<Department> departments = departmentService.findAll();

        model.addAttribute("departments", departments);*/
        return "/view/front/depart-list";
    }

}
