package com.ge.code.generate.request.service;

import java.util.List;

import com.ge.code.generate.request.repository.entity.ListOfValues;

public interface ResourceRequestService {
	public List<ListOfValues> findByType(String type);
	public List<ListOfValues> findByName(String name);
	public List<ListOfValues> findByTypeAndName(String type, String name);
}
