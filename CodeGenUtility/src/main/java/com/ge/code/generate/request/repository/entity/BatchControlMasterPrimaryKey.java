package com.ge.code.generate.request.repository.entity;


import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class BatchControlMasterPrimaryKey {
	@Column(name = "SUBJ_AREA", nullable = false)
	private String subjectArea;
	@Column(name = "TGT_TBL_N", nullable = false)
	private String targetTableName;
	@Column(name = "DFLT_INS", nullable = false)
	private int defaultInstance;
	
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
	public int getDefaultInstance() {
		return defaultInstance;
	}
	public void setDefaultInstance(int defaultInstance) {
		this.defaultInstance = defaultInstance;
	}
}
