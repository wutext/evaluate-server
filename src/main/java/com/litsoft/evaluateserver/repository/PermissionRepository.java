package com.litsoft.evaluateserver.repository;

import com.litsoft.evaluateserver.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.PostRemove;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {
    //权限删除时，权限角色关联的数据删除
    @Modifying
    @Query(value = "delete from role_permission where permission_id = ?1", nativeQuery = true)
    int deleteRolePermissionByPermissionId(Integer userId);
}
