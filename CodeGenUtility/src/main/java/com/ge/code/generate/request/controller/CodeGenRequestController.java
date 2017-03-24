package com.ge.code.generate.request.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ge.code.generate.request.repository.entity.RequestHistory;
import com.ge.code.generate.request.service.CodeGenRequestService;
import com.ge.code.generate.request.value.object.CodeGenRequest;

@CrossOrigin
@RestController
@RequestMapping("/codeGenRequests")
public class CodeGenRequestController {
	
	@Autowired
	private CodeGenRequestService codeGenRequestService;
	
	@RequestMapping("/requests")
	public List<RequestHistory> getAllCodeGenRequests(String sourceType, String sourceSystem, String dbConnection, String dbName, String loadType, @RequestParam("fromDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date fromDate, @RequestParam("toDate") @DateTimeFormat(pattern="yyyy-MM-dd")  Date toDate) {
		return codeGenRequestService.getAllCodeGenRequests(sourceType, sourceSystem, dbConnection, dbName, loadType, fromDate, toDate);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/create")
	public void create(@RequestBody CodeGenRequest codeGenRequest){
		codeGenRequestService.create(codeGenRequest);
	}
}
