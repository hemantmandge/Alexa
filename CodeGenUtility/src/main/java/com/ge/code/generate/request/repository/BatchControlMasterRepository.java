package com.ge.code.generate.request.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.ge.code.generate.request.repository.entity.BatchControlMaster;
import com.ge.code.generate.request.repository.entity.BatchControlMasterPrimaryKey;

public interface BatchControlMasterRepository extends CrudRepository<BatchControlMaster, BatchControlMasterPrimaryKey>{
	
	List<BatchControlMaster> findBySourceSystem(String sourceSystem);
	@Query("SELECT MAX(batchId) FROM BatchControlMaster")
    Integer getMaxBatchId();
}
