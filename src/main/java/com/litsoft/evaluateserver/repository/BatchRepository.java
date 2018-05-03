package com.litsoft.evaluateserver.repository;

import com.litsoft.evaluateserver.entity.Batch;
import com.litsoft.evaluateserver.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BatchRepository extends JpaRepository<Batch, Integer>, JpaSpecificationExecutor<Batch> {

    Page<Batch> findAll(Specification<Batch> spec, Pageable pageable);

    @Modifying
    @Query(value = "delete from batch where id in (:ids)", nativeQuery = true)
    void deleteByIds(List<Integer> ids);

    @Query(value = "select  * from batch order by batch_number desc", nativeQuery = true)
    List<Batch> findAllBathch();

    public Batch findByBatchNumber(String batchNumber);
}
