package com.litsoft.evaluateserver.service;

import com.litsoft.evaluateserver.entity.UserScore;
import com.litsoft.evaluateserver.entity.sysVo.ScoreView;
import com.litsoft.evaluateserver.repository.UserScoreRepository;
import com.litsoft.evaluateserver.util.QueryParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserScoreService {
    @Autowired
    private UserScoreRepository userScoreRepository;

    @Autowired
    private EntityManager manager;

    @Transactional
    public boolean save(UserScore userScore) {
        boolean flag = false;
        UserScore us = userScoreRepository.save(userScore);
        if (us != null) {
            flag = true;
        }
        return flag;
    }

    //获取用户得分数据
    public List<ScoreView> getUserScore(QueryParam param) {
        String sql = "select user_name,AVG(progress_completion_score)," +
            "AVG(workload_score),AVG(work_quality_score),AVG(work_efficiency_score)," +
            "AVG(working_attitude_score),AVG(attendance_score),AVG(progress_deviation_score)," +
            "AVG(work_cooperate_score),avg(total),DATE_FORMAT(create_time,'%Y-%m') t,dept_name from user_score where 1=1 ";
        String condition = "GROUP BY user_name,create_time ORDER BY t desc";
        String username = param.getUsername();
        String time = param.getTime();
        String dept = param.getDepartment();
        if (StringUtils.isNotEmpty(username)) {
            sql = sql + " and user_name like '%" + username + "%'";
        }
        if (StringUtils.isNotEmpty(dept)) {
            sql = sql + " and dept_name like '%" + dept + "%'";
        }
        if (StringUtils.isNotEmpty(time)) {
            sql = sql + "and DATE_FORMAT(create_time,'%Y-%m') like '%" + time.substring(0, 7) + "%'";
        }
        sql = sql + condition;
        Integer page = param.getPage() - 1;
        Integer limit = param.getLimit();
        sql = sql + " limit " + page + "," + limit;
        Query nativeQuery = manager.createNativeQuery(sql);
        List list = nativeQuery.getResultList();
        List<ScoreView> viewList = new ArrayList<ScoreView>();
        for (int i = 0; i < list.size(); i++) {
            ScoreView view = new ScoreView();
            Object[] objectArray = (Object[]) list.get(i);
            view.setUserName((String) objectArray[0]);
            view.setProgressCompletionScore(formateAvgScore((BigDecimal) objectArray[1]));
            view.setWorkloadScore(formateAvgScore(((BigDecimal) objectArray[2])));
            view.setWorkQualityScore(formateAvgScore(((BigDecimal) objectArray[3])));
            view.setWorkEfficiencyScore(formateAvgScore(((BigDecimal) objectArray[4])));
            view.setWorkingAttitudeScore(formateAvgScore(((BigDecimal) objectArray[5])));
            view.setAttendanceScore(formateAvgScore(((BigDecimal) objectArray[6])));
            view.setProgressDeviationScore(formateAvgScore(((BigDecimal) objectArray[7])));
            view.setWorkCooperateScore(formateAvgScore(((BigDecimal) objectArray[8])));
            view.setTotal(formateAvgScore(((BigDecimal) objectArray[9])));
            view.setCreateTime((String) objectArray[10]);
            view.setDeptName((String) objectArray[11]);
            viewList.add(view);
        }
        return viewList;
    }

    //格式化分数
    private BigDecimal formateAvgScore(BigDecimal score) {
        return score.setScale(2, BigDecimal.ROUND_HALF_UP);
    }

    //获取用户得分数据详情
    public List<UserScore> getUserScoreDetail(String userName, String time) {
        if (StringUtils.isNotEmpty(time)) {
            time = time.substring(0, 7);
        }
        List<UserScore> list = userScoreRepository.findByUserNameAndCreateTime(userName, time);
        return list;
    }

    public List<UserScore> findList() {
        CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
        CriteriaQuery<UserScore> query = criteriaBuilder.createQuery(UserScore.class);
        Root<UserScore> root = query.from(UserScore.class);
        CriteriaQuery<UserScore> q = query.multiselect(root.get("userName"), root.get("createTime"),
            criteriaBuilder.avg(root.get("total")));
        q.groupBy(root.get("userName"), root.get("createTime"));
        q.orderBy(criteriaBuilder.desc(root.get("createTime")));
        List<UserScore> list = manager.createQuery(q).getResultList();
        return list;
    }
}
