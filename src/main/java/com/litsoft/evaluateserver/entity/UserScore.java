package com.litsoft.evaluateserver.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "user_score")
public class UserScore implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;
    @Column(name = "user_id")
    private Integer userId;    //用户ID
    @Column(name = "user_name")
    private String userName;   //用户姓名
    @Column(name = "dept_name")
    private String deptName;   //职位角色
    @Column(name = "company_name")
    private String companyName; //所属公司
    @Column(name = "project_name")
    private String projectName; //所属项目
    @Column(name = "progress_completion_score")
    private Integer progressCompletionScore; //项目进度得分
    @Column(name = "workload_score")
    private Integer workloadScore;  //工作量得分
    @Column(name = "work_quality_score")
    private Integer workQualityScore;  //工作质量得分
    @Column(name = "work_efficiency_score")
    private Integer workEfficiencyScore;  //工作效率得分
    @Column(name = "working_attitude_score")
    private Integer workingAttitudeScore;  //工作态度得分
    @Column(name = "attendance_score")
    private Integer attendanceScore;  //出勤率得分
    @Column(name = "progress_deviation_score")
    private Integer progressDeviationScore;  //进度偏差得分
    @Column(name = "work_cooperate_score")
    private Integer workCooperateScore;  //工作配合情况得分
    @Column(name = "type")
    private Integer type;  //评分角色类型;1:客户2:经理3:人事
    @Column(name = "create_time")
    private Date createTime;  //创建时间
    @Column(name = "sign_name")
    private String signName; //评分人签字
    @Column(name = "total")
    private Integer total;  //合计得分
    @Column(name = "batch")
    private String batch;   //批次

    private Double price;

    public UserScore() {
    }

    public UserScore(String userName, Date createTime, Double price) {
        this.userName = userName;
        this.createTime = createTime;
        this.price = price;
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getProgressCompletionScore() {
        return progressCompletionScore;
    }

    public void setProgressCompletionScore(Integer progressCompletionScore) {
        this.progressCompletionScore = progressCompletionScore;
    }

    public Integer getWorkloadScore() {
        return workloadScore;
    }

    public void setWorkloadScore(Integer workloadScore) {
        this.workloadScore = workloadScore;
    }

    public Integer getWorkQualityScore() {
        return workQualityScore;
    }

    public void setWorkQualityScore(Integer workQualityScore) {
        this.workQualityScore = workQualityScore;
    }

    public Integer getWorkEfficiencyScore() {
        return workEfficiencyScore;
    }

    public void setWorkEfficiencyScore(Integer workEfficiencyScore) {
        this.workEfficiencyScore = workEfficiencyScore;
    }


    public Integer getAttendanceScore() {
        return attendanceScore;
    }

    public void setAttendanceScore(Integer attendanceScore) {
        this.attendanceScore = attendanceScore;
    }

    public Integer getProgressDeviationScore() {
        return progressDeviationScore;
    }

    public void setProgressDeviationScore(Integer progressDeviationScore) {
        this.progressDeviationScore = progressDeviationScore;
    }

    public Integer getWorkCooperateScore() {
        return workCooperateScore;
    }

    public void setWorkCooperateScore(Integer workCooperateScore) {
        this.workCooperateScore = workCooperateScore;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }


    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getWorkingAttitudeScore() {
        return workingAttitudeScore;
    }

    public void setWorkingAttitudeScore(Integer workingAttitudeScore) {
        this.workingAttitudeScore = workingAttitudeScore;
    }

    public String getSignName() {
        return signName;
    }

    public void setSignName(String signName) {
        this.signName = signName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }
}
