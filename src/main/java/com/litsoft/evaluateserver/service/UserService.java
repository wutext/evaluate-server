package com.litsoft.evaluateserver.service;

import com.litsoft.evaluateserver.entity.Role;
import com.litsoft.evaluateserver.entity.User;
import com.litsoft.evaluateserver.entity.sysVo.UserVo;
import com.litsoft.evaluateserver.repository.RoleRepository;
import com.litsoft.evaluateserver.repository.UserRepository;
import com.litsoft.evaluateserver.util.MdUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

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

    @Transactional
    public User saveUser(UserVo userVo) throws Exception {

        User user = MdUtil.md5Password(userVo.getUsername(), userVo.getPassword());
        user.setEmail(userVo.getEmail());
        user.setState(new Byte("1"));
        user.setPhone(userVo.getEmail());
        user.setRoleList(getRoleList(userVo.getRoleId()));
        User uBack = userRepository.save(user);
        return uBack;
    }


    private List<Role> getRoleList(String[] roleId) {

        List<Role> roles = new ArrayList<>();
        if(roleId.length >0) {
            for(int i=0;i<roleId.length;i++) {
                roles.add(new Role(Integer.valueOf(roleId[i])));
            }
        }
        return roles;
    }

    public User findById(Integer id) {
        return userRepository.findOne(id);
    }

    public void deleteSingleUser(Integer id) {
        userRepository.delete(id);
    }

    public void deleteIds(List<Long> ids) {
        ids.forEach(id -> userRepository.delete(id.intValue()));
    }
}
