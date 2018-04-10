package com.litsoft.evaluateserver.entity.sysVo;

public class UserVo {

    private Integer id;
    private String username;
    private String password;
    private String state;
    private String phone;
    private String email;
    private String[] roleId;

    public UserVo() {
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

    public UserVo(Integer id, String username, String state, String phone, String email) {
        this.id = id;
        this.username = username;
        this.state = state;
        this.phone = phone;
        this.email = email;
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

    public void setRoleId(String[] roleId) {
        this.roleId = roleId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
