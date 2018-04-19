package com.litsoft.evaluateserver.service;

import com.litsoft.evaluateserver.entity.DepartUtil;
import com.litsoft.evaluateserver.entity.Department;
import com.litsoft.evaluateserver.entity.basic.BasicAttribute;
import com.litsoft.evaluateserver.entity.vo.DepartmentVo;
import com.litsoft.evaluateserver.exception.NotFoundException;
import com.litsoft.evaluateserver.repository.DepartmentRepository;
import com.litsoft.evaluateserver.repository.DepartmentUtilRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class DepartmentUtilService {

    @Autowired
    private DepartmentUtilRepository departUtilRepository;

    @Autowired
    private DepartmentRepository departmentRepository;


    public DepartUtil findDepartUtil(Integer id) {
        return departUtilRepository.findOne(id);
    }

    @Transactional
    public DepartUtil savePermission(DepartmentVo departmentVo) {
        DepartUtil departUtil = getDepartment(departmentVo);
        return departUtilRepository.save(departUtil);
    }

    private DepartUtil getDepartment(DepartmentVo dv) {
        Department department = departmentRepository.findOne(dv.getDepartId());
        DepartUtil departUtil = new DepartUtil();
        departUtil.setId(dv.getUtilId());
        departUtil.setName(dv.getUtilName());
        departUtil.setSort(dv.getSort());
        departUtil.setDepartmentId(dv.getDepartId());
        departUtil.getDepartment().setName(department.getName());
        departUtil.getDepartment().setSort(department.getSort());
        return departUtil;
    }

    @Transactional
    public boolean deleteUtil(Integer id) {
       departUtilRepository.deleteById(id);
       return true;
    }
}
