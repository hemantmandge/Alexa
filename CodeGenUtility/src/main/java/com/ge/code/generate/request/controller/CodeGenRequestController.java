package com.ge.code.generate.request.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ge.code.generate.request.repository.entity.BatchControlMaster;
import com.ge.code.generate.request.service.CodeGenRequestService;

@RestController
@RequestMapping("/requests")
public class CodeGenRequestController {
	
	@Autowired
	private CodeGenRequestService codeGenRequestService;
	
	@RequestMapping("/createCodeGenRequest")
	public List<BatchControlMaster> getAllCodeGenRequests() {
		return codeGenRequestService.getAllCodeGenRequests();
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/codeGenRequests")
	public void addCodeGenRequest(){
		
	}
}
