package com.ge.code.generate.request.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ge.code.generate.request.controller.ConstantUtils;
import com.ge.code.generate.request.repository.BatchControlMasterRepository;
import com.ge.code.generate.request.repository.IngestSubJobControlRepository;
import com.ge.code.generate.request.repository.entity.BatchControlMaster;
import com.ge.code.generate.request.repository.entity.BatchControlMasterPrimaryKey;
import com.ge.code.generate.request.repository.entity.IngestSubJobControl;
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

	@Override
	public List<BatchControlMaster> getAllCodeGenRequests() {
		List<BatchControlMaster> batchControlMasterList = new ArrayList<BatchControlMaster>();
		// batchControlMasterRepository.findAll().forEach(batchControlMasterList
		// :: add);
		batchControlMasterRepository.findBySourceSystem("MSSQL").forEach(batchControlMasterList::add);
		return batchControlMasterList;
	}

	@Override
	public void create(CodeGenRequest codeGenRequest) {
		if ((codeGenRequest.getSourceType().equalsIgnoreCase(ConstantUtils.ORACLE)) 
				|| (codeGenRequest.getSourceType().equalsIgnoreCase(ConstantUtils.MSSQL))
						|| (codeGenRequest.getSourceType().equalsIgnoreCase(ConstantUtils.TERADATA))
								|| (codeGenRequest.getSourceType().equalsIgnoreCase(ConstantUtils.GREENPLUM))) {
			populateDataForRDBMS(codeGenRequest);
		} else if (codeGenRequest.getSourceType().equalsIgnoreCase(ConstantUtils.FILE)) {
			populateDataForFile(codeGenRequest);
		}
	}

	@Transactional
	private void populateDataForRDBMS(CodeGenRequest codeGenRequest) {
		BatchControlMaster batchControlMaster;
		for (String sourceTableName : codeGenRequest.getSourceTableNames()) {
			String targetTableName;
			if(codeGenRequest.getSourceTableNames().size() > 1) {
				targetTableName = sourceTableName;
			} else {
				targetTableName = codeGenRequest.getTargetTableName();
			}
			BatchControlMasterPrimaryKey batchControlMasterPrimaryKey = new BatchControlMasterPrimaryKey();
			batchControlMasterPrimaryKey.setDefaultInstance(1);
			batchControlMasterPrimaryKey.setSubjectArea(codeGenRequest.getTargetDBName());
			batchControlMasterPrimaryKey.setTargetTableName(targetTableName);

			batchControlMaster = batchControlMasterRepository.findOne(batchControlMasterPrimaryKey);
			if (batchControlMaster == null) {
				batchControlMaster = new BatchControlMaster(batchControlMasterPrimaryKey);
				batchControlMaster.setLoadType(ConstantUtils.LOAD_TYPE_OVERWRITE);
				batchControlMaster.setRefreshType(ConstantUtils.REFRESH_TYPE_FULL);
				batchControlMaster.setLastRunLoadTimestamp(new Date());
				batchControlMaster.setCreateTimeStamp(new Date());
				
				//Populate Default Values
				batchControlMaster.setBatchId(batchControlMasterRepository.getMaxBatchId() + 1);
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
				} else {
					batchControlMaster.setLoadType(ConstantUtils.LOAD_TYPE_APPEND);
					batchControlMaster.setRefreshType(ConstantUtils.REFRESH_TYPE_PARTIAL);
				}
			}
			batchControlMaster.setSource(codeGenRequest.getSource());// DBschema name
			batchControlMaster.setSourceTableName(sourceTableName);
			if(codeGenRequest.getSourceTableNames().size() > 1) {
				List<String> columns = resourceRequestService.getColumns(codeGenRequest.getUsername(), codeGenRequest.getPassword(), codeGenRequest.getDbConnection(), 
						codeGenRequest.getDbName(), codeGenRequest.getSourceType(), sourceTableName, codeGenRequest.getSource());
				batchControlMaster.setSourceColumnName(StringUtils.join(columns.toArray(new String[columns.size()]),","));
			} else {
				batchControlMaster.setSourceColumnName(StringUtils.join(codeGenRequest.getSourceColumnNames().toArray(new String[codeGenRequest.getSourceColumnNames().size()]),","));
			}
			batchControlMaster.setCalculateDeltaOn(codeGenRequest.getCalculateDeltaOn());
			batchControlMaster.setWhereCondition(codeGenRequest.getWhereCondition());
			batchControlMaster.setArchivePeriod(codeGenRequest.getArchivePeriod());
			batchControlMaster.setTargetDBName(codeGenRequest.getTargetDBName());// hiveDBNAME
			batchControlMaster.setTargetPartitionKey(codeGenRequest.getTargetPartitionKey());
			batchControlMaster.setSourceSystem(codeGenRequest.getSourceType());
			batchControlMaster.setUpdateTimeStamp(new Date());
			
			IngestSubJobControl ingestSubJobControl;
			
			ingestSubJobControl = ingestSubJobControlRepository.findOne(targetTableName);
			if (ingestSubJobControl == null) {
				ingestSubJobControl = new IngestSubJobControl();
				ingestSubJobControl.setTargetTableName(targetTableName);
				ingestSubJobControl.setEpocIdCurrent(ConstantUtils.UNIX_TIME_STAMP);
				ingestSubJobControl.setEpocIdTemp(ConstantUtils.UNIX_TIME_STAMP);
				
				ingestSubJobControl.setMasterJobName(codeGenRequest.getDbName() + "-" + ConstantUtils.ORACLE_TEMPLATE);
				//ingestSubJobControl.setScriptName(ConstantUtils.SCRIPT_NAME);
				if(ConstantUtils.NON_PARTITIONED.equalsIgnoreCase(codeGenRequest.getHiveTableType())) {
					ingestSubJobControl.setScriptName(ConstantUtils.SCRIPT_NAME);
				}
				else {
					ingestSubJobControl.setScriptName(ConstantUtils.SCRIPT_NAME_PARTIOTION);
				}
				ingestSubJobControl.setParameterFileLocation(ConstantUtils.PARAMETER_FILE_LOCATION);
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
			
			if (codeGenRequest.getJoinKeys()!= null && codeGenRequest.getJoinKeys().size() > 0) {
				ingestSubJobControl.setJoinKey(StringUtils.join(codeGenRequest.getJoinKeys().toArray(new String[codeGenRequest.getJoinKeys().size()]), ","));
			} else {
				List<String> primaryKeys = resourceRequestService.getPrimaryKey(codeGenRequest.getUsername(), codeGenRequest.getPassword(), codeGenRequest.getDbConnection(), 
						codeGenRequest.getDbName(), codeGenRequest.getSourceType(), sourceTableName, codeGenRequest.getSource());
				if (primaryKeys != null && primaryKeys.size() > 0) {
					ingestSubJobControl.setJoinKey(StringUtils.join(primaryKeys.toArray(new String[primaryKeys.size()]), ","));
				} else {
					ingestSubJobControl.setJoinKey("PRIMARY");
				}	
			}
			ingestSubJobControl.setSource(codeGenRequest.getSourceType());
			
			batchControlMasterRepository.save(batchControlMaster);
			ingestSubJobControlRepository.save(ingestSubJobControl);
		}
	}
	
	@Transactional
	private void populateDataForFile(CodeGenRequest codeGenRequest) {
		BatchControlMaster batchControlMaster;
		String targetTableName;
		targetTableName = codeGenRequest.getTargetTableName();
		BatchControlMasterPrimaryKey batchControlMasterPrimaryKey = new BatchControlMasterPrimaryKey();
		batchControlMasterPrimaryKey.setDefaultInstance(1);
		batchControlMasterPrimaryKey.setSubjectArea(codeGenRequest.getTargetDBName());	
		batchControlMasterPrimaryKey.setTargetTableName(targetTableName);

		batchControlMaster = batchControlMasterRepository.findOne(batchControlMasterPrimaryKey);
		if (batchControlMaster == null) {
			batchControlMaster = new BatchControlMaster(batchControlMasterPrimaryKey);
			batchControlMaster.setLoadType(ConstantUtils.LOAD_TYPE_OVERWRITE);
			batchControlMaster.setRefreshType(ConstantUtils.REFRESH_TYPE_FULL);
			batchControlMaster.setLastRunLoadTimestamp(new Date());
			batchControlMaster.setCreateTimeStamp(new Date());
			
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
	
			batchControlMaster.setSourceTableName("");
			batchControlMaster.setSourceColumnName("");

		} else {
			if (codeGenRequest.getLoadType().equalsIgnoreCase(ConstantUtils.LOAD_TYPE_FULL_LOAD)) {
				batchControlMaster.setLoadType(ConstantUtils.LOAD_TYPE_OVERWRITE);
				batchControlMaster.setRefreshType(ConstantUtils.REFRESH_TYPE_FULL);
			} else {
				batchControlMaster.setLoadType(ConstantUtils.LOAD_TYPE_APPEND);
				batchControlMaster.setRefreshType(ConstantUtils.REFRESH_TYPE_PARTIAL);
			}
		}
		
		batchControlMaster.setSource(codeGenRequest.getSFTPserverIp());
		batchControlMaster.setSourceDirectory(codeGenRequest.getFilePath());
		batchControlMaster.setFillerOne(codeGenRequest.getFileDelimeter());
		batchControlMaster.setFillerTwo(codeGenRequest.getRowTag());
		batchControlMaster.setFillerThree(codeGenRequest.getFileSchemaPath());
		
		//batchControlMaster.setWhereCondition(codeGenRequest.getWhereCondition());//NA
		batchControlMaster.setArchivePeriod(codeGenRequest.getArchivePeriod());
		batchControlMaster.setTargetDBName(codeGenRequest.getTargetDBName());// hiveDBNAME
		batchControlMaster.setTargetPartitionKey(codeGenRequest.getTargetPartitionKey().toUpperCase());
		batchControlMaster.setSourceSystem(ConstantUtils.FILE_TYPE+codeGenRequest.getFileType());
		batchControlMaster.setUpdateTimeStamp(new Date());
		
		IngestSubJobControl ingestSubJobControl;
		
		ingestSubJobControl = ingestSubJobControlRepository.findOne(targetTableName);
		if (ingestSubJobControl == null) {
			ingestSubJobControl = new IngestSubJobControl();
			ingestSubJobControl.setTargetTableName(targetTableName);
			ingestSubJobControl.setEpocIdCurrent(ConstantUtils.UNIX_TIME_STAMP);
			ingestSubJobControl.setEpocIdTemp(ConstantUtils.UNIX_TIME_STAMP);
			
			ingestSubJobControl.setMasterJobName(ConstantUtils.FILE_TEMPLATE);
			if(ConstantUtils.NON_PARTITIONED.equalsIgnoreCase(codeGenRequest.getHiveTableType())) {
				ingestSubJobControl.setScriptName(ConstantUtils.SCRIPT_NAME);
			}
			else {
				ingestSubJobControl.setScriptName(ConstantUtils.SCRIPT_NAME_PARTIOTION);
			}
			ingestSubJobControl.setParameterFileLocation(ConstantUtils.PARAMETER_FILE_LOCATION);
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
		
		//ingestSubJobControl.setSource(codeGenRequest.getSourceType());
		ingestSubJobControl.setSource(ConstantUtils.FILE_TYPE+codeGenRequest.getFileType());
		
		batchControlMasterRepository.save(batchControlMaster);
		ingestSubJobControlRepository.save(ingestSubJobControl);
	}
	
	@Transactional
	private void populateDataForHadoop(CodeGenRequest codeGenRequest) {
		BatchControlMaster batchControlMaster;
		String targetTableName;
		targetTableName = codeGenRequest.getTargetTableName();
		BatchControlMasterPrimaryKey batchControlMasterPrimaryKey = new BatchControlMasterPrimaryKey();
		batchControlMasterPrimaryKey.setDefaultInstance(1);
		//todo needs to be changed  
		batchControlMasterPrimaryKey.setSubjectArea(codeGenRequest.getTargetDBName());	
		//batchControlMasterPrimaryKey.setSubjectArea(ConstantUtils.SUBJ_AREA_HDFS+codeGenRequest.getTargetDBName());	
		batchControlMasterPrimaryKey.setTargetTableName(targetTableName);

		batchControlMaster = batchControlMasterRepository.findOne(batchControlMasterPrimaryKey);
		if (batchControlMaster == null) {
			batchControlMaster = new BatchControlMaster(batchControlMasterPrimaryKey);
			batchControlMaster.setLoadType(ConstantUtils.LOAD_TYPE_OVERWRITE);
			batchControlMaster.setRefreshType(ConstantUtils.REFRESH_TYPE_FULL);
			batchControlMaster.setLastRunLoadTimestamp(new Date());
			batchControlMaster.setCreateTimeStamp(new Date());
			
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
	
			batchControlMaster.setSourceTableName("");//Field is NA for Hadoop but need to pass blank ("") as column has not null constrain
			batchControlMaster.setSourceColumnName("");//Field is NA for Hadoop but need to pass blank ("") as column has not null constrain

		} else {
			if (codeGenRequest.getLoadType().equalsIgnoreCase(ConstantUtils.LOAD_TYPE_FULL_LOAD)) {
				batchControlMaster.setLoadType(ConstantUtils.LOAD_TYPE_OVERWRITE);
				batchControlMaster.setRefreshType(ConstantUtils.REFRESH_TYPE_FULL);
			} else {
				batchControlMaster.setLoadType(ConstantUtils.LOAD_TYPE_APPEND);
				batchControlMaster.setRefreshType(ConstantUtils.REFRESH_TYPE_PARTIAL);
			}
		}
		
		//TODO
		batchControlMaster.setSource(codeGenRequest.getSFTPserverIp());
		batchControlMaster.setSourceDirectory(codeGenRequest.getFilePath());
		batchControlMaster.setFillerOne(codeGenRequest.getFileDelimeter());
		batchControlMaster.setFillerTwo(codeGenRequest.getRowTag());
		batchControlMaster.setFillerThree(codeGenRequest.getFileSchemaPath());
		//batchControlMaster.setWhereCondition(codeGenRequest.getWhereCondition());//NA
		batchControlMaster.setArchivePeriod(codeGenRequest.getArchivePeriod());
		batchControlMaster.setTargetDBName(codeGenRequest.getTargetDBName());// hiveDBNAME
		batchControlMaster.setTargetPartitionKey(codeGenRequest.getTargetPartitionKey().toUpperCase());
		batchControlMaster.setSourceSystem(ConstantUtils.HADOOP_TYPE+codeGenRequest.getFileType());
		batchControlMaster.setUpdateTimeStamp(new Date());
		
		IngestSubJobControl ingestSubJobControl;
		
		ingestSubJobControl = ingestSubJobControlRepository.findOne(targetTableName);
		if (ingestSubJobControl == null) {
			ingestSubJobControl = new IngestSubJobControl();
			ingestSubJobControl.setTargetTableName(targetTableName);
			ingestSubJobControl.setEpocIdCurrent(ConstantUtils.UNIX_TIME_STAMP);
			ingestSubJobControl.setEpocIdTemp(ConstantUtils.UNIX_TIME_STAMP);
			
			ingestSubJobControl.setMasterJobName(ConstantUtils.FILE_TEMPLATE); 
			if(ConstantUtils.NON_PARTITIONED.equalsIgnoreCase(codeGenRequest.getHiveTableType())) {
				ingestSubJobControl.setScriptName(ConstantUtils.SCRIPT_NAME);
			}
			else {
				ingestSubJobControl.setScriptName(ConstantUtils.SCRIPT_NAME_PARTIOTION);
			}
			ingestSubJobControl.setParameterFileLocation(ConstantUtils.PARAMETER_FILE_LOCATION);
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
		
		//TODO
		if (codeGenRequest.getJoinKeys()!= null && codeGenRequest.getJoinKeys().size() > 0) {
			ingestSubJobControl.setJoinKey(StringUtils.join(codeGenRequest.getJoinKeys().toArray(new String[codeGenRequest.getJoinKeys().size()]), ","));
		}
		
		//ingestSubJobControl.setSource(codeGenRequest.getSourceType());
		ingestSubJobControl.setSource(ConstantUtils.FILE_TYPE+codeGenRequest.getFileType());
		
		batchControlMasterRepository.save(batchControlMaster);
		ingestSubJobControlRepository.save(ingestSubJobControl);
	}
}
