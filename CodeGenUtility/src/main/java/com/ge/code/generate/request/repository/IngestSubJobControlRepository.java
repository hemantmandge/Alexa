package com.ge.code.generate.request.repository;

import org.springframework.data.repository.CrudRepository;

import com.ge.code.generate.request.repository.entity.IngestSubJobControl;
import com.ge.code.generate.request.repository.entity.IngestSubJobControlPrimaryKey;

public interface IngestSubJobControlRepository extends CrudRepository<IngestSubJobControl, IngestSubJobControlPrimaryKey>{
}
