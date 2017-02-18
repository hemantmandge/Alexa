package com.ge.code.generate.request.value.object;

import java.util.List;

public class CodeGenRequest {
	/**
	 * to store the selected source type
	 */
	private String sourceType;
	/**
	 * to store the DB connection
	 */
	private String dbConnection;
	private String username;
	private String password;
	/**
	 * to store DB name/SID
	 */
	private String dbName;

	/**
	 * to store DBschema name
	 */
	private String source;
	private List<String> sourceTableNames;
	private List<String> sourceColumnNames;
	private String calculateDeltaOn;
	/**
	 * to store Unique Key
	 */
	private List<String> joinKeys;

	private String whereCondition;
	private String archivePeriod;
	/**
	 * to store target Type
	 */
	private String targetType;
	private String loadType;
	/**
	 * // hive DB Name
	 */
	private String targetDBName;
	/**
	 * hive table name
	 */
	private String targetTableNames;
	private String targetConnection;
	private String hiveTableType;

	private String targetPartitionKey;


	/**
	 * @return the joinKeys
	 */
	public List<String> getJoinKeys() {
		return joinKeys;
	}

	/**
	 * @param joinKeys
	 *            the joinKeys to set
	 */
	public void setJoinKeys(List<String> joinKeys) {
		this.joinKeys = joinKeys;
	}

	/**
	 * @return the sourceType
	 */
	public String getSourceType() {
		return sourceType;
	}

	/**
	 * @param sourceType
	 *            the sourceType to set
	 */
	public void setSourceType(String sourceType) {
		this.sourceType = sourceType;
	}

	/**
	 * @return the targetDBName
	 */
	public String getTargetDBName() {
		return targetDBName;
	}

	/**
	 * @param targetDBName
	 *            the targetDBName to set
	 */
	public void setTargetDBName(String targetDBName) {
		this.targetDBName = targetDBName;
	}

	/**
	 * @return the sourceTableNames
	 */
	public List<String> getSourceTableNames() {
		return sourceTableNames;
	}

	/**
	 * @param sourceTableNames
	 *            the sourceTableNames to set
	 */
	public void setSourceTableNames(List<String> sourceTableNames) {
		this.sourceTableNames = sourceTableNames;
	}

	/**
	 * @return the sourceColumnNames
	 */
	public List<String> getSourceColumnNames() {
		return sourceColumnNames;
	}

	/**
	 * @param sourceColumnNames
	 *            the sourceColumnNames to set
	 */
	public void setSourceColumnNames(List<String> sourceColumnNames) {
		this.sourceColumnNames = sourceColumnNames;
	}

	/**
	 * @return the source
	 */
	public String getSource() {
		return source;
	}

	/**
	 * @param source
	 *            the source to set
	 */
	public void setSource(String source) {
		this.source = source;
	}

	

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the dbConnection
	 */
	public String getDbConnection() {
		return dbConnection;
	}

	/**
	 * @param dbConnection
	 *            the dbConnection to set
	 */
	public void setDbConnection(String dbConnection) {
		this.dbConnection = dbConnection;
	}

	
	/**
	 * @return the dbName
	 */
	public String getDbName() {
		return dbName;
	}

	/**
	 * @param dbName the dbName to set
	 */
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}

	/**
	 * @return the calcDeltaOn
	 */
	public String getCalculateDeltaOn() {
		return calculateDeltaOn;
	}

	/**
	 * @param calcDeltaOn
	 *            the calcDeltaOn to set
	 */
	public void setCalculateDeltaOn(String calcDeltaOn) {
		this.calculateDeltaOn = calcDeltaOn;
	}

	/**
	 * @return the loadType
	 */
	public String getLoadType() {
		return loadType;
	}

	/**
	 * @param loadType
	 *            the loadType to set
	 */
	public void setLoadType(String loadType) {
		this.loadType = loadType;
	}

	/**
	 * @return the partitionKey
	 */

	/**
	 * @return the hiveTableType
	 */
	public String getHiveTableType() {
		return hiveTableType;
	}

	/**
	 * @return the targetPartitionKey
	 */
	public String getTargetPartitionKey() {
		return targetPartitionKey;
	}

	/**
	 * @param targetPartitionKey
	 *            the targetPartitionKey to set
	 */
	public void setTargetPartitionKey(String targetPartitionKey) {
		this.targetPartitionKey = targetPartitionKey;
	}

	/**
	 * @param hiveTableType
	 *            the hiveTableType to set
	 */
	public void setHiveTableType(String hiveTableType) {
		this.hiveTableType = hiveTableType;
	}

	/**
	 * @return the whereCondition
	 */
	public String getWhereCondition() {
		return whereCondition;
	}

	/**
	 * @param whereCondition
	 *            the whereCondition to set
	 */
	public void setWhereCondition(String whereCondition) {
		this.whereCondition = whereCondition;
	}

	/**
	 * @return the archivePeriod
	 */
	public String getArchivePeriod() {
		return archivePeriod;
	}

	/**
	 * @param archivePeriod
	 *            the archivePeriod to set
	 */
	public void setArchivePeriod(String archivePeriod) {
		this.archivePeriod = archivePeriod;
	}

	/**
	 * @return the targetType
	 */
	public String getTargetType() {
		return targetType;
	}

	/**
	 * @param targetType
	 *            the targetType to set
	 */
	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}

	/**
	 * @return the targetConnection
	 */
	public String getTargetConnection() {
		return targetConnection;
	}

	/**
	 * @param targetConnection
	 *            the targetConnection to set
	 */
	public void setTargetConnection(String targetConnection) {
		this.targetConnection = targetConnection;
	}

	/**
	 * @return the targetTableName
	 */
	public String getTargetTableNames() {
		return targetTableNames;
	}

	/**
	 * @param targetTableName
	 *            the targetTableName to set
	 */
	public void setTargetTableNames(String targetTableNames) {
		this.targetTableNames = targetTableNames;
	}
}
