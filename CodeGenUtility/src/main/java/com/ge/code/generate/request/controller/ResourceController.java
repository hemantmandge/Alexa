package com.ge.code.generate.request.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ge.code.generate.request.service.CodeGenRequestService;

@RestController
@RequestMapping("/repository")
public class ResourceController {
	
	@Autowired
	private CodeGenRequestService codeGenRequestService;
	/*
	@RequestMapping("/getDataSources")
	public String getDataSources() {
		return codeGenRequestService.getAllCodeGenRequests();
	}
	
	@RequestMapping("/getHostDetails")
	public String getHostDetails() {
		return codeGenRequestService.getAllCodeGenRequests();
	}
	
	@RequestMapping("/getDataBaseDetails")
	public String getDataBaseDetails(@RequestParam("userId") String userId, @RequestParam("password") String password, 
			@RequestParam("host") String host, @RequestParam("datasource") String datasource) {
		return codeGenRequestService.getAllCodeGenRequests();
	}
	
	@RequestMapping("/getTables")
	public String getTables() {
		return codeGenRequestService.getAllCodeGenRequests();
	}
	
	@RequestMapping("/getColumns")
	public String getColumns() {
		return codeGenRequestService.getAllCodeGenRequests();
	}
	
	@RequestMapping("/getRequestLoadType")
	public String getRequestLoadType() {
		return codeGenRequestService.getAllCodeGenRequests();
	}
	
	@RequestMapping("/getHiveTableType")
	public String getHiveTableType() {
		return codeGenRequestService.getAllCodeGenRequests();
	}
	
	@RequestMapping("/getSFTPSourceDetails")
	public String getSFTPSourceDetails() {
		return codeGenRequestService.getAllCodeGenRequests();
	}
	
	@RequestMapping("/getFileType")
	public String getFileType() {
		return codeGenRequestService.getAllCodeGenRequests();
	}*/
}
