package com.ge.code.generate.request.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.ge.code.generate.request.controller.ConstantUtils;
import com.ge.code.generate.request.repository.BatchControlMasterRepository;
import com.ge.code.generate.request.repository.IngestSubJobControlRepository;
import com.ge.code.generate.request.repository.RequestHistoryRepository;
import com.ge.code.generate.request.repository.entity.BatchControlMaster;
import com.ge.code.generate.request.repository.entity.BatchControlMasterPrimaryKey;
import com.ge.code.generate.request.repository.entity.IngestSubJobControl;
import com.ge.code.generate.request.repository.entity.IngestSubJobControlPrimaryKey;
import com.ge.code.generate.request.repository.entity.RequestHistory;
import com.ge.code.generate.request.service.CodeGenRequestService;
import com.ge.code.generate.request.service.ResourceRequestService;
import com.ge.code.generate.request.value.object.CodeGenRequest;

@Service
public class CodeGenRequestServiceImpl implements CodeGenRequestService {
	@Autowired
	BatchControlMasterRepository batchControlMasterRepository;
	@Autowired
	IngestSubJobControlRepository ingestSubJobControlRepository; 
	@Autowired
	ResourceRequestService resourceRequestService;
	@Autowired
	RequestHistoryRepository requestHistoryRepository;
	

	@Override
	public Page<RequestHistory> getAllCodeGenRequests(String sourceType, String sourceSystem, String dbConnection, String dbName, String loadType,Date fromDate, Date toDate, Pageable pageable) {
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(ConstantUtils.DATE_FORMAT);
		String fromDateStr = null, toDateStr = null;
		if (null != fromDate) {
			calendar.setTime(fromDate);
			calendar.set(Calendar.HOUR_OF_DAY,0);
			calendar.set(Calendar.MINUTE,0);
			calendar.set(Calendar.SECOND,0);
			calendar.set(Calendar.MILLISECOND,0);
			fromDate = calendar.getTime();
			fromDateStr = simpleDateFormat.format(fromDate);
		}
		if (null != toDate) {
			calendar.setTime(toDate);
			calendar.set(Calendar.HOUR_OF_DAY,23);
			calendar.set(Calendar.MINUTE,59);
			calendar.set(Calendar.SECOND,59);
			calendar.set(Calendar.MILLISECOND,999);
			toDate = calendar.getTime();
			toDateStr = simpleDateFormat.format(toDate);
		}
		
		//return requestHistoryRepository.findBySourceTypeAndSourceSystemAndDbConnectionAndDbNameAndLoadTypeAndCreateTimeStampGreaterThanEqualAndCreateTimeStampLessThanEqualOrderByRequestIdDesc(sourceType, sourceSystem, dbConnection, dbName, loadType, fromDate, toDate, pageable);
		return requestHistoryRepository.getAllCodeGenRequests(sourceType, sourceSystem, dbConnection, dbName, loadType, fromDateStr, toDateStr, pageable);
	}

	@Override
	public void create(CodeGenRequest codeGenRequest) {
		if ((ConstantUtils.ORACLE).equalsIgnoreCase(codeGenRequest.getSourceSystem()) 
				|| (ConstantUtils.MSSQL.equalsIgnoreCase(codeGenRequest.getSourceSystem()))
						|| (ConstantUtils.TERADATA.equalsIgnoreCase(codeGenRequest.getSourceSystem()))
								|| (ConstantUtils.GREENPLUM.equalsIgnoreCase(codeGenRequest.getSourceSystem()))) {
			populateDataForRDBMS(codeGenRequest);
		} else if (ConstantUtils.FILE.equalsIgnoreCase(codeGenRequest.getSourceSystem())) {
			populateDataForFile(codeGenRequest);
		} else if (ConstantUtils.HADOOP.equalsIgnoreCase(codeGenRequest.getSourceSystem())) {
			populateDataForHadoop(codeGenRequest);
		}
	}

	@Transactional
	private void populateDataForRDBMS(CodeGenRequest codeGenRequest) {
		BatchControlMaster batchControlMaster;
		for (String sourceTableName : codeGenRequest.getSourceTableNames()) {
			String targetTableName;
			if(codeGenRequest.getSourceTableNames().size() > 1) {
				targetTableName = sourceTableName.toLowerCase();
			} else {
				targetTableName = codeGenRequest.getTargetTableName().toLowerCase();
			}
			BatchControlMasterPrimaryKey batchControlMasterPrimaryKey = new BatchControlMasterPrimaryKey();
			batchControlMasterPrimaryKey.setDefaultInstance(1);
			batchControlMasterPrimaryKey.setSubjectArea(codeGenRequest.getTargetDBName());
			batchControlMasterPrimaryKey.setTargetTableName(targetTableName);

			batchControlMaster = batchControlMasterRepository.findOne(batchControlMasterPrimaryKey);
			Date currentDate = new Date();
			if (batchControlMaster == null) {
				batchControlMaster = new BatchControlMaster(batchControlMasterPrimaryKey);
				if(ConstantUtils.LOAD_TYPE_INCREMENTAL_LOAD.equalsIgnoreCase(codeGenRequest.getLoadType())|| 
						ConstantUtils.LOAD_TYPE_FULL_LOAD.equalsIgnoreCase(codeGenRequest.getLoadType()))
					{
						batchControlMaster.setLoadType(ConstantUtils.LOAD_TYPE_OVERWRITE);
						batchControlMaster.setRefreshType(ConstantUtils.REFRESH_TYPE_FULL);
					}
				else
					{
					batchControlMaster.setLoadType(ConstantUtils.LOAD_TYPE_FIRSTLOAD);
					batchControlMaster.setRefreshType(ConstantUtils.REFRESH_TYPE_FULL);
					}
				batchControlMaster.setLastRunLoadTimestamp(currentDate);
				batchControlMaster.setCreateTimeStamp(currentDate);
				
				//Populate Default Values
				batchControlMaster.setBatchId(0);
				batchControlMaster.setMaxRunBatchId(new Long(0));
				batchControlMaster.setActiveFlag("Y");
				batchControlMaster.setRootDirectory(ConstantUtils.DATACODEGEN_BASE);
				batchControlMaster.setMasterJobName(codeGenRequest.getDbName() + "-" + ConstantUtils.ORACLE_TEMPLATE);

				// No Need to pass value as default is null
				//batchControlMaster.setOffsetVal(null);
				// No Need to pass value as default is null
				//batchControlMaster.setLastRunBatchId(null);
				batchControlMaster.setDefaultParallel("");
				batchControlMaster.setTransformationMergeOrUpdate("");
				batchControlMaster.setSourceDirectory("");
				batchControlMaster.setFillerOne("");
				batchControlMaster.setFillerTwo("");
				batchControlMaster.setFillerThree("");
				batchControlMaster.setLastKey("");

			} else {
				if (codeGenRequest.getLoadType().equalsIgnoreCase(ConstantUtils.LOAD_TYPE_FULL_LOAD)) {
					batchControlMaster.setLoadType(ConstantUtils.LOAD_TYPE_OVERWRITE);
					batchControlMaster.setRefreshType(ConstantUtils.REFRESH_TYPE_FULL);
				} else if(ConstantUtils.LOAD_TYPE_INCREMENTAL_LOAD.equalsIgnoreCase(codeGenRequest.getLoadType())) {
					batchControlMaster.setLoadType(ConstantUtils.LOAD_TYPE_APPEND);
					batchControlMaster.setRefreshType(ConstantUtils.REFRESH_TYPE_PARTIAL);
				} else if(ConstantUtils.LOAD_TYPE_FULL_HISTORY.equalsIgnoreCase(codeGenRequest.getLoadType())) {
					batchControlMaster.setLoadType(ConstantUtils.LOAD_TYPE_SCDFULL);
					batchControlMaster.setRefreshType(ConstantUtils.REFRESH_TYPE_FULL);
				}
				 else if(ConstantUtils.LOAD_TYPE_ONETIME_HISTORY.equalsIgnoreCase(codeGenRequest.getLoadType())) {
						batchControlMaster.setLoadType(ConstantUtils.LOAD_TYPE_FIRSTLOAD);
						batchControlMaster.setRefreshType(ConstantUtils.REFRESH_TYPE_FULL);
					}
				 else
				 {
					 	batchControlMaster.setLoadType(ConstantUtils.LOAD_TYPE_SCDINC);
						batchControlMaster.setRefreshType(ConstantUtils.REFRESH_TYPE_PARTIAL); 
				 }
			}
			batchControlMaster.setSource(codeGenRequest.getSource());// DBschema name
			batchControlMaster.setSourceTableName(sourceTableName.toUpperCase());
			String sourceColumnName = "";
			if(codeGenRequest.getSourceTableNames().size() > 1) {
				List<String> columns = resourceRequestService.getColumns(codeGenRequest.getUsername(), codeGenRequest.getPassword(), codeGenRequest.getDbConnection(), 
						codeGenRequest.getDbName(), codeGenRequest.getSourceSystem(), sourceTableName, codeGenRequest.getSource());
				sourceColumnName = StringUtils.join(columns.toArray(new String[columns.size()]),",");
			} else {
				sourceColumnName = StringUtils.join(codeGenRequest.getSourceColumnNames().toArray(new String[codeGenRequest.getSourceColumnNames().size()]),",");
			}
			batchControlMaster.setSourceColumnName(sourceColumnName.toUpperCase());
			batchControlMaster.setCalculateDeltaOn(codeGenRequest.getCalculateDeltaOn().toUpperCase());
			batchControlMaster.setWhereCondition(codeGenRequest.getWhereCondition());
			batchControlMaster.setArchivePeriod(codeGenRequest.getArchivePeriod());
			batchControlMaster.setTargetDBName(codeGenRequest.getTargetDBName());// hiveDBNAME
			batchControlMaster.setTargetPartitionKey(codeGenRequest.getTargetPartitionKey().toUpperCase());
			batchControlMaster.setSourceSystem(codeGenRequest.getSourceSystem());
			batchControlMaster.setUpdateTimeStamp(currentDate);
			
			IngestSubJobControl ingestSubJobControl;
			IngestSubJobControlPrimaryKey ingestSubJobControlPrimaryKey = new IngestSubJobControlPrimaryKey();
			ingestSubJobControlPrimaryKey.setTargetTableName(targetTableName);
			ingestSubJobControlPrimaryKey.setTargetDBName(codeGenRequest.getTargetDBName());
			
			ingestSubJobControl = ingestSubJobControlRepository.findOne(ingestSubJobControlPrimaryKey);
			if (ingestSubJobControl == null) {
				ingestSubJobControl = new IngestSubJobControl();
				ingestSubJobControl.setIngestSubJobControlPrimaryKey(ingestSubJobControlPrimaryKey);
				ingestSubJobControl.setEpocIdCurrent(ConstantUtils.UNIX_TIME_STAMP);
				ingestSubJobControl.setEpocIdTemp(ConstantUtils.UNIX_TIME_STAMP);
				
				ingestSubJobControl.setMasterJobName(codeGenRequest.getDbName() + "-" + ConstantUtils.ORACLE_TEMPLATE);
				//ingestSubJobControl.setScriptName(ConstantUtils.SCRIPT_NAME);
				if(ConstantUtils.LOAD_TYPE_INCREMENTAL_LOAD.equalsIgnoreCase(codeGenRequest.getLoadType())|| 
						ConstantUtils.LOAD_TYPE_FULL_LOAD.equalsIgnoreCase(codeGenRequest.getLoadType()))
				{
				if(ConstantUtils.NON_PARTITIONED.equalsIgnoreCase(codeGenRequest.getTargetTableType())) {
					ingestSubJobControl.setScriptName(ConstantUtils.SCRIPT_NAME);
				}
				else {
					ingestSubJobControl.setScriptName(ConstantUtils.SCRIPT_NAME_PARTIOTION);
				}
				
				ingestSubJobControl.setParameterFileLocation(ConstantUtils.PARAMETER_FILE_LOCATION);
				}
				else
				{
					ingestSubJobControl.setScriptName(ConstantUtils.SCRIPT_NAME_SCDTYPE);
					ingestSubJobControl.setParameterFileLocation(ConstantUtils.PARAMETER_FILE_LOCATION_SCDPARAM);
				}
				
				Date date;
				try {
					SimpleDateFormat format = new SimpleDateFormat(ConstantUtils.DATE_FORMAT); 
					date = format.parse(ConstantUtils.DEFAULT_DATE);
				} catch (ParseException e) {
					e.printStackTrace();
					date = new Date();
				} 
				ingestSubJobControl.setLastUpdatedTimeStamp(date);
				// pass null
				ingestSubJobControl.setReferenceColumnName(null);
				ingestSubJobControl.setPigProperty(null);
				ingestSubJobControl.setMaxWhereColumn(null);
				ingestSubJobControl.setIngestBatchId(null);
				ingestSubJobControl.setThresholdLimit(null);
				ingestSubJobControl.setScriptLocation("");
				ingestSubJobControl.setHiveSQLLocation("");
			}
			
			String joinKey = "";
			if (codeGenRequest.getJoinKeys()!= null && codeGenRequest.getJoinKeys().size() > 0) {
				joinKey = StringUtils.join(codeGenRequest.getJoinKeys().toArray(new String[codeGenRequest.getJoinKeys().size()]), ",").toUpperCase();
			} else {
				List<String> primaryKeys = resourceRequestService.getPrimaryKey(codeGenRequest.getUsername(), codeGenRequest.getPassword(), codeGenRequest.getDbConnection(), 
						codeGenRequest.getDbName(), codeGenRequest.getSourceSystem(), sourceTableName, codeGenRequest.getSource());
				if (primaryKeys != null && primaryKeys.size() > 0) {
					joinKey = StringUtils.join(primaryKeys.toArray(new String[primaryKeys.size()]), ",").toUpperCase();
				} else {
					joinKey = "PRIMARY";
				}	
			}
			ingestSubJobControl.setJoinKey(joinKey);
			ingestSubJobControl.setSource(codeGenRequest.getSourceSystem());
			
			RequestHistory requestHistory = new RequestHistory();
			requestHistory.setSourceType(ConstantUtils.RDBMS);
			requestHistory.setSourceSystem(codeGenRequest.getSourceSystem());
			requestHistory.setDbConnection(codeGenRequest.getDbConnection());
			requestHistory.setDbName(codeGenRequest.getDbName());
			requestHistory.setSource(codeGenRequest.getSource());
			requestHistory.setSourceTableName(sourceTableName.toUpperCase());
			requestHistory.setSourceColumnName(sourceColumnName.toUpperCase());
			requestHistory.setCalculateDeltaOn(codeGenRequest.getCalculateDeltaOn().toUpperCase());
			requestHistory.setJoinKey(joinKey);
			requestHistory.setWhereCondition(codeGenRequest.getWhereCondition());
			//requestHistory.setSourceDirectory(codeGenRequest.getSourceDirectory()); N.A. for RDBMS
			requestHistory.setFillerOne("");
			requestHistory.setFillerTwo("");
			requestHistory.setFillerThree("");
			requestHistory.setArchivePeriod(codeGenRequest.getArchivePeriod());
			requestHistory.setTargetConnection(codeGenRequest.getTargetConnection());
			requestHistory.setTargetDBName(codeGenRequest.getTargetDBName());
			/*Replicated below code as user report / request history should show value same as
			 * that of entered by user. However it is requirement of DNA team to have target
			 * table name in small case.
			 * */
			if(codeGenRequest.getSourceTableNames().size() > 1) {
				targetTableName = sourceTableName.toUpperCase();
			} else {
				targetTableName = codeGenRequest.getTargetTableName();
			}
			requestHistory.setTargetTableName(targetTableName);
			requestHistory.setTargetTableType(codeGenRequest.getTargetTableType());
			requestHistory.setTargetPartitionKey(codeGenRequest.getTargetPartitionKey().toUpperCase());
			requestHistory.setLoadType(codeGenRequest.getLoadType());
			requestHistory.setCreateTimeStamp(currentDate);
			requestHistory.setUpdateTimeStamp(currentDate);
			
			batchControlMasterRepository.save(batchControlMaster);
			ingestSubJobControlRepository.save(ingestSubJobControl);
			requestHistoryRepository.save(requestHistory);
		}
	}
	
	@Transactional
	private void populateDataForFile(CodeGenRequest codeGenRequest) {
		BatchControlMaster batchControlMaster;
		String targetTableName;
		targetTableName = codeGenRequest.getTargetTableName().toLowerCase();
		BatchControlMasterPrimaryKey batchControlMasterPrimaryKey = new BatchControlMasterPrimaryKey();
		batchControlMasterPrimaryKey.setDefaultInstance(1);
		batchControlMasterPrimaryKey.setSubjectArea(codeGenRequest.getTargetDBName());	
		batchControlMasterPrimaryKey.setTargetTableName(targetTableName);

		batchControlMaster = batchControlMasterRepository.findOne(batchControlMasterPrimaryKey);
		Date currentDate = new Date();
		if (batchControlMaster == null) {
			batchControlMaster = new BatchControlMaster(batchControlMasterPrimaryKey);
			if(ConstantUtils.LOAD_TYPE_INCREMENTAL_LOAD.equalsIgnoreCase(codeGenRequest.getLoadType())|| 
					ConstantUtils.LOAD_TYPE_FULL_LOAD.equalsIgnoreCase(codeGenRequest.getLoadType()))
				{
			batchControlMaster.setLoadType(ConstantUtils.LOAD_TYPE_OVERWRITE);
			batchControlMaster.setRefreshType(ConstantUtils.REFRESH_TYPE_FULL);
				}
			else
				{
					batchControlMaster.setLoadType(ConstantUtils.LOAD_TYPE_FIRSTLOAD);
					batchControlMaster.setRefreshType(ConstantUtils.REFRESH_TYPE_FULL);
				}
			batchControlMaster.setLastRunLoadTimestamp(currentDate);
			batchControlMaster.setCreateTimeStamp(currentDate);
			
			//Populate Default Values
			batchControlMaster.setBatchId(0);
			batchControlMaster.setMaxRunBatchId(new Long(0));
			batchControlMaster.setActiveFlag(ConstantUtils.ACTIVE_FLAG);
			batchControlMaster.setRootDirectory(ConstantUtils.DATACODEGEN_BASE);
			batchControlMaster.setMasterJobName(ConstantUtils.FILE_TEMPLATE);
			batchControlMaster.setSourceColumnName("");

			// No Need to pass value as default is null
			//batchControlMaster.setOffsetVal(null);//NA
			// No Need to pass value as default is null
			//batchControlMaster.setLastRunBatchId(null);//NA but in batch control master its default value is 0
			//batchControlMaster.setDefaultParallel("");//NA
			//batchControlMaster.setTransformationMergeOrUpdate(""); //NA
			//batchControlMaster.setLastKey("");//NA
			//batchControlMaster.setCalculateDeltaOn(ConstantUtils.INT + codeGenRequest.getCalculateDeltaOn());//NA
	

		} else {
			if (codeGenRequest.getLoadType().equalsIgnoreCase(ConstantUtils.LOAD_TYPE_FULL_LOAD)) {
				batchControlMaster.setLoadType(ConstantUtils.LOAD_TYPE_OVERWRITE);
				batchControlMaster.setRefreshType(ConstantUtils.REFRESH_TYPE_FULL);
			} else  if(ConstantUtils.LOAD_TYPE_INCREMENTAL_LOAD.equalsIgnoreCase(codeGenRequest.getLoadType())) {
				batchControlMaster.setLoadType(ConstantUtils.LOAD_TYPE_APPEND);
				batchControlMaster.setRefreshType(ConstantUtils.REFRESH_TYPE_PARTIAL);
			}
			else if(ConstantUtils.LOAD_TYPE_FULL_HISTORY.equalsIgnoreCase(codeGenRequest.getLoadType())) {
				batchControlMaster.setLoadType(ConstantUtils.LOAD_TYPE_SCDFULL);
				batchControlMaster.setRefreshType(ConstantUtils.REFRESH_TYPE_FULL);
			}
			 else if(ConstantUtils.LOAD_TYPE_ONETIME_HISTORY.equalsIgnoreCase(codeGenRequest.getLoadType())) {
					batchControlMaster.setLoadType(ConstantUtils.LOAD_TYPE_FIRSTLOAD);
					batchControlMaster.setRefreshType(ConstantUtils.REFRESH_TYPE_FULL);
				}
			 else
			 {
				 	batchControlMaster.setLoadType(ConstantUtils.LOAD_TYPE_SCDINC);
					batchControlMaster.setRefreshType(ConstantUtils.REFRESH_TYPE_PARTIAL); 
			 }
		
		}
		
		batchControlMaster.setSourceTableName(codeGenRequest.getFileName());
		batchControlMaster.setSource(codeGenRequest.getServerIp());
		batchControlMaster.setSourceDirectory(codeGenRequest.getFilePath());
		if (StringUtils.isNotEmpty(codeGenRequest.getFileDelimeter())) {
			batchControlMaster.setFillerOne("\\" + codeGenRequest.getFileDelimeter());
		}
		batchControlMaster.setFillerTwo(codeGenRequest.getRowTag());
		batchControlMaster.setFillerThree(codeGenRequest.getFileSchemaPath());
		
		//batchControlMaster.setWhereCondition(codeGenRequest.getWhereCondition());//NA
		batchControlMaster.setArchivePeriod(codeGenRequest.getArchivePeriod());
		batchControlMaster.setTargetDBName(codeGenRequest.getTargetDBName());// hiveDBNAME
		if(null != codeGenRequest.getTargetPartitionKey()) {
			batchControlMaster.setTargetPartitionKey(codeGenRequest.getTargetPartitionKey().toUpperCase()); 
		}
		else {
			batchControlMaster.setTargetPartitionKey(codeGenRequest.getTargetPartitionKey());
		}
		batchControlMaster.setSourceSystem(ConstantUtils.FILE_TYPE+codeGenRequest.getFileType());
		batchControlMaster.setUpdateTimeStamp(currentDate);
		
		IngestSubJobControl ingestSubJobControl;
		IngestSubJobControlPrimaryKey ingestSubJobControlPrimaryKey = new IngestSubJobControlPrimaryKey();
		ingestSubJobControlPrimaryKey.setTargetTableName(targetTableName);
		ingestSubJobControlPrimaryKey.setTargetDBName(codeGenRequest.getTargetDBName());
		
		ingestSubJobControl = ingestSubJobControlRepository.findOne(ingestSubJobControlPrimaryKey);
		if (ingestSubJobControl == null) {
			ingestSubJobControl = new IngestSubJobControl();
			ingestSubJobControl.setIngestSubJobControlPrimaryKey(ingestSubJobControlPrimaryKey);
			ingestSubJobControl.setEpocIdCurrent(ConstantUtils.UNIX_TIME_STAMP);
			ingestSubJobControl.setEpocIdTemp(ConstantUtils.UNIX_TIME_STAMP);
			
			ingestSubJobControl.setMasterJobName(ConstantUtils.FILE_TEMPLATE);
			
			if(ConstantUtils.LOAD_TYPE_INCREMENTAL_LOAD.equalsIgnoreCase(codeGenRequest.getLoadType())|| 
					ConstantUtils.LOAD_TYPE_FULL_LOAD.equalsIgnoreCase(codeGenRequest.getLoadType()))
			{
			if(ConstantUtils.NON_PARTITIONED.equalsIgnoreCase(codeGenRequest.getTargetTableType())) {
				ingestSubJobControl.setScriptName(ConstantUtils.SCRIPT_NAME);
			}
			else {
				ingestSubJobControl.setScriptName(ConstantUtils.SCRIPT_NAME_PARTIOTION);
			}
			
			ingestSubJobControl.setParameterFileLocation(ConstantUtils.PARAMETER_FILE_LOCATION);
			}
			else
			{
				ingestSubJobControl.setScriptName(ConstantUtils.SCRIPT_NAME_SCDTYPE);
				ingestSubJobControl.setParameterFileLocation(ConstantUtils.PARAMETER_FILE_LOCATION_SCDPARAM);

			}
			Date date;
			try {
				SimpleDateFormat format = new SimpleDateFormat(ConstantUtils.DATE_FORMAT); 
				date = format.parse(ConstantUtils.DEFAULT_DATE);
			} catch (ParseException e) {
				e.printStackTrace();
				date = new Date();
			} 
			ingestSubJobControl.setLastUpdatedTimeStamp(date);
			ingestSubJobControl.setScriptLocation(""); //NA Field is NA for File but need to pass blank ("") as column has not null constrain
			//ingestSubJobControl.setReferenceColumnName(null); //NA
			//ingestSubJobControl.setPigProperty(null);//NA
			//ingestSubJobControl.setMaxWhereColumn(null);//NA
			//ingestSubJobControl.setIngestBatchId(null);//NA
			//ingestSubJobControl.setThresholdLimit(null);//NA
			//ingestSubJobControl.setHiveSQLLocation("");//NA
		}
		
		if (codeGenRequest.getJoinKeys()!= null && codeGenRequest.getJoinKeys().size() > 0) {
			ingestSubJobControl.setJoinKey(StringUtils.join(codeGenRequest.getJoinKeys().toArray(new String[codeGenRequest.getJoinKeys().size()]), ","));
		}
		
		//ingestSubJobControl.setSource(codeGenRequest.getSourceSystem());
		ingestSubJobControl.setSource(ConstantUtils.FILE_TYPE+codeGenRequest.getFileType());
		
		RequestHistory requestHistory = new RequestHistory();
		requestHistory.setSourceType(ConstantUtils.FILE);
		requestHistory.setSourceSystem(codeGenRequest.getFileType());
		requestHistory.setDbConnection(codeGenRequest.getServerIp());
		requestHistory.setSourceTableName(codeGenRequest.getFileName());
		//requestHistory.setDbName(codeGenRequest.getDbName()); N.A. for File
		//requestHistory.setSource(codeGenRequest.getSource()); N.A. for File
		//requestHistory.setSourceColumnName(sourceColumnName); N.A. for File
		//requestHistory.setCalculateDeltaOn(codeGenRequest.getCalculateDeltaOn()); N.A. for File
		requestHistory.setJoinKey(ingestSubJobControl.getJoinKey());
		//requestHistory.setWhereCondition(codeGenRequest.getWhereCondition()); N.A. for File
		requestHistory.setSourceDirectory(codeGenRequest.getFilePath());
		requestHistory.setFillerOne(codeGenRequest.getFileDelimeter());
		requestHistory.setFillerTwo(codeGenRequest.getRowTag());
		requestHistory.setFillerThree(codeGenRequest.getFileSchemaPath());
		requestHistory.setArchivePeriod(codeGenRequest.getArchivePeriod());
		requestHistory.setTargetConnection(codeGenRequest.getTargetConnection());
		requestHistory.setTargetDBName(codeGenRequest.getTargetDBName());
		requestHistory.setTargetTableName(codeGenRequest.getTargetTableName());
		requestHistory.setTargetTableType(codeGenRequest.getTargetTableType());
		requestHistory.setTargetPartitionKey(codeGenRequest.getTargetPartitionKey());
		requestHistory.setLoadType(codeGenRequest.getLoadType());
		requestHistory.setCreateTimeStamp(currentDate);
		requestHistory.setUpdateTimeStamp(currentDate);
		
		batchControlMasterRepository.save(batchControlMaster);
		ingestSubJobControlRepository.save(ingestSubJobControl);
		requestHistoryRepository.save(requestHistory);
	}
	
	@Transactional
	private void populateDataForHadoop(CodeGenRequest codeGenRequest) {
		BatchControlMaster batchControlMaster;
		String targetTableName;
		targetTableName = codeGenRequest.getTargetTableName().toLowerCase();
		BatchControlMasterPrimaryKey batchControlMasterPrimaryKey = new BatchControlMasterPrimaryKey();
		batchControlMasterPrimaryKey.setDefaultInstance(1);
		//todo needs to be changed  
		batchControlMasterPrimaryKey.setSubjectArea(codeGenRequest.getTargetDBName());	
		//batchControlMasterPrimaryKey.setSubjectArea(ConstantUtils.SUBJ_AREA_HDFS+codeGenRequest.getTargetDBName());	
		batchControlMasterPrimaryKey.setTargetTableName(targetTableName);

		batchControlMaster = batchControlMasterRepository.findOne(batchControlMasterPrimaryKey);
		
		Date currentDate = new Date();
		if (batchControlMaster == null) {
			batchControlMaster = new BatchControlMaster(batchControlMasterPrimaryKey);
			if(ConstantUtils.LOAD_TYPE_INCREMENTAL_LOAD.equalsIgnoreCase(codeGenRequest.getLoadType())|| 
					ConstantUtils.LOAD_TYPE_FULL_LOAD.equalsIgnoreCase(codeGenRequest.getLoadType()))
				{
					batchControlMaster.setLoadType(ConstantUtils.LOAD_TYPE_OVERWRITE);
					batchControlMaster.setRefreshType(ConstantUtils.REFRESH_TYPE_FULL);
				}
			else
				{
					batchControlMaster.setLoadType(ConstantUtils.LOAD_TYPE_FIRSTLOAD);
					batchControlMaster.setRefreshType(ConstantUtils.REFRESH_TYPE_FULL);
				}
			batchControlMaster.setLastRunLoadTimestamp(currentDate);
			batchControlMaster.setCreateTimeStamp(currentDate);
			
			//Populate Default Values
			batchControlMaster.setBatchId(0);
			batchControlMaster.setMaxRunBatchId(new Long(0));
			batchControlMaster.setActiveFlag(ConstantUtils.ACTIVE_FLAG);
			batchControlMaster.setRootDirectory(ConstantUtils.DATACODEGEN_BASE);
			batchControlMaster.setMasterJobName(ConstantUtils.FILE_TEMPLATE);

			// No Need to pass value as default is null
			//batchControlMaster.setOffsetVal(null);//NA
			// No Need to pass value as default is null
			//batchControlMaster.setLastRunBatchId(null);//NA but in batch control master its default value is 0
			//batchControlMaster.setDefaultParallel("");//NA
			//batchControlMaster.setTransformationMergeOrUpdate(""); //NA
			//batchControlMaster.setLastKey("");//NA
			//batchControlMaster.setCalculateDeltaOn(ConstantUtils.INT + codeGenRequest.getCalculateDeltaOn());//NA
	
			batchControlMaster.setSourceColumnName("");//Field is NA for Hadoop but need to pass blank ("") as column has not null constrain

		} else {
			if (codeGenRequest.getLoadType().equalsIgnoreCase(ConstantUtils.LOAD_TYPE_FULL_LOAD)) {
				batchControlMaster.setLoadType(ConstantUtils.LOAD_TYPE_OVERWRITE);
				batchControlMaster.setRefreshType(ConstantUtils.REFRESH_TYPE_FULL);
			} else if(ConstantUtils.LOAD_TYPE_INCREMENTAL_LOAD.equalsIgnoreCase(codeGenRequest.getLoadType())) {
				batchControlMaster.setLoadType(ConstantUtils.LOAD_TYPE_APPEND);
				batchControlMaster.setRefreshType(ConstantUtils.REFRESH_TYPE_PARTIAL);
			}
			 else if(ConstantUtils.LOAD_TYPE_FULL_HISTORY.equalsIgnoreCase(codeGenRequest.getLoadType())) {
					batchControlMaster.setLoadType(ConstantUtils.LOAD_TYPE_SCDFULL);
					batchControlMaster.setRefreshType(ConstantUtils.REFRESH_TYPE_FULL);
				}
				 else if(ConstantUtils.LOAD_TYPE_ONETIME_HISTORY.equalsIgnoreCase(codeGenRequest.getLoadType())) {
						batchControlMaster.setLoadType(ConstantUtils.LOAD_TYPE_FIRSTLOAD);
						batchControlMaster.setRefreshType(ConstantUtils.REFRESH_TYPE_FULL);
					}
				 else
				 {
					 	batchControlMaster.setLoadType(ConstantUtils.LOAD_TYPE_SCDINC);
						batchControlMaster.setRefreshType(ConstantUtils.REFRESH_TYPE_PARTIAL); 
				 }
		}
		
		batchControlMaster.setSourceTableName(codeGenRequest.getFileName());
		batchControlMaster.setSource(codeGenRequest.getServerIp());
		batchControlMaster.setSourceDirectory(codeGenRequest.getFilePath());
		if (StringUtils.isNotEmpty(codeGenRequest.getFileDelimeter())) {
			batchControlMaster.setFillerOne("\\" + codeGenRequest.getFileDelimeter());
		}
		batchControlMaster.setFillerTwo(codeGenRequest.getRowTag());
		batchControlMaster.setFillerThree(codeGenRequest.getFileSchemaPath());
		//batchControlMaster.setWhereCondition(codeGenRequest.getWhereCondition());//NA
		batchControlMaster.setArchivePeriod(codeGenRequest.getArchivePeriod());
		batchControlMaster.setTargetDBName(codeGenRequest.getTargetDBName());// hiveDBNAME
		if(null != codeGenRequest.getTargetPartitionKey()) {
			batchControlMaster.setTargetPartitionKey(codeGenRequest.getTargetPartitionKey().toUpperCase()); 
		}
		else {
			batchControlMaster.setTargetPartitionKey(codeGenRequest.getTargetPartitionKey());
		}
		batchControlMaster.setSourceSystem(ConstantUtils.HADOOP_TYPE+codeGenRequest.getFileType());
		batchControlMaster.setUpdateTimeStamp(currentDate);
		
		IngestSubJobControl ingestSubJobControl;
		
		IngestSubJobControlPrimaryKey ingestSubJobControlPrimaryKey = new IngestSubJobControlPrimaryKey();
		ingestSubJobControlPrimaryKey.setTargetTableName(targetTableName);
		ingestSubJobControlPrimaryKey.setTargetDBName(codeGenRequest.getTargetDBName());
		
		ingestSubJobControl = ingestSubJobControlRepository.findOne(ingestSubJobControlPrimaryKey);
		if (ingestSubJobControl == null) {
			ingestSubJobControl = new IngestSubJobControl();
			ingestSubJobControl.setIngestSubJobControlPrimaryKey(ingestSubJobControlPrimaryKey);
			ingestSubJobControl.setEpocIdCurrent(ConstantUtils.UNIX_TIME_STAMP);
			ingestSubJobControl.setEpocIdTemp(ConstantUtils.UNIX_TIME_STAMP);
			
			ingestSubJobControl.setMasterJobName(ConstantUtils.FILE_TEMPLATE); 
			
			if(ConstantUtils.LOAD_TYPE_INCREMENTAL_LOAD.equalsIgnoreCase(codeGenRequest.getLoadType())|| 
					ConstantUtils.LOAD_TYPE_FULL_LOAD.equalsIgnoreCase(codeGenRequest.getLoadType()))
			{
			if(ConstantUtils.NON_PARTITIONED.equalsIgnoreCase(codeGenRequest.getTargetTableType())) {
				ingestSubJobControl.setScriptName(ConstantUtils.SCRIPT_NAME);
			}
			else {
				ingestSubJobControl.setScriptName(ConstantUtils.SCRIPT_NAME_PARTIOTION);
			}
			ingestSubJobControl.setParameterFileLocation(ConstantUtils.PARAMETER_FILE_LOCATION);
			}
			else
			{
				ingestSubJobControl.setScriptName(ConstantUtils.SCRIPT_NAME_SCDTYPE);
				ingestSubJobControl.setParameterFileLocation(ConstantUtils.PARAMETER_FILE_LOCATION_SCDPARAM);

			}
			Date date;
			try {
				SimpleDateFormat format = new SimpleDateFormat(ConstantUtils.DATE_FORMAT); 
				date = format.parse(ConstantUtils.DEFAULT_DATE);
			} catch (ParseException e) {
				e.printStackTrace();
				date = new Date();
			} 
			ingestSubJobControl.setLastUpdatedTimeStamp(date);
			ingestSubJobControl.setScriptLocation(""); //NA Field is NA for Hadoop but need to pass blank ("") as column has not null constrain
			//ingestSubJobControl.setReferenceColumnName(null); //NA
			//ingestSubJobControl.setPigProperty(null);//NA
			//ingestSubJobControl.setMaxWhereColumn(null);//NA
			//ingestSubJobControl.setIngestBatchId(null);//NA
			//ingestSubJobControl.setThresholdLimit(null);//NA
			//ingestSubJobControl.setHiveSQLLocation("");//NA
		}
		
		if (codeGenRequest.getJoinKeys()!= null && codeGenRequest.getJoinKeys().size() > 0) {
			ingestSubJobControl.setJoinKey(StringUtils.join(codeGenRequest.getJoinKeys().toArray(new String[codeGenRequest.getJoinKeys().size()]), ","));
		}
		
		//ingestSubJobControl.setSource(codeGenRequest.getSourceSystem());
		ingestSubJobControl.setSource(ConstantUtils.HADOOP_TYPE+codeGenRequest.getFileType());
		
		RequestHistory requestHistory = new RequestHistory();
		requestHistory.setSourceType(ConstantUtils.HADOOP);
		requestHistory.setSourceSystem(codeGenRequest.getFileType());
		requestHistory.setDbConnection(codeGenRequest.getServerIp());
		requestHistory.setSourceTableName(codeGenRequest.getFileName());
		//requestHistory.setDbName(codeGenRequest.getDbName()); N.A. for File
		//requestHistory.setSource(codeGenRequest.getSource()); N.A. for File
		//requestHistory.setSourceColumnName(sourceColumnName); N.A. for File
		//requestHistory.setCalculateDeltaOn(codeGenRequest.getCalculateDeltaOn()); N.A. for File
		requestHistory.setJoinKey(ingestSubJobControl.getJoinKey());
		//requestHistory.setWhereCondition(codeGenRequest.getWhereCondition()); N.A. for File
		requestHistory.setSourceDirectory(codeGenRequest.getFilePath());
		requestHistory.setFillerOne(codeGenRequest.getFileDelimeter());
		requestHistory.setFillerTwo(codeGenRequest.getRowTag());
		requestHistory.setFillerThree(codeGenRequest.getFileSchemaPath());
		requestHistory.setArchivePeriod(codeGenRequest.getArchivePeriod());
		requestHistory.setTargetConnection(codeGenRequest.getTargetConnection());
		requestHistory.setTargetDBName(codeGenRequest.getTargetDBName());
		requestHistory.setTargetTableName(codeGenRequest.getTargetTableName());
		requestHistory.setTargetTableType(codeGenRequest.getTargetTableType());
		requestHistory.setTargetPartitionKey(codeGenRequest.getTargetPartitionKey());
		requestHistory.setLoadType(codeGenRequest.getLoadType());
		requestHistory.setCreateTimeStamp(currentDate);
		requestHistory.setUpdateTimeStamp(currentDate);
		
		batchControlMasterRepository.save(batchControlMaster);
		ingestSubJobControlRepository.save(ingestSubJobControl);
		requestHistoryRepository.save(requestHistory);
	
	}
}
