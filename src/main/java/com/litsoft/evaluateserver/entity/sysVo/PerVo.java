package com.litsoft.evaluateserver.entity.sysVo;

public class PerVo {

    private Integer parId;
    private Integer id;
    private String name;
    private String permission;  //权限标识
    private String resourceType;
    private Integer sort;
    private String url;

    public PerVo() {
    }

    public PerVo(String name, Integer id, String permission, String resourceType, Integer sort, String url) {
        this.name = name;
        this.parId = id;
        this.permission = permission;
        this.resourceType = resourceType;
        this.sort = sort;
        this.url = url;
    }

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

    public Integer getParId() {
        return parId;
    }

    public void setParId(Integer parId) {
        this.parId = parId;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
