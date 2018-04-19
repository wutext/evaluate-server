package com.litsoft.evaluateserver.entity.sysVo;

import java.math.BigDecimal;
import java.util.Date;

public class ScoreView {
    private Integer id;
    private String userName;
    private BigDecimal total;
    private BigDecimal progressCompletionScore;  //项目进度得分
    private BigDecimal workloadScore;  //工作量得分
    private BigDecimal workQualityScore;  //工作质量得分;
    private BigDecimal workEfficiencyScore;  //工作效率得分
    private BigDecimal workingAttitudeScore;  //工作态度得分
    private BigDecimal attendanceScore;  //出勤率得分
    private BigDecimal progressDeviationScore;  //进度偏差得分
    private BigDecimal workCooperateScore;  //工作配合情况得分
    private String createTime;
    private String deptName;
    private String batch;

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

    public BigDecimal getProgressCompletionScore() {
        return progressCompletionScore;
    }

    public void setProgressCompletionScore(BigDecimal progressCompletionScore) {
        this.progressCompletionScore = progressCompletionScore;
    }

    public BigDecimal getWorkloadScore() {
        return workloadScore;
    }

    public void setWorkloadScore(BigDecimal workloadScore) {
        this.workloadScore = workloadScore;
    }

    public BigDecimal getWorkQualityScore() {
        return workQualityScore;
    }

    public void setWorkQualityScore(BigDecimal workQualityScore) {
        this.workQualityScore = workQualityScore;
    }

    public BigDecimal getWorkEfficiencyScore() {
        return workEfficiencyScore;
    }

    public void setWorkEfficiencyScore(BigDecimal workEfficiencyScore) {
        this.workEfficiencyScore = workEfficiencyScore;
    }

    public BigDecimal getWorkingAttitudeScore() {
        return workingAttitudeScore;
    }

    public void setWorkingAttitudeScore(BigDecimal workingAttitudeScore) {
        this.workingAttitudeScore = workingAttitudeScore;
    }

    public BigDecimal getAttendanceScore() {
        return attendanceScore;
    }

    public void setAttendanceScore(BigDecimal attendanceScore) {
        this.attendanceScore = attendanceScore;
    }

    public BigDecimal getProgressDeviationScore() {
        return progressDeviationScore;
    }

    public void setProgressDeviationScore(BigDecimal progressDeviationScore) {
        this.progressDeviationScore = progressDeviationScore;
    }

    public BigDecimal getWorkCooperateScore() {
        return workCooperateScore;
    }

    public void setWorkCooperateScore(BigDecimal workCooperateScore) {
        this.workCooperateScore = workCooperateScore;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }
}
