package com.litsoft.evaluateserver.repository;

import com.litsoft.evaluateserver.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department,Integer> {

    
}
