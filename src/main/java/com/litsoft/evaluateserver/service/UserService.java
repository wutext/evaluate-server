package com.litsoft.evaluateserver.service;

import com.litsoft.evaluateserver.entity.User;
import com.litsoft.evaluateserver.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> findAll() {
        return userRepository.findAll();
    }


    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    //用户添加
    @Transactional
    public boolean insert(User user) {
        boolean flag = false;
        User u = userRepository.save(user);
        if (u != null) {
            flag = true;
        }
        return flag;
    }

    //用户修改
    @Transactional
    public boolean update(User user) {
        boolean flag = false;
        User u = userRepository.saveAndFlush(user);
        if (u != null) {
            flag = true;
        }
        return flag;
    }

    //用户删除
    @Transactional
    public boolean delete(Integer id) {
        boolean flag = false;
        userRepository.delete(id);
        boolean userRoleFalg = false;
        userRoleFalg = this.deleteUserRoleByUserId(id);
        if (userRoleFalg) {
            flag = true;
        }
        return flag;
    }

    //用户删除时，和角色关联的数据删除
    @Transactional
    public boolean deleteUserRoleByUserId(Integer userId) {
        boolean flag = false;
        int num = userRepository.deleteUserRoleByUserId(userId);
        if (num > 0) {
            flag = true;
        }
        return flag;
    }
}
