package com.ge.code.generate.request.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.ge.code.generate.request.repository.entity.RequestHistory;

public interface RequestHistoryRepository extends PagingAndSortingRepository<RequestHistory, String>{
}
