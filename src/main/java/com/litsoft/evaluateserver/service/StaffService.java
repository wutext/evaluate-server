package com.litsoft.evaluateserver.service;

import com.litsoft.evaluateserver.entity.Staff;
import com.litsoft.evaluateserver.entity.sysVo.UserVo;
import com.litsoft.evaluateserver.repository.StaffRepository;
import com.litsoft.evaluateserver.util.MdUtil;
import com.mysql.jdbc.StringUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class StaffService {
    @Autowired
    private StaffRepository staffRepository;

    @Autowired
    private DepartmentService departmentService;

    public List<Staff> findAll() {
        return staffRepository.findAll();
    }

    public Staff findByUsername(String username) {
        return staffRepository.findByUsername(username);
    }

    //用户添加
    @Transactional
    public boolean insert(Staff user) {
        boolean flag = false;
        Staff u = staffRepository.save(user);
        if (u != null) {
            flag = true;
        }
        return flag;
    }

    //用户修改
    @Transactional
    public boolean update(Staff user) {
        boolean flag = false;
        Staff u = staffRepository.saveAndFlush(user);
        if (u != null) {
            flag = true;
        }
        return flag;
    }

    //用户删除
    @Transactional
    public boolean delete(Integer id) {
        boolean flag = false;
        staffRepository.delete(id);
        if (flag) {
            flag = true;
        }
        return flag;
    }


    @Transactional
    public Staff saveUser(UserVo userVo) throws Exception {

        Staff user = new Staff();
        if (userVo.getId() == null) {
            user = copyStaff(userVo);
            user.setDepartUtil(departmentService.findDepartUtilById(userVo.getUtilId()));
        } else {
            Staff preUser = staffRepository.findOne(userVo.getId());
            preUser.setStaffName(userVo.getStaffName());
            preUser.setCompany(userVo.getCompany());
            preUser.setProject(userVo.getProject());
            preUser.setPhone(userVo.getPhone());
            preUser.setEmail(userVo.getEmail());
            preUser.setStaffNo(userVo.getStaffNo());
            preUser.setPosition(userVo.getPosition());
            preUser.setState(1);
            preUser.setDepartUtil(departmentService.findDepartUtilById(userVo.getUtilId()));
            user = preUser;
        }
        return staffRepository.save(user);
    }

    public Staff copyStaff(UserVo userVo) {
        Staff staff = new Staff();
        staff.setId(userVo.getId());
        staff.setCompany(userVo.getCompany());
        staff.setStaffName(userVo.getStaffName());
        staff.setEmail(userVo.getEmail());
        staff.setPhone(userVo.getPhone());
        staff.setState(1);
        staff.setProject(userVo.getProject());
        staff.setStaffNo(userVo.getStaffNo());
        staff.setPosition(userVo.getPosition());
        return staff;
    }


    public Staff findById(Integer id) {
        return staffRepository.findOne(id);
    }

    public void deleteSingleUser(Integer id) {
        staffRepository.delete(id);
    }

    public void deleteIds(String ids) {

        List<Integer> idList = getIdList(ids);
        idList.forEach(id -> staffRepository.delete(id.intValue()));
    }

    private List<Integer> getIdList(String ids) {
        String[] strId = ids.substring(1, ids.length() - 1).split(",");
        List<Integer> intId = new ArrayList<>();
        for (String id : strId) {
            if (!StringUtils.isNullOrEmpty(id)) {
                intId.add(Integer.valueOf(id));
            }

        }
        return intId;
    }
}
