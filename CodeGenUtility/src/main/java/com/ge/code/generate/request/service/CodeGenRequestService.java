package com.ge.code.generate.request.service;

import java.util.List;

import com.ge.code.generate.request.repository.entity.BatchControlMaster;
import com.ge.code.generate.request.value.object.CodeGenRequest;

public interface CodeGenRequestService {
	public List<BatchControlMaster> getAllCodeGenRequests();
	public void create(CodeGenRequest codeGenRequest);
}
