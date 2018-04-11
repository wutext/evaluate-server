package com.litsoft.evaluateserver.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "permission")
public class Permission implements Serializable {
    @Id
    @GeneratedValue
    private Integer id;//主键.
    private String name;//名称.
    @Column(columnDefinition="enum('menu','button')")
    private String resourceType;//资源类型，[menu|button]
    private String url;//资源路径.
    private String permission; //权限字符串,menu例子：role:*，button例子：role:create,role:update,role:delete,role:view
    private String parentIds; //父编号列表
    private Integer sort;
    private String status;
    @ManyToMany(fetch= FetchType.EAGER)
    @JoinTable(name="RolePermission",joinColumns={@JoinColumn(name="permissionId")},inverseJoinColumns={@JoinColumn(name="roleId")})
    private List<Role> roles;

    @ManyToOne
    private Permission par;

    @OneToMany
    @JoinColumn(name = "par_id")
    private List<Permission> permissionList = new ArrayList();


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public Permission getPar() {
        return par;
    }

    public void setPar(Permission par) {
        this.par = par;
    }

    public String getParentIds() {
        return parentIds;
    }

    public void setParentIds(String parentIds) {
        this.parentIds = parentIds;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<Permission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<Permission> permissionList) {
        this.permissionList = permissionList;
    }


    @Transient
    public static void sortList(List<Permission> list, List<Permission> sourcelist, Integer parentId){
        for (int i=0; i<sourcelist.size(); i++){
            Permission p = sourcelist.get(i);
            if (p.getPar()!=null && p.getPar().getId()!=null
                && p.getPar().getId().equals(parentId)){
                list.add(p);
                // 判断是否还有子节点, 有则继续获取子节点
                for (int j=0; j<sourcelist.size(); j++){
                    Permission child = sourcelist.get(j);
                    if (child.getPar()!=null && child.getPar().getId()!=null
                        && child.getPar().getId().equals(p.getId())){
                        sortList(list, sourcelist, p.getId());
                        break;
                    }
                }
            }
        }
    }
}
