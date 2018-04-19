package com.litsoft.evaluateserver.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Department {

    @Id
    @GeneratedValue
    private Integer id;
    @Column(length = 30, nullable = false)
    private String name;
    private Integer sort;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "department")
    private List<DepartUtil> departUtil = new ArrayList<>();

    public Department() {
    }

    public Department(String name) {
        this.name = name;
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

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public List<DepartUtil> getDepartUtil() {
        return departUtil;
    }

    public void setDepartUtil(List<DepartUtil> departUtil) {
        this.departUtil = departUtil;
    }

}
