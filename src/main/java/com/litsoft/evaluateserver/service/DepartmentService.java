package com.litsoft.evaluateserver.service;

import com.litsoft.evaluateserver.entity.Department;
import com.litsoft.evaluateserver.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public List<Department> findAll() {

        return departmentRepository.findAll();
    }
}
