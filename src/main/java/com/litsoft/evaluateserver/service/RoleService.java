package com.litsoft.evaluateserver.service;

import com.litsoft.evaluateserver.entity.Permission;
import com.litsoft.evaluateserver.entity.Role;
import com.litsoft.evaluateserver.entity.User;
import com.litsoft.evaluateserver.entity.sysVo.MenuTree;
import com.litsoft.evaluateserver.entity.sysVo.RoleVo;
import com.litsoft.evaluateserver.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public List<Role> findAll() {
        return roleRepository.findAll();
    }

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
    public void delete(Integer id) {
        roleRepository.delete(id);
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

    public RoleVo findById(Integer id) {
        Role role = roleRepository.findOne(id);
        RoleVo roleVo = new RoleVo();
        if(!ObjectUtils.isEmpty(role)) {

            roleVo.setId(role.getId());
            roleVo.setRole(role.getRole());
            roleVo.setDescription(role.getDescription());
            roleVo.setPerIds(getPerIds(role.getPermissions()));
        }
        return roleVo;
    }

    private List<Integer> getPerIds(List<Permission> permissions) {
        List<Integer> perIds = new ArrayList();
         permissions.forEach(permission -> {
            perIds.add(permission.getId());
        });
        return perIds;
    }

    @Transactional
    public Role getRoleFromRoleVo(RoleVo roleVo) {

        Role role = new Role();
        if(roleVo.getId()!=null) {
            /*role.setId(roleVo.getId())*/
            role = roleRepository.findOne(roleVo.getId());
            roleRepository.deleteRolePermission(role.getId());

        }
        role.setDescription(roleVo.getDescription());
        role.setRole(roleVo.getRole());
        List<Permission> permissionList = new ArrayList<>();
        roleVo.getPerIds().stream().forEach(perId -> {
            Permission permission = new Permission();
            permission.setId(perId);
            permissionList.add(permission);
        });
        role.setPermissions(permissionList);
        return role;
    }

    public void deleteArrayIds(List<Long> roleIds) {
        roleIds.forEach(id -> roleRepository.delete(id.intValue()));
    }

    public List<MenuTree> getViewMenu(List<Permission> permissionList) {

        List<MenuTree> menuTrees = new ArrayList<>();

        permissionList.forEach(permission -> {
            MenuTree menuTree = new MenuTree();
            menuTree.setId(permission.getId());
            menuTree.setName(permission.getName());
            menuTree.setUrl(permission.getUrl());
            menuTree.setpId(null);
            if(!ObjectUtils.isEmpty(permission.getPar())) {
                menuTree.setpId(permission.getPar().getId());
            }
            menuTrees.add(menuTree);
        });
        return menuTrees;
    }

    public Role findByRole(String name) {
        return roleRepository.findByRole(name);
    }
}
