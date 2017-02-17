package com.ge.code.generate.request.service;

import java.util.List;

import com.ge.code.generate.request.repository.entity.ListOfValues;

public interface ResourceRequestService {
	public List<ListOfValues> findByType(String type);
	public List<ListOfValues> findByName(String name);
	public List<ListOfValues> findByTypeAndName(String type, String name);
	public List<String> getSchemaDetails(String userid, String password, String host, String database, String rdbmsName);
	public List<String> getTables(String userid, String password, String host, String database, String rdbmsName, String schema);
	public List<String> getColumns(String userid, String password, String host, String database, String rdbmsName, String tableName);
}
