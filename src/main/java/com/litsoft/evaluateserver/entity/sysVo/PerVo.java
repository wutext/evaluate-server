package com.litsoft.evaluateserver.entity.sysVo;

public class PerVo {

    private String name;
    private String parId;
    private String permission;
    private String resourceType;
    private String sort;
    private String url;

    public PerVo() {
    }

    public PerVo(String name, String id, String permission, String resourceType, String sort, String url) {
        this.name = name;
        this.parId = id;
        this.permission = permission;
        this.resourceType = resourceType;
        this.sort = sort;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParId() {
        return parId;
    }

    public void setParId(String parId) {
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

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
