package com.ge.code.generate.request.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ge.code.generate.request.repository.ListOfValuesRepository;
import com.ge.code.generate.request.repository.entity.ListOfValues;
import com.ge.code.generate.request.service.ResourceRequestService;

@Service
public class ResourceRequestServiceImpl implements ResourceRequestService {
	@Autowired
	ListOfValuesRepository listOfValuesRepository;

	@Override
	public List<ListOfValues> findByType(String type) {
		List<ListOfValues> listOfValues = new ArrayList<ListOfValues>();
		listOfValuesRepository.findByType(type).forEach(listOfValues :: add);
		return listOfValues;
	}

	@Override
	public List<ListOfValues> findByName(String name) {
		List<ListOfValues> listOfValues = new ArrayList<ListOfValues>();
		listOfValuesRepository.findByName(name).forEach(listOfValues :: add);
		return listOfValues;
	}

	@Override
	public List<ListOfValues> findByTypeAndName(String type, String name) {
		List<ListOfValues> listOfValues = new ArrayList<ListOfValues>();
		listOfValuesRepository.findByTypeAndName(type, name).forEach(listOfValues :: add);
		return listOfValues;
	}

}
