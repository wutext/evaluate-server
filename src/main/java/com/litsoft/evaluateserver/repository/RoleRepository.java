package com.litsoft.evaluateserver.repository;

import com.litsoft.evaluateserver.entity.Role;
import com.litsoft.evaluateserver.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer>, JpaSpecificationExecutor<Role> {
    //角色删除时，用户角色关联的数据删除
    @Modifying
    @Query(value = "delete from user_role where role_id = ?1", nativeQuery = true)
    int deleteUserRoleByRoleId(Integer userId);

    //角色删除时，角色权限关联的数据删除
    @Modifying
    @Query(value = "delete from role_permission where role_id = ?1", nativeQuery = true)
    int deleteRolePermissionByRoleId(Integer userId);

    @Modifying
    @Query(value = "delete from Role role where role.id in (?1)", nativeQuery = true)
    void deleteArrayIds(List<Long> roleIds);

    @Modifying
    @Query(value = "delete from role_permission where role_id =?", nativeQuery = true)
    void deleteRolePermission(Integer id);

    public Role findByRole(String name);
}
