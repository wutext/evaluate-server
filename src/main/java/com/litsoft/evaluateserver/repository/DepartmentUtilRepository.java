package com.litsoft.evaluateserver.repository;


import com.litsoft.evaluateserver.entity.DepartUtil;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface DepartmentUtilRepository extends JpaRepository<DepartUtil,Integer> {


    @Modifying
    @Query(value = "delete from depart_util where id=?1", nativeQuery = true)
    void deleteById(Integer id);

    public DepartUtil findByName(String name);
}
