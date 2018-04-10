package com.litsoft.evaluateserver.service;

import com.litsoft.evaluateserver.entity.UserScore;
import com.litsoft.evaluateserver.repository.UserScoreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserScoreService {
    @Autowired
    private UserScoreRepository userScoreRepository;

    @Transactional
    public boolean save(UserScore userScore) {
        boolean flag = false;
        UserScore us = userScoreRepository.save(userScore);
        if (us != null) {
            flag = true;
        }
        return flag;
    }
}
