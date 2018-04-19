package com.litsoft.evaluateserver.entity.vo;

public class DepartmentVo {

    private Integer departId;
    private String departName;
    private Integer utilId;
    private String utilName;
    private Integer sort;
    private String type;

    public DepartmentVo() {
    }

    public DepartmentVo(Integer departId, String departName, Integer utilId, String utilName, Integer sort) {
        this.departId = departId;
        this.departName = departName;
        this.utilId = utilId;
        this.utilName = utilName;
        this.sort = sort;
    }

    public Integer getDepartId() {
        return departId;
    }

    public void setDepartId(Integer departId) {
        this.departId = departId;
    }

    public String getDepartName() {
        return departName;
    }

    public void setDepartName(String departName) {
        this.departName = departName;
    }

    public Integer getUtilId() {
        return utilId;
    }

    public void setUtilId(Integer utilId) {
        this.utilId = utilId;
    }

    public String getUtilName() {
        return utilName;
    }

    public void setUtilName(String utilName) {
        this.utilName = utilName;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
