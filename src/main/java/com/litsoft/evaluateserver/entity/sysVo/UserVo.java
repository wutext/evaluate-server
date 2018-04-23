package com.litsoft.evaluateserver.entity.sysVo;

import java.util.List;

public class UserVo {

    private Integer id;
    private String username;
    private String password;
    private String state;
    private String phone;
    private String company;
    private String project;
    private String email;
    private String[] roleId;
    private List<Integer> raters;
    private Integer utilId;
    private String departmentUtil;

    public UserVo() {
    }

    public UserVo(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserVo(Integer id, String username, String state) {
        this.id = id;
        this.username = username;
        this.state = state;
    }

    public UserVo(String username, String state, String phone, String email, String[] roleId) {
        this.username = username;
        this.state = state;
        this.phone = phone;
        this.email = email;
        this.roleId = roleId;
    }

    public UserVo(Integer id, String username, String company, String project, String departmentUtil) {
        this.id = id;
        this.username = username;
        this.company = company;
        this.project = project;
        this.departmentUtil = departmentUtil;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String[] getRoleId() {
        return roleId;
    }

    public Integer getUtilId() {
        return utilId;
    }

    public void setUtilId(Integer utilId) {
        this.utilId = utilId;
    }

    public void setRoleId(String[] roleId) {
        this.roleId = roleId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public List<Integer> getRaters() {
        return raters;
    }

    public void setRaters(List<Integer> raters) {
        this.raters = raters;
    }

    public String getDepartmentUtil() {
        return departmentUtil;
    }

    public void setDepartmentUtil(String departmentUtil) {
        this.departmentUtil = departmentUtil;
    }
}
