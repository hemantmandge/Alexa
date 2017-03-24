package com.ge.code.generate.request.service;

import java.util.Date;
import java.util.List;

import com.ge.code.generate.request.repository.entity.RequestHistory;
import com.ge.code.generate.request.value.object.CodeGenRequest;

public interface CodeGenRequestService {
	public List<RequestHistory> getAllCodeGenRequests(String sourceType, String sourceSystem, String dbConnection, String dbName, String loadType, Date fromDate, Date toDate);
	public void create(CodeGenRequest codeGenRequest);
}
