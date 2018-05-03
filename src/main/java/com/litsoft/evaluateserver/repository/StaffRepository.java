package com.litsoft.evaluateserver.repository;

import com.litsoft.evaluateserver.entity.Staff;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface StaffRepository extends JpaRepository<Staff, Integer> {
    //根据用户名查询用户信息
    @Query("select u from Staff u where u.staffNo=:username")
    Staff findByUsername(@Param("username") String username);

    //用户删除时，关联的角色数据删除
    @Modifying
    @Query(value = "delete from user_role where user_id = ?1", nativeQuery = true)
    int deleteUserRoleByUserId(Integer userId);

    Page<Staff> findAll(Specification<Staff> spec, Pageable pageable);

}
