package com.ge.code.generate.request.service;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.ge.code.generate.request.repository.entity.RequestHistory;
import com.ge.code.generate.request.value.object.CodeGenRequest;

public interface CodeGenRequestService {
	public Page<RequestHistory> getAllCodeGenRequests(String sourceType, String sourceSystem, String dbConnection, 
			String dbName, String loadType, Date fromDate, Date toDate, Pageable pageable);
	public void create(CodeGenRequest codeGenRequest);
}
