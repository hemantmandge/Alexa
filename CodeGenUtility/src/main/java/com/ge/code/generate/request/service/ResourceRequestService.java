package com.ge.code.generate.request.service;

import java.util.List;

import org.springframework.web.bind.annotation.RequestParam;

import com.ge.code.generate.request.repository.entity.ListOfValues;

public interface ResourceRequestService {
	public List<ListOfValues> findByType(String type);
	public List<ListOfValues> findByName(String name);
	public List<ListOfValues> findByTypeAndName(String type, String name);
	public List<String> getSchemaDetails(@RequestParam("userid") String userid, @RequestParam("password") String password,
			 @RequestParam("host") String host,  @RequestParam("database") String database,  @RequestParam("rdbmsName") String rdbmsName);
}
