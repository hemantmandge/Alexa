package com.ge.code.generate.request.repository;

import java.util.Date;
import java.util.List;

import javax.persistence.TemporalType;

import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.ge.code.generate.request.repository.entity.RequestHistory;

public interface RequestHistoryRepository extends PagingAndSortingRepository<RequestHistory, String>{
	List<RequestHistory> findBySourceTypeAndSourceSystemAndDbConnectionAndDbNameAndLoadTypeAndCreateTimeStampGreaterThanEqualAndCreateTimeStampLessThanEqual
	(String sourceType, String sourceSystem, String dbConnection, String dbName, String loadType, @Temporal(TemporalType.TIMESTAMP) Date fromDate, @Temporal(TemporalType.TIMESTAMP)Date toDate);
}
