package com.ge.code.generate.request.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.ge.code.generate.request.repository.entity.ListOfValues;

public interface ListOfValuesRepository extends CrudRepository<ListOfValues, Integer>{
	public List<ListOfValues> findByType(String type);
	public List<ListOfValues> findByName(String name);
	public List<ListOfValues> findByTypeAndName(String type, String name);
}
