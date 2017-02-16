package com.ge.code.generate.request.repository.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "ingest_sub_job_cntl")
	public class IngestSubJobControl {
	
	@Column(name = "master_job_n")
	private String masterJobName;
	@Column(name = "script_n")
	private String scriptName;
	@Column(name = "script_loc")
	private String scriptLocation;
	@Column(name = "`source`")
	private String source;
	@Id
	@Column(name = "tgt_tbl_n")
	private String targetTableName;
	@Column(name = "join_key")
	private String joinKey;
	@Column(name = "XREF_VER_COL_N")
	private String referenceColumnName;
	@Column(name = "HIVE_SQL_LOC")
	private String hiveSQLLocation;
	@Column(name = "PIG_PROP")
	private String pigProperty;
	@Column(name = "parm_file_loc")
	private String parameterFileLocation;
	@Column(name = "EPOC_ID_CURR")
	private String epocIdCurrent;
	@Column(name = "EPOC_ID_TEMP")
	private String epocIdTemp;
	@Column(name = "LST_RUN_UPDT_TS")
	private Date lastUpdatedTimeStamp;
	@Column(name = "MAXWHER_COLS")
	private String maxWhereColumn;
	@Column(name = "IGN_BTCH_ID")
	private String ingestBatchId;
	@Column(name = "THRESHOLD_LIMIT")
	private long thresholdLimit;
	
	public String getMasterJobName() {
		return masterJobName;
	}
	public void setMasterJobName(String masterJobName) {
		this.masterJobName = masterJobName;
	}
	public String getScriptName() {
		return scriptName;
	}
	public void setScriptName(String scriptName) {
		this.scriptName = scriptName;
	}
	public String getScriptLocation() {
		return scriptLocation;
	}
	public void setScriptLocation(String scriptLocation) {
		this.scriptLocation = scriptLocation;
	}
	public String getSource() {
		return source;
	}
	public void setSource(String source) {
		this.source = source;
	}
	public String getTargetTableName() {
		return targetTableName;
	}
	public void setTargetTableName(String targetTableName) {
		this.targetTableName = targetTableName;
	}
	public String getJoinKey() {
		return joinKey;
	}
	public void setJoinKey(String joinKey) {
		this.joinKey = joinKey;
	}
	public String getReferenceColumnName() {
		return referenceColumnName;
	}
	public void setReferenceColumnName(String referenceColumnName) {
		this.referenceColumnName = referenceColumnName;
	}
	public String getHiveSQLLocation() {
		return hiveSQLLocation;
	}
	public void setHiveSQLLocation(String hiveSQLLocation) {
		this.hiveSQLLocation = hiveSQLLocation;
	}
	public String getPigProperty() {
		return pigProperty;
	}
	public void setPigProperty(String pigProperty) {
		this.pigProperty = pigProperty;
	}
	public String getParameterFileLocation() {
		return parameterFileLocation;
	}
	public void setParameterFileLocation(String parameterFileLocation) {
		this.parameterFileLocation = parameterFileLocation;
	}
	public String getEpocIdCurrent() {
		return epocIdCurrent;
	}
	public void setEpocIdCurrent(String epocIdCurrent) {
		this.epocIdCurrent = epocIdCurrent;
	}
	public String getEpocIdTemp() {
		return epocIdTemp;
	}
	public void setEpocIdTemp(String epocIdTemp) {
		this.epocIdTemp = epocIdTemp;
	}
	public Date getLastUpdatedTimeStamp() {
		return lastUpdatedTimeStamp;
	}
	public void setLastUpdatedTimeStamp(Date lastUpdatedTimeStamp) {
		this.lastUpdatedTimeStamp = lastUpdatedTimeStamp;
	}
	public String getMaxWhereColumn() {
		return maxWhereColumn;
	}
	public void setMaxWhereColumn(String maxWhereColumn) {
		this.maxWhereColumn = maxWhereColumn;
	}
	public String getIngestBatchId() {
		return ingestBatchId;
	}
	public void setIngestBatchId(String ingestBatchId) {
		this.ingestBatchId = ingestBatchId;
	}
	public long getThresholdLimit() {
		return thresholdLimit;
	}
	public void setThresholdLimit(long thresholdLimit) {
		this.thresholdLimit = thresholdLimit;
	}
	
}
