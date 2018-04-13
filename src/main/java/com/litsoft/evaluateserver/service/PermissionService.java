package com.litsoft.evaluateserver.service;

import com.litsoft.evaluateserver.entity.Permission;
import com.litsoft.evaluateserver.entity.User;
import com.litsoft.evaluateserver.entity.sysVo.PerVo;
import com.litsoft.evaluateserver.repository.PermissionRepository;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.rest.webmvc.ControllerUtils;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;


@Service
public class PermissionService {

    @Autowired
    private EntityManager entityManager;

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

        User user = getShiroUser();
        List<Permission> perList = new ArrayList();
        List<Permission> permissionList = permissionRepository.findMenuByUserId(user.getId());
        Permission.sortList(perList, permissionList, 1);
        return permissionList;
    }

    @Transactional
    public Permission savePermission(PerVo perVo) {

        Permission permission = getPermission(perVo);
        Permission backPermission = permissionRepository.save(permission);
        if(perVo.getId()==null && !ObjectUtils.isEmpty(backPermission)) {
            String parent_Ids = permission.getPar().getId()+","+backPermission.getId();
            permissionRepository.updatePermissionById(parent_Ids, backPermission.getId());
        }
        return permission;
    }

    private Permission getPermission(PerVo perVo) {

        Permission permission = new Permission();
        if(perVo.getId()!=null) {
            permission = permissionRepository.findOne(perVo.getId());
        }
        permission.setName(perVo.getName());
        permission.setParId(perVo.getParId());
        permission.setResourceType(perVo.getResourceType());
        permission.setUrl(perVo.getUrl());
        permission.setPermission(perVo.getPermission());
        permission.setSort(perVo.getSort());
        permission.setStatus("1");
        return permission;
    }

    public User getShiroUser() {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        return user;
    }

    @Transactional
    public boolean deletePermission(Integer id) {
        List<Integer> childId = permissionRepository.findChildId(id);
        boolean delTrue = delete(id);
        if(delTrue) {
           if(!CollectionUtils.isEmpty(childId)) {
               childId.forEach(cId -> delete(cId));
           }
           return true;
        }
        return false;
    }
}
