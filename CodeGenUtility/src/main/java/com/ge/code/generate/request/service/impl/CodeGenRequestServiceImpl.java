package com.ge.code.generate.request.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ge.code.generate.request.repository.BatchControlMasterRepository;
import com.ge.code.generate.request.repository.entity.BatchControlMaster;
import com.ge.code.generate.request.service.CodeGenRequestService;

@Service
public class CodeGenRequestServiceImpl implements CodeGenRequestService {
	@Autowired
	BatchControlMasterRepository batchControlMasterRepository;

	@Override
	public List<BatchControlMaster> getAllCodeGenRequests() {
		List<BatchControlMaster> batchControlMasterList = new ArrayList<BatchControlMaster>();
		//batchControlMasterRepository.findAll().forEach(batchControlMasterList :: add);
		batchControlMasterRepository.findBySourceSystem("MSSQL").forEach(batchControlMasterList :: add);
		return batchControlMasterList;
	}

	@Override
	public void addCodeGenRequest() {
		// TODO Auto-generated method stub

	}

}
