package com.litsoft.evaluateserver.repository;

import com.litsoft.evaluateserver.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Integer> {
    //权限删除时，权限角色关联的数据删除
    @Modifying
    @Query(value = "delete from role_permission where permission_id = ?1", nativeQuery = true)
    int deleteRolePermissionByPermissionId(Integer userId);

    @Query(value = "select distinct p.* from permission p " +
        " inner join role_permission rp on p.id = rp.permission_id " +
        " inner join role r on r.id = rp.role_id" +
        " where r.id in (" +
        " select r.id from user u " +
        " left join user_role ur on u.id = ur.user_id " +
        " left join role r on r.id = ur.role_id" +
        " where u.id=?1 and status=1 and resource_type='menu' order by sort ) ORDER BY p.sort  ", nativeQuery = true)
    List<Permission> findMenuByUserId(Integer uId);

    @Modifying
    @Query(value = "update permission p set p.parent_ids = ?1 where p.id=?2", nativeQuery = true)
    void updatePermissionById(String parentIds, Integer id);

    @Query(value="select id from permission where par_id=?1",nativeQuery = true)
    List<Integer> findChildId(Integer id);

    @Query(value="select * from permission where status=1",nativeQuery = true)
    List<Permission> findAllMenu();

    @Query(value="select distinct rp.permission_id from role r inner join role_permission rp on r.id=rp.role_id where r.id=?1",nativeQuery = true)
    List<Integer> findRolePermission(Integer roleId);

    public Permission findByName(String name);
}
