package com.litsoft.evaluateserver.service;

import com.litsoft.evaluateserver.entity.Batch;
import com.litsoft.evaluateserver.entity.Permission;
import com.litsoft.evaluateserver.entity.Role;
import com.litsoft.evaluateserver.entity.sysVo.MenuTree;
import com.litsoft.evaluateserver.entity.sysVo.RoleVo;
import com.litsoft.evaluateserver.exception.NotFoundException;
import com.litsoft.evaluateserver.repository.BatchRepository;
import com.litsoft.evaluateserver.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class BatchService {

    @Autowired
    private BatchRepository batchRepository;

    public List<Batch> findBatchList() {

        return batchRepository.findAllBathch();
    }

    public Batch findOne(Integer id) {
        return batchRepository.findOne(id);
    }

    @Transactional
    public Batch save(Batch batch) {
        return batchRepository.save(batch);
    }

    @Transactional
    public boolean deleteBatch(Integer id) {

        try {
            batchRepository.delete(id);
            return true;
        }catch (Exception e) {
            new NotFoundException("删除失败");
            return false;
        }
    }

    @Transactional
    public boolean deleteBatchByIds(List<Integer> ids) {

        try {
            ids.forEach(id -> batchRepository.delete(id));
            return true;
        }catch (Exception e) {
            throw new NotFoundException("删除失败");
        }
    }
}
