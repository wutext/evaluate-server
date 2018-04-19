package com.litsoft.evaluateserver.util;

import org.springframework.util.ObjectUtils;

import java.util.Map;

public class QueryParam {

    private Integer page;
    private Integer limit;

    //user查询条件
    private String username = "";
    private String department = "";
    private String time = "";
    private String batch = "";
    //角色查询条件
    private String roleSearch  ="";


    public QueryParam() {

    }
    public QueryParam(Integer page, Integer limit) {
        this.page = page;
        this.limit = limit;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getRoleSearch() {
        return roleSearch;
    }

    public void setRoleSearch(String roleSearch) {
        this.roleSearch = roleSearch;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public QueryParam(Map<String, Object> params) {
        this.page = Integer.valueOf(params.get("page").toString());
        this.limit = Integer.valueOf(params.get("limit").toString());
        if(!ObjectUtils.isEmpty(params.get("roleSearch"))) {
            this.roleSearch = params.get("roleSearch").toString();
        }
        if(!ObjectUtils.isEmpty(params.get("username"))) {
            this.username = params.get("username").toString();
        }
        if(!ObjectUtils.isEmpty(params.get("department"))) {
            this.department = params.get("department").toString();
        }
        if(!ObjectUtils.isEmpty(params.get("time"))) {
            this.time = params.get("time").toString();
        }

        if(!ObjectUtils.isEmpty(params.get("batch"))) {
            this.batch = params.get("batch").toString();
        }
    }
}
