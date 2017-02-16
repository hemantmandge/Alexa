package com.ge.code.generate.request.service;

import java.util.List;

import com.ge.code.generate.request.repository.entity.BatchControlMaster;

public interface CodeGenRequestService {
	public List<BatchControlMaster> getAllCodeGenRequests();
	public void addCodeGenRequest();
}
