package com.litsoft.evaluateserver.service;

import com.litsoft.evaluateserver.entity.Department;
import com.litsoft.evaluateserver.exception.NotFoundException;
import com.litsoft.evaluateserver.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public List<Department> findAll() {

        return departmentRepository.findAll();
    }

    public Department findById(Integer departId) {

        return departmentRepository.findOne(departId);
    }

    @Transactional
    public Department savePermission(Department departmentVo) {
        return departmentRepository.save(departmentVo);
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
}
