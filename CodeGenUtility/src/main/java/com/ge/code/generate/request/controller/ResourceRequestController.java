package com.ge.code.generate.request.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ge.code.generate.request.repository.entity.ListOfValues;
import com.ge.code.generate.request.service.ResourceRequestService;

@RestController
@RequestMapping("/resources")
public class ResourceRequestController {
	
	@Autowired
	private ResourceRequestService resourceRequestService;
	
	@RequestMapping("/findByType")
	public List<ListOfValues> findByType(@RequestParam("type") String type) {
		return resourceRequestService.findByType(type);
	}
	
	@RequestMapping("/findByName")
	public List<ListOfValues> findByName(@RequestParam("name") String name) {
		return resourceRequestService.findByName(name);
	}
	@RequestMapping("/findByTypeAndName")
	public List<ListOfValues> findByTypeAndName(@RequestParam("type") String type, @RequestParam("name") String name){
		return resourceRequestService.findByTypeAndName(type, name);
	}
	
	@RequestMapping("/getSchemaDetails")
	public String getSchemaDetails() {
		//return resourceRequestService.getAllCodeGenRequests();
		return null;
	}
	
	
	@RequestMapping("/getTables")
	public String getTables() {
		//return resourceRequestService.getAllCodeGenRequests();
		return null;
	}
	
	@RequestMapping("/getColumns")
	public String getColumns() {
		//return resourceRequestService.getAllCodeGenRequests();
		return null;
	}
	/*
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
