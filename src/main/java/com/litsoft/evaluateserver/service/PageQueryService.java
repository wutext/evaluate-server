package com.litsoft.evaluateserver.service;

import com.litsoft.evaluateserver.entity.Batch;
import com.litsoft.evaluateserver.entity.Permission;
import com.litsoft.evaluateserver.entity.Role;
import com.litsoft.evaluateserver.util.QueryParam;
import org.springframework.data.domain.Page;

public interface PageQueryService {

    Page findUserNoCriteria(QueryParam param);

    Page findUserPageSearch(QueryParam param);

    Page findRoleNoCriteria(QueryParam param);

    Page findRolePageSearch(QueryParam param);

    Page findPermissionNoCriteria(QueryParam param);

    Page<Batch> findBatchPageSearch(QueryParam param);
}
