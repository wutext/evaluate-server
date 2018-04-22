package com.litsoft.evaluateserver.service;

import com.litsoft.evaluateserver.entity.DepartUtil;
import com.litsoft.evaluateserver.entity.Department;
import com.litsoft.evaluateserver.entity.basic.BasicAttribute;
import com.litsoft.evaluateserver.entity.vo.DepartmentVo;
import com.litsoft.evaluateserver.exception.NotFoundException;
import com.litsoft.evaluateserver.repository.DepartmentRepository;
import com.litsoft.evaluateserver.repository.DepartmentUtilRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    @Autowired
    private DepartmentUtilRepository departUtilRepository;

    public List<Department> findAll() {

        return departmentRepository.findAll();
    }

    public Department findById(Integer departId) {

        return departmentRepository.findOne(departId);
    }

    @Transactional
    public Department savePermission(DepartmentVo departmentVo) {
        Department department = getDepartment(departmentVo);
        return departmentRepository.save(department);
    }

    private Department getDepartment(DepartmentVo dv) {

        Department department = new Department();

        department.setId(dv.getDepartId());
        department.setName(dv.getDepartName());
        department.setSort(dv.getSort());
        return department;

    }

    @Transactional
    public boolean deleteDepartment(Integer id) {

        try {
            departmentRepository.delete(id);
            return true;
        } catch (Exception e) {
            new NotFoundException("error: 删除失败");
            return false;
        }
    }

    public List<Integer> getUtilIds(String departmentId, String departUtil) {
        List<Integer> utilIds = new ArrayList<Integer>();
        Department department = departmentRepository.findOne(Integer.valueOf(departmentId));
        if(!CollectionUtils.isEmpty(department.getDepartUtil())) {
            department.getDepartUtil().forEach(utilId -> {
                utilIds.add(utilId.getId());
            });
        }

        return utilIds;
    }
}
