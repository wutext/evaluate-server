package com.litsoft.evaluateserver.service;

import com.litsoft.evaluateserver.entity.Permission;
import com.litsoft.evaluateserver.entity.User;
import com.litsoft.evaluateserver.repository.PermissionRepository;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Service
public class PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    //权限添加
    @Transactional
    public boolean insert(Permission permission) {
        boolean flag = false;
        Permission p = permissionRepository.save(permission);
        if (p != null) {
            flag = true;
        }
        return flag;
    }

    //权限修改
    @Transactional
    public boolean update(Permission permission) {
        boolean flag = false;
        Permission p = permissionRepository.saveAndFlush(permission);
        if (p != null) {
            flag = true;
        }
        return flag;
    }

    //权限删除
    @Transactional
    public boolean delete(Integer id) {
        boolean flag = false;
        permissionRepository.delete(id);
        boolean userRoleFalg = false;
        userRoleFalg = this.deleteRolePermissionByPermissionId(id);
        if (userRoleFalg) {
            flag = true;
        }
        return flag;
    }

    //权限删除时，角色权限关联的数据删除
    @Transactional
    public boolean deleteRolePermissionByPermissionId(Integer userId) {
        boolean flag = false;
        int num = permissionRepository.deleteRolePermissionByPermissionId(userId);
        if (num > 0) {
            flag = true;
        }
        return flag;
    }


    public Permission findById(Integer id) {
        return permissionRepository.findOne(id);
    }

    public List<Permission> findAll() {

        return permissionRepository.findAll();
    }

    public List<Permission> findMenuByUserId() {

        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        List<Permission> permissionList = new ArrayList<>();

        return permissionList;
    }
}
