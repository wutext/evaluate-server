package com.litsoft.evaluateserver.service;

import com.litsoft.evaluateserver.entity.Role;
import com.litsoft.evaluateserver.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;


@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;



    //角色添加
    @Transactional
    public boolean insert(Role role) {
        boolean flag = false;
        Role r = roleRepository.save(role);
        if (r != null) {
            flag = true;
        }
        return flag;
    }

    //角色修改
    @Transactional
    public boolean update(Role role) {
        boolean flag = false;
        Role r = roleRepository.saveAndFlush(role);
        if (r != null) {
            flag = true;
        }
        return flag;
    }

    //角色删除
    @Transactional
    public boolean delete(Integer id) {
        boolean flag = false;
        roleRepository.delete(id);
        boolean userRoleFalg = false;
        boolean rolePermissionFlag = false;
        userRoleFalg = this.deleteUserRoleByRoleId(id);
        rolePermissionFlag = this.deleteRolePermissionByRoleId(id);
        if (userRoleFalg && rolePermissionFlag) {
            flag = true;
        }
        return flag;
    }

    //角色删除时，用户角色关联的数据删除
    @Transactional
    public boolean deleteUserRoleByRoleId(Integer userId) {
        boolean flag = false;
        int num = roleRepository.deleteUserRoleByRoleId(userId);
        if (num > 0) {
            flag = true;
        }
        return flag;
    }

    //角色删除时，角色权限关联的数据删除
    @Transactional
    public boolean deleteRolePermissionByRoleId(Integer userId) {
        boolean flag = false;
        int num = roleRepository.deleteRolePermissionByRoleId(userId);
        if (num > 0) {
            flag = true;
        }
        return flag;
    }
}
