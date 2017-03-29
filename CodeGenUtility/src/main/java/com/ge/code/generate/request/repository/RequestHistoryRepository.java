package com.ge.code.generate.request.repository;

import java.util.Date;

import javax.persistence.TemporalType;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Temporal;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.ge.code.generate.request.repository.entity.RequestHistory;

public interface RequestHistoryRepository extends PagingAndSortingRepository<RequestHistory, String>{
	Page<RequestHistory> findBySourceTypeAndSourceSystemAndDbConnectionAndDbNameAndLoadTypeAndCreateTimeStampGreaterThanEqualAndCreateTimeStampLessThanEqualOrderByRequestIdDesc
	(String sourceType, String sourceSystem, String dbConnection, String dbName, String loadType, 
			@Temporal(TemporalType.TIMESTAMP) Date fromDate, @Temporal(TemporalType.TIMESTAMP)Date toDate, Pageable pageable);
	
	@Query(value = "SELECT REQ FROM JOB_REQ REQ "
					+ "WHERE (:sourceType IS NULL OR :sourceType = '' OR SRC=:sourceType) "
					+ "AND (:sourceSystem IS NULL OR :sourceSystem = '' OR SRC_SYS=:sourceSystem) "
					+ "AND (:dbConnection IS NULL OR :dbConnection = '' OR SRC_CONN=:dbConnection) "
					+ "AND (:dbName IS NULL OR :dbName = '' OR DB_NAME=:dbName) "
					+ "AND (:loadType IS NULL OR :loadType = '' OR LOAD_TYPE=:loadType) "
					+ "AND (:fromDate IS NULL OR :fromDate = '' OR CRTE_TMSP>=:fromDate) "
					+ "AND (:toDate IS NULL OR :toDate = '' OR CRTE_TMSP<=:toDate) "
					+ "ORDER BY REQ_ID DESC",
			countQuery = "SELECT COUNT(REQ_ID) FROM JOB_REQ REQ "
					+ "WHERE (:sourceType IS NULL OR :sourceType = '' OR SRC=:sourceType) "
					+ "AND (:sourceSystem IS NULL OR :sourceSystem = '' OR SRC_SYS=:sourceSystem) "
					+ "AND (:dbConnection IS NULL OR :dbConnection = '' OR SRC_CONN=:dbConnection) "
					+ "AND (:dbName IS NULL OR :dbName = '' OR DB_NAME=:dbName) "
					+ "AND (:loadType IS NULL OR :loadType = '' OR LOAD_TYPE=:loadType) "
					+ "AND (:fromDate IS NULL OR :fromDate = '' OR CRTE_TMSP>=:fromDate) "
					+ "AND (:toDate IS NULL OR :toDate = '' OR CRTE_TMSP<=:toDate) ",
		    nativeQuery = false)
	Page<RequestHistory> getAllCodeGenRequests(@Param("sourceType")String sourceType, @Param("sourceSystem")String sourceSystem, 
			@Param("dbConnection")String dbConnection, @Param("dbName")String dbName, @Param("loadType")String loadType, 
			@Param("fromDate")Date fromDate, @Param("toDate")Date toDate, @Param("pageable")Pageable pageable);
}
