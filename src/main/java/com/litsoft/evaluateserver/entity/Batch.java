package com.litsoft.evaluateserver.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "batch")
public class Batch implements Serializable {

    @Id
    @GeneratedValue
    private Integer id; // 编号
    private String batchNumber; //批次号

    public Batch() {
    }

    public Batch(Integer id, String batchNumber) {
        this.id = id;
        this.batchNumber = batchNumber;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getBatchNumber() {
        return batchNumber;
    }

    public void setBatchNumber(String batchNumber) {
        this.batchNumber = batchNumber;
    }
}
