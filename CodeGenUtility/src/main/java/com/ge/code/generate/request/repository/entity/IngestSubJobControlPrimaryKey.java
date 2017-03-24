package com.ge.code.generate.request.repository.entity;


import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class IngestSubJobControlPrimaryKey implements Serializable{
	@Column(name = "TARGET",  nullable = false)
	private String targetDBName;
	@Column(name = "TGT_TBL_N", nullable = false)
	private String targetTableName;
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
	
}
