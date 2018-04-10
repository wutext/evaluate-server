package com.litsoft.evaluateserver.repository;

import com.litsoft.evaluateserver.entity.UserScore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserScoreRepository extends JpaRepository<UserScore, Integer> {
}
