package com.litsoft.evaluateserver.service;

import com.litsoft.evaluateserver.entity.Permission;
import com.litsoft.evaluateserver.entity.Role;
import com.litsoft.evaluateserver.entity.User;
import com.litsoft.evaluateserver.repository.PermissionRepository;
import com.litsoft.evaluateserver.repository.RoleRepository;
import com.litsoft.evaluateserver.repository.UserRepository;
import com.litsoft.evaluateserver.util.QueryParam;
import com.mysql.jdbc.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

@Service
public class PageQueryServiceImpl implements PageQueryService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Override
    public Page<User> findUserNoCriteria(QueryParam param) {
        Pageable pageable = new PageRequest(param.getPage()-1,param.getLimit(), Sort.Direction.ASC, "id");
        return userRepository.findAll(pageable);
    }

    @Override
    public Page<User> findUserPageSearch(QueryParam param) {
        Pageable pageable =  new PageRequest(param.getPage()-1,param.getLimit(), Sort.Direction.ASC, "id");
        Specification<User> spec = new Specification<User>() {

            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

                boolean depEx = StringUtils.isNullOrEmpty(param.getDepartment());
                boolean nameEx = StringUtils.isNullOrEmpty(param.getUsername());
                if(!depEx) {
                    Path<String> department = root.get("department");
                    Predicate p1 = cb.like(department, "%"+param.getDepartment()+"%");
                    Predicate p = cb.and(p1);
                    return p;
                }

                if(!nameEx) {
                    Path<String> username = root.get("username");
                    Predicate p2 = cb.like(username, "%"+param.getUsername()+"%");
                    Predicate p = cb.and(p2);
                    return p;
                }

                if(!depEx && !nameEx) {
                    Path<String> department = root.get("department");
                    Path<String> username = root.get("username");
                    Predicate p1 = cb.like(department, "%"+param.getDepartment()+"%");
                    Predicate p2 = cb.like(username, "%"+param.getUsername()+"%");
                    Predicate p = cb.and(p1,p2);
                    return p;
                }
                return null;
            }
        };
        return userRepository.findAll(spec, pageable);
    }

    @Override
    public Page findRoleNoCriteria(QueryParam param) {
        Pageable pageable = new PageRequest(param.getPage()-1,param.getLimit(), Sort.Direction.ASC, "id");
        return roleRepository.findAll(pageable);
    }

    @Override
    public Page<Role> findRolePageSearch(QueryParam param) {
        Pageable pageable =  new PageRequest(param.getPage()-1,param.getLimit(), Sort.Direction.ASC, "id");
        Specification<Role> spec = new Specification<Role>() {

            @Override
            public Predicate toPredicate(Root<Role> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                if(!StringUtils.isNullOrEmpty(param.getRoleSearch())) {
                    Path<String> role = root.get("role");
                    Predicate p1 = cb.like(role, "%"+param.getRoleSearch()+"%");
                    Predicate p = cb.and(p1);
                    return p;
                }
                return null;
            }
        };
        return roleRepository.findAll(spec, pageable);
    }

    @Override
    public Page<Permission> findPermissionNoCriteria(QueryParam param) {
        Pageable pageable = new PageRequest(param.getPage()-1,param.getLimit(), Sort.Direction.ASC, "id");
        return permissionRepository.findAll(pageable);
    }
}
