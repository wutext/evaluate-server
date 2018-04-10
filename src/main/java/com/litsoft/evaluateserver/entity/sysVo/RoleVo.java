package com.litsoft.evaluateserver.entity.sysVo;

import java.util.List;

public class RoleVo {

    private Integer id;
    private String role;
    private String description;
    private List<Integer> perIds;

    public RoleVo() {
    }

    public RoleVo(Integer id, String role, String description) {
        this.id = id;
        this.role = role;
        this.description = description;
    }


    public RoleVo(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPerIds(List<Integer> perIds) {
        this.perIds = perIds;
    }

    public List<Integer> getPerIds() {
        return perIds;
    }

}
