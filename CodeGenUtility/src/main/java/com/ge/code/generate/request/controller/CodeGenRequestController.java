package com.ge.code.generate.request.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.ge.code.generate.request.service.CodeGenRequestService;

@RestController
public class CodeGenRequestController {
	
	@Autowired
	private CodeGenRequestService codeGenRequestService;
	
	@RequestMapping("/codeGenRequests")
	public String getAllCodeGenRequests() {
		return codeGenRequestService.getAllCodeGenRequests();
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/codeGenRequests")
	public void addCodeGenRequest(){
		
	}
}
