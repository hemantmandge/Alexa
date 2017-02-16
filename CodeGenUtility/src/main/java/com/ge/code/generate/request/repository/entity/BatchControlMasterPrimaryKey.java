package com.ge.code.generate.request.repository.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class BatchControlMasterPrimaryKey implements Serializable{
	@Column(name = "SUBJ_AREA", nullable = false)
	private String subjectArea;
	@Column(name = "TGT_TBL_N", nullable = false)
	private String targetTableName;
	@Column(name = "DFLT_INS", nullable = false)
	private Integer defaultInstance;
	
	public String getSubjectArea() {
		return subjectArea;
	}
	public void setSubjectArea(String subjectArea) {
		this.subjectArea = subjectArea;
	}
	public String getTargetTableName() {
		return targetTableName;
	}
	public void setTargetTableName(String targetTableName) {
		this.targetTableName = targetTableName;
	}
	public Integer getDefaultInstance() {
		return defaultInstance;
	}
	public void setDefaultInstance(Integer defaultInstance) {
		this.defaultInstance = defaultInstance;
	}
}
