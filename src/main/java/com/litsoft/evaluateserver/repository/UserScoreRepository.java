package com.litsoft.evaluateserver.repository;

import com.litsoft.evaluateserver.entity.UserScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserScoreRepository extends JpaRepository<UserScore, Integer> {

    @Query(value = "select * from user_score where user_name = :userName and DATE_FORMAT(create_time,'%Y-%m') = :createTime and batch = :batch", nativeQuery = true)
    List<UserScore> findByUserNameAndCreateTimeAndBatch(@Param("userName") String userName, @Param("createTime") String createTime, @Param("batch") String batch);

    UserScore findByUserIdAndTypeAndSignName(Integer userId, Integer type, String signName);

    @Query(value = "select u.type from user_score u where u.user_id =?1 and u.batch =?2", nativeQuery = true)
    List<Integer> findScoreStatusByUserId(Integer id, String batchNumber);
}
