package com.ge.code.generate.request.repository.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "JOB_REQ")
public class RequestHistory {

	@Id
	@Column(name = "REQ_ID")
	private Integer requestId;
	@Column(name = "SRC")
	private String sourceType;
	@Column(name = "SRC_SYS")
	private String sourceSystem;
	@Column(name = "SRC_CONN")
	private String dbConnection;
	@Column(name = "DB_NAME")
	private String dbName;
	@Column(name = "SOURCE")
	private String source;
	@Column(name = "SRC_TBL_N")
	private String sourceTableName;
	@Column(name = "COLS")
	private String sourceColumnName;
	@Column(name = "INCR_COL_N")
	private String calculateDeltaOn;
	@Column(name = "JOIN_KEY")
	private String joinKey;
	@Column(name = "WHRE_COL")
	private String whereCondition;
	@Column(name = "SOURCE_DIR")
	private String sourceDirectory;
	@Column(name = "ARC_LIM")
	private String archivePeriod;
	@Column(name = "FILLER_1")
	private String fillerOne;
	@Column(name = "FILLER_2")
	private String fillerTwo;
	@Column(name = "FILLER_3")
	private String fillerThree;
	@Column(name = "TGT_CONN")
	private String targetConnection;
	@Column(name = "TARGET")
	private String targetDBName;
	@Column(name = "TGT_TBL_N")
	private String targetTableName;
	@Column(name = "TGT_TBL_TYPE")
	private String targetTableType;
	@Column(name = "PRTN_KEY")
	private String targetPartitionKey;
	@Column(name = "LOAD_TYPE")
	private String loadType;
	@Column(name = "CRTE_TMSP")
	private Date createTimeStamp;
	@Column(name = "UPDT_TMSP")
	private Date updateTimeStamp;
	
	public Integer getRequestId() {
		return requestId;
	}
	public void setRequestId(Integer requestId) {
		this.requestId = requestId;
	}
	public String getSourceType() {
		return sourceType;
	}
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}
	public String getSourceSystem() {
		return sourceSystem;
	}
	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}
	public String getDbConnection() {
		return dbConnection;
	}
	public void setDbConnection(String dbConnection) {
		this.dbConnection = dbConnection;
	}
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getSourceTableName() {
		return sourceTableName;
	}
	public void setSourceTableName(String sourceTableName) {
		this.sourceTableName = sourceTableName;
	}
	public String getSourceColumnName() {
		return sourceColumnName;
	}
	public void setSourceColumnName(String sourceColumnName) {
		this.sourceColumnName = sourceColumnName;
	}
	public String getCalculateDeltaOn() {
		return calculateDeltaOn;
	}
	public void setCalculateDeltaOn(String calculateDeltaOn) {
		this.calculateDeltaOn = calculateDeltaOn;
	}
	public String getJoinKey() {
		return joinKey;
	}
	public void setJoinKey(String joinKey) {
		this.joinKey = joinKey;
	}
	public String getWhereCondition() {
		return whereCondition;
	}
	public void setWhereCondition(String whereCondition) {
		this.whereCondition = whereCondition;
	}
	public String getSourceDirectory() {
		return sourceDirectory;
	}
	public void setSourceDirectory(String sourceDirectory) {
		this.sourceDirectory = sourceDirectory;
	}
	public String getArchivePeriod() {
		return archivePeriod;
	}
	public void setArchivePeriod(String archivePeriod) {
		this.archivePeriod = archivePeriod;
	}
	public String getFillerOne() {
		return fillerOne;
	}
	public void setFillerOne(String fillerOne) {
		this.fillerOne = fillerOne;
	}
	public String getFillerTwo() {
		return fillerTwo;
	}
	public void setFillerTwo(String fillerTwo) {
		this.fillerTwo = fillerTwo;
	}
	public String getFillerThree() {
		return fillerThree;
	}
	public void setFillerThree(String fillerThree) {
		this.fillerThree = fillerThree;
	}
	public String getTargetConnection() {
		return targetConnection;
	}
	public void setTargetConnection(String targetConnection) {
		this.targetConnection = targetConnection;
	}
	public String getTargetDBName() {
		return targetDBName;
	}
	public void setTargetDBName(String targetDBName) {
		this.targetDBName = targetDBName;
	}
	public String getTargetTableName() {
		return targetTableName;
	}
	public void setTargetTableName(String targetTableName) {
		this.targetTableName = targetTableName;
	}
	public String getTargetTableType() {
		return targetTableType;
	}
	public void setTargetTableType(String targetTableType) {
		this.targetTableType = targetTableType;
	}
	public String getTargetPartitionKey() {
		return targetPartitionKey;
	}
	public void setTargetPartitionKey(String targetPartitionKey) {
		this.targetPartitionKey = targetPartitionKey;
	}
	public String getLoadType() {
		return loadType;
	}
	public void setLoadType(String loadType) {
		this.loadType = loadType;
	}
	public Date getCreateTimeStamp() {
		return createTimeStamp;
	}
	public void setCreateTimeStamp(Date createTimeStamp) {
		this.createTimeStamp = createTimeStamp;
	}
	public Date getUpdateTimeStamp() {
		return updateTimeStamp;
	}
	public void setUpdateTimeStamp(Date updateTimeStamp) {
		this.updateTimeStamp = updateTimeStamp;
	}
}
