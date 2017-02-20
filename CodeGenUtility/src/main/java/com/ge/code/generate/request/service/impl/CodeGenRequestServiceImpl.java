package com.ge.code.generate.request.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
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
		if (codeGenRequest.getSourceType().equalsIgnoreCase(ConstantUtils.ORACLE)) {
			populateDataForOracle(codeGenRequest);
		}
	}

	private void populateDataForOracle(CodeGenRequest codeGenRequest) {
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
				batchControlMaster.setMaxRunBatchId(new Long(0));
				batchControlMaster.setActiveFlag("Y");
				batchControlMaster.setRootDirectory(ConstantUtils.DATACODEGEN_BASE);
				batchControlMaster.setMasterJobName(codeGenRequest.getDbName() + "-" + ConstantUtils.ORACLE_TEMPLATE);

				// TODO: setoffsetval is int
				//batchControlMaster.setOffsetVal();
				// TODO: setLastRunBatchId is long
				//batchControlMaster.setLastRunBatchId();
				// TODO: Commenting below code as nothing to be passed.
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
						codeGenRequest.getDbName(), codeGenRequest.getSourceType(), sourceTableName);
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
			
			// TODO: Check if value is incremented
			// batchControlMaster.setBatchId(null);
			IngestSubJobControl ingestSubJobControl;
			
			ingestSubJobControl = ingestSubJobControlRepository.findOne(targetTableName);
			if (ingestSubJobControl == null) {
				ingestSubJobControl = new IngestSubJobControl();
				ingestSubJobControl.setTargetTableName(targetTableName);
				ingestSubJobControl.setEpocIdCurrent(ConstantUtils.UNIX_TIME_STAMP);
				ingestSubJobControl.setEpocIdTemp(ConstantUtils.UNIX_TIME_STAMP);
				
				ingestSubJobControl.setMasterJobName(codeGenRequest.getDbName() + "-" + ConstantUtils.ORACLE_TEMPLATE);
				ingestSubJobControl.setScriptName(ConstantUtils.ORACLE_SCRIPT_NAME);
				ingestSubJobControl.setParameterFileLocation(ConstantUtils.ORACLE_PARAMETER_FILE_LOCATION);
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
			
			ingestSubJobControl.setJoinKey(StringUtils.join(codeGenRequest.getJoinKeys().toArray(new String[codeGenRequest.getJoinKeys().size()]), ","));
			ingestSubJobControl.setSource(codeGenRequest.getSourceType());
			
			batchControlMasterRepository.save(batchControlMaster);
			ingestSubJobControlRepository.save(ingestSubJobControl);
		}
	}
}
