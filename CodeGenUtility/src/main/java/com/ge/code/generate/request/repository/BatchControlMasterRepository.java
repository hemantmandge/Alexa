package com.ge.code.generate.request.repository;

import org.springframework.data.repository.CrudRepository;

import com.ge.code.generate.request.repository.entity.BatchControlMaster;
import com.ge.code.generate.request.repository.entity.BatchControlMasterPrimaryKey;

public interface BatchControlMasterRepository extends CrudRepository<BatchControlMaster, BatchControlMasterPrimaryKey>{
}
