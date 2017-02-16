package com.ge.code.generate.request.repository;

import org.springframework.data.repository.CrudRepository;

import com.ge.code.generate.request.repository.entity.IngestSubJobControl;

public interface BatchControlMaster extends CrudRepository<IngestSubJobControl, String>{
}
