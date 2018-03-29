package com.litsoft.evaluateserver.service;


import com.litsoft.evaluateserver.model.SysUser;
import com.litsoft.evaluateserver.repository.SysUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class CustomUserService {

    @Autowired
    private SysUserRepository sysUserRepository;

    public SysUser loadUserByUsername(String name, String password) throws Exception {
        SysUser sysUser = sysUserRepository.findByUsername(name);
        if(ObjectUtils.isEmpty(sysUser)) {
           throw new Exception("用户名不存在");
        }else if (password.equalsIgnoreCase(sysUser.getPassword())) {
            return sysUser;
        }
        return new SysUser();

    }
}
