package com.litsoft.evaluateserver.service;

import com.litsoft.evaluateserver.entity.Batch;
import com.litsoft.evaluateserver.entity.Permission;
import com.litsoft.evaluateserver.entity.Role;
import com.litsoft.evaluateserver.entity.User;
import com.litsoft.evaluateserver.entity.UserScore;
import com.litsoft.evaluateserver.entity.sysVo.ScoreView;
import com.litsoft.evaluateserver.repository.BatchRepository;
import com.litsoft.evaluateserver.repository.PermissionRepository;
import com.litsoft.evaluateserver.repository.RoleRepository;
import com.litsoft.evaluateserver.repository.UserRepository;
import com.litsoft.evaluateserver.repository.UserScoreRepository;
import com.litsoft.evaluateserver.util.QueryParam;
import com.mysql.jdbc.StringUtils;
import com.sun.rmi.rmid.ExecPermission;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class PageQueryServiceImpl implements PageQueryService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private UserScoreRepository userScoreRepository;

    @Autowired
    private BatchRepository batchRepository;



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

                boolean depEx = StringUtils.isNullOrEmpty(param.getDepartUtil());
                boolean nameEx = StringUtils.isNullOrEmpty(param.getUsername());
                if(!depEx) {
                    Path<String> departUtil = root.get("departUtil").get("id");
                    Predicate p1 = cb.equal(departUtil, param.getUtilIds());
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
                    Path<String> departUtil = root.get("departUtil").get("id");
                    Path<String> username = root.get("username");
                    Predicate p1 = cb.equal(departUtil, param.getDepartUtil());
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

    @Override
    public Page<Batch> findBatchPageSearch(QueryParam param) {
        Pageable pageable =  new PageRequest(param.getPage()-1,param.getLimit(), Sort.Direction.DESC, "batchNumber");
        Specification<Batch> spec = new Specification<Batch>() {

            @Override
            public Predicate toPredicate(Root<Batch> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                if(!StringUtils.isNullOrEmpty(param.getBatchNumber())) {
                    Path<String>  batchNumber= root.get("batchNumber");
                    Predicate p1 = cb.like(batchNumber, "%"+param.getBatchNumber()+"%");
                    Predicate p = cb.and(p1);
                    return p;
                }
                return null;
            }
        };
        return batchRepository.findAll(spec, pageable);
    }

    @Override
    public Page<User> findUserBySoemSelect(QueryParam param) {

        Pageable pageable =  new PageRequest(param.getPage()-1,param.getLimit(), Sort.Direction.ASC, "id");
        Specification<User> spec = new Specification<User>() {
            @Override
            public Predicate toPredicate(Root<User> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder cb) {

                List<Predicate> predicateList = new ArrayList<Predicate>();

                boolean depEx = StringUtils.isNullOrEmpty(param.getDepartUtil());
                boolean nameEx = StringUtils.isNullOrEmpty(param.getUsername());
                boolean departUtil = StringUtils.isNullOrEmpty(param.getDepartUtil());
                //根据普通属性选择查询
                Path<String> username = root.get("username");
                //专门用来in查询的
                Expression<Integer> exp = root.get("departUtil").get("id");

                if(!nameEx) {
                    predicateList.add(cb.like(username, "%"+param.getUsername()+"%"));
                }
                if(!CollectionUtils.isEmpty(param.getUtilIds())) {
                    predicateList.add(cb.and(exp.in(param.getUtilIds())));
                }
                Predicate[] p = new Predicate[predicateList.size()];

                return cb.and(predicateList.toArray(p));
            }
        };
        return userRepository.findAll(spec, pageable);
    }


}
