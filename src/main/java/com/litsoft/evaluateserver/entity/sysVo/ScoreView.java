package com.litsoft.evaluateserver.entity.sysVo;

import java.math.BigDecimal;
import java.util.Date;

public class ScoreView {
    private Integer id;
    private String userName;
    private BigDecimal total;
    private String createTime;

    public ScoreView(){}

    public ScoreView(Integer id,String username,BigDecimal total,String createTime) {
        this.id=id;
        this.userName=username;
        this.total=total;
        this.createTime=createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String username) {
        this.userName = username;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }
}
