package com.litsoft.evaluateserver.entity.sysVo;

public class MenuTree {

    private Integer id;
    private String name;
    private String url;
    private Integer pId;

    public MenuTree() {
    }

    public MenuTree(Integer id, String name, String url, Integer pId) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.pId = pId;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getpId() {
        return pId;
    }

    public void setpId(Integer pId) {
        this.pId = pId;
    }
}
