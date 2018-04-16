package com.litsoft.evaluateserver.repository;

import com.litsoft.evaluateserver.entity.UserScore;
import com.litsoft.evaluateserver.entity.sysVo.ScoreView;
import com.litsoft.evaluateserver.util.QueryParam;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserScoreRepository extends JpaRepository<UserScore, Integer> {
    //Page<UserScore> findAll(Specification<UserScore> spec, Pageable pageable);

    @Query(value = "select id,user_name,avg(total) total,DATE_FORMAT(create_time,'%Y-%m') create_time from user_score GROUP BY create_time  ORDER BY create_time desc /*#pageable*/",
        countQuery = "SELECT count(p.name) FROM (select user_name name,avg(total),DATE_FORMAT(create_time,'%Y-%m') t from user_score GROUP BY create_time ) p",
        nativeQuery = true)
    Page<ScoreView> queryUserScoreByPage(Pageable pageable);

    @Query(value = "select * from user_score where user_name = :userName and DATE_FORMAT(create_time,'%Y-%m') = :createTime", nativeQuery = true)
    List<UserScore> findByUserNameAndCreateTime(@Param("userName")String userName, @Param("createTime")String createTime);
}
