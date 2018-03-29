package com.litsoft.evaluateserver.repository;

import com.litsoft.evaluateserver.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    //角色删除时，用户角色关联的数据删除
    @Modifying
    @Query(value = "delete from user_role where role_id = ?1", nativeQuery = true)
    int deleteUserRoleByRoleId(Integer userId);

    //角色删除时，角色权限关联的数据删除
    @Modifying
    @Query(value = "delete from role_permission where role_id = ?1", nativeQuery = true)
    int deleteRolePermissionByRoleId(Integer userId);
}
