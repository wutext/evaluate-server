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
        String sql = "select user_name,avg(total),DATE_FORMAT(create_time,'%Y-%m') t from user_score where 1=1 ";
        String condition = "GROUP BY user_name,create_time ORDER BY t desc";
        String username = param.getUsername();
        String time = param.getTime();
        if (StringUtils.isNotEmpty(username)) {
            sql = sql + " and user_name like '%"+username+"%'";
        }
        if (StringUtils.isNotEmpty(time)) {
            sql = sql + "and DATE_FORMAT(create_time,'%Y-%m') like '%"+time.substring(0,7)+"%'";
        }
        sql = sql + condition;
        Integer page = param.getPage()-1;
        Integer limit = param.getLimit();
        sql = sql + " limit "+page+","+limit;
        Query nativeQuery = manager.createNativeQuery(sql);
        List list = nativeQuery.getResultList();
        List<ScoreView> viewList = new ArrayList<ScoreView>();
        for(int i=0;i<list.size(); i++){
            ScoreView view = new ScoreView();
            Object[] objectArray = (Object[]) list.get(i);
            view.setUserName((String)objectArray[0]);
            view.setTotal(((BigDecimal) objectArray[1]).setScale(2,BigDecimal.ROUND_HALF_UP));
            view.setCreateTime((String)objectArray[2]);
            viewList.add(view);
        }
        return viewList;
    }

    //获取用户得分数据详情
    public List<UserScore> getUserScoreDetail(String userName,String time){
        if (StringUtils.isNotEmpty(time)) {
            time = time.substring(0,7);
        }
      List<UserScore> list = userScoreRepository.findByUserNameAndCreateTime(userName,time);
      return list;
    }
}
