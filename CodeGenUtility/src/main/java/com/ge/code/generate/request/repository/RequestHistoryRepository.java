package com.ge.code.generate.request.repository;

import java.util.Date;

import javax.persistence.TemporalType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ge.code.generate.request.repository.entity.RequestHistory;

public interface RequestHistoryRepository extends PagingAndSortingRepository<RequestHistory, String>{
	Page<RequestHistory> findBySourceTypeAndSourceSystemAndDbConnectionAndDbNameAndLoadTypeAndCreateTimeStampGreaterThanEqualAndCreateTimeStampLessThanEqual
	(String sourceType, String sourceSystem, String dbConnection, String dbName, String loadType, 
			@Temporal(TemporalType.TIMESTAMP) Date fromDate, @Temporal(TemporalType.TIMESTAMP)Date toDate, Pageable pageable);
}
