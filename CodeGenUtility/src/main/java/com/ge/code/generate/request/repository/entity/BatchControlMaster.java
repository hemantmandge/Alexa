package com.ge.code.generate.request.repository.entity;


import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "btch_cntl_master")
public class BatchControlMaster {
	public BatchControlMaster() {
		
	}
	
	public BatchControlMaster(BatchControlMasterPrimaryKey batchControlMasterPrimaryKey) {
		super();
		this.batchControlMasterPrimaryKey = batchControlMasterPrimaryKey;
	}
	
	@EmbeddedId
	@Id
	private BatchControlMasterPrimaryKey batchControlMasterPrimaryKey;
	@Column(name = "`SOURCE`")
	private String source;
	@Column(name = "SRC_TBL_N")
	private String sourceTableName;
	@Column(name = "COLS")
	private String sourceColumnName;
	@Column(name = "INCR_COL_N")
	private String calculateDeltaOn;
	@Column(name = "WHRE_COL")
	private String whereCondition;
	@Column(name = "ARC_LIM")
	private String archivePeriod;
	@Column(name = "WRTE_MODE")
	private String loadType;
	@Column(name = "RFSH_TYPE")
	private String refreshType;
	@Column(name = "TARGET")
	private String targetDBName;
	@Column(name = "PRTN_KEY")
	private String targetPartitionKey;
	@Column(name = "LAST_KEY")
	private String lastKey;
	@Column(name = "OFFSET_VAL")
	private Integer offsetVal;
	@Column(name = "ACTV_F")
	private String activeFlag;
	@Column(name = "LAST_RUN_BTCH_ID")
	private Long lastRunBatchId = new Long(0);
	@Column(name = "LAST_RUN_LOAD_TMSP")
	private Date lastRunLoadTimestamp;
	@Column(name = "DFLT_PRLL")
	private String defaultParallel;
	@Column(name = "TRFM_MRGE_OR_UPDT")
	private String  transformationMergeOrUpdate;
	@Column(name = "MAX_RUN_BTCH_ID")
	private Long maxRunBatchId;
	@GeneratedValue(strategy=GenerationType.AUTO) 
	@Column(name = "BTCH_ID")
	private Integer batchId;
	@Column(name = "ROOT_DIR")
	private String rootDirectory;
	@Column(name = "SRC_SYS")
	private String sourceSystem;
	@Column(name = "SOURCE_DIR")
	private String sourceDirectory;
	@Column(name = "MASTER_JOB_N")
	private String masterJobName;
	@Column(name = "FILLER_1")
	private String fillerOne;
	@Column(name = "FILLER_2")
	private String fillerTwo;
	@Column(name = "FILLER_3")
	private String fillerThree;
	@Column(name = "CRTE_TMSP")
	private Date createTimeStamp;
	@Column(name = "UPDT_TMSP")
	private Date updateTimeStamp;
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
	public String getWhereCondition() {
		return whereCondition;
	}
	public void setWhereCondition(String whereCondition) {
		this.whereCondition = whereCondition;
	}
	public String getArchivePeriod() {
		return archivePeriod;
	}
	public void setArchivePeriod(String archivePeriod) {
		this.archivePeriod = archivePeriod;
	}
	public String getLoadType() {
		return loadType;
	}
	public void setLoadType(String loadType) {
		this.loadType = loadType;
	}
	public String getRefreshType() {
		return refreshType;
	}
	public void setRefreshType(String refreshType) {
		this.refreshType = refreshType;
	}
	public String getTargetDBName() {
		return targetDBName;
	}
	public void setTargetDBName(String targetDBName) {
		this.targetDBName = targetDBName;
	}
	public String getTargetPartitionKey() {
		return targetPartitionKey;
	}
	public void setTargetPartitionKey(String targetPartitionKey) {
		this.targetPartitionKey = targetPartitionKey;
	}
	public String getLastKey() {
		return lastKey;
	}
	public void setLastKey(String lastKey) {
		this.lastKey = lastKey;
	}
	public Integer getOffsetVal() {
		return offsetVal;
	}
	public void setOffsetVal(Integer offsetVal) {
		this.offsetVal = offsetVal;
	}
	public String getActiveFlag() {
		return activeFlag;
	}
	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}
	public Long getLastRunBatchId() {
		return lastRunBatchId;
	}
	public void setLastRunBatchId(Long lastRunBatchId) {
		this.lastRunBatchId = lastRunBatchId;
	}
	public Date getLastRunLoadTimestamp() {
		return lastRunLoadTimestamp;
	}
	public void setLastRunLoadTimestamp(Date lastRunLoadTimestamp) {
		this.lastRunLoadTimestamp = lastRunLoadTimestamp;
	}
	public String getDefaultParallel() {
		return defaultParallel;
	}
	public void setDefaultParallel(String defaultParallel) {
		this.defaultParallel = defaultParallel;
	}
	public String getTransformationMergeOrUpdate() {
		return transformationMergeOrUpdate;
	}
	public void setTransformationMergeOrUpdate(String transformationMergeOrUpdate) {
		this.transformationMergeOrUpdate = transformationMergeOrUpdate;
	}
	public Long getMaxRunBatchId() {
		return maxRunBatchId;
	}
	public void setMaxRunBatchId(Long maxRunBatchId) {
		this.maxRunBatchId = maxRunBatchId;
	}
	public Integer getBatchId() {
		return batchId;
	}
	public void setBatchId(Integer batchId) {
		this.batchId = batchId;
	}
	public String getRootDirectory() {
		return rootDirectory;
	}
	public void setRootDirectory(String rootDirectory) {
		this.rootDirectory = rootDirectory;
	}
	public String getSourceSystem() {
		return sourceSystem;
	}
	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}
	public String getSourceDirectory() {
		return sourceDirectory;
	}
	public void setSourceDirectory(String sourceDirectory) {
		this.sourceDirectory = sourceDirectory;
	}
	public String getMasterJobName() {
		return masterJobName;
	}
	public void setMasterJobName(String masterJobName) {
		this.masterJobName = masterJobName;
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
