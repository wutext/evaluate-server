package com.litsoft.evaluateserver.repository;

import com.litsoft.evaluateserver.entity.UserScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserScoreRepository extends JpaRepository<UserScore, Integer> {

    @Query(value = "select * from user_score where user_name = :userName and DATE_FORMAT(create_time,'%Y-%m') = :createTime", nativeQuery = true)
    List<UserScore> findByUserNameAndCreateTime(@Param("userName") String userName, @Param("createTime") String createTime);
}
