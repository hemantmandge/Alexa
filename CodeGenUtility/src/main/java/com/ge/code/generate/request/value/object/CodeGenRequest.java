package com.ge.code.generate.request.value.object;

import java.util.List;

public class CodeGenRequest {
	/**
	 * to store the selected source type
	 */
	private String sourceSystem;
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
	private String targetTableName;
	private String targetConnection;
	private String hiveTableType;

	private String targetPartitionKey;
	/**
	 * Value objects for file data source
	 */
	private String fileType;
	private String filePath;
	private String rowTag;
	private String serverIP;
	private String fileDelimeter;
	private String fileSchemaPath;
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
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
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
	public List<String> getSourceTableNames() {
		return sourceTableNames;
	}
	public void setSourceTableNames(List<String> sourceTableNames) {
		this.sourceTableNames = sourceTableNames;
	}
	public List<String> getSourceColumnNames() {
		return sourceColumnNames;
	}
	public void setSourceColumnNames(List<String> sourceColumnNames) {
		this.sourceColumnNames = sourceColumnNames;
	}
	public String getCalculateDeltaOn() {
		return calculateDeltaOn;
	}
	public void setCalculateDeltaOn(String calculateDeltaOn) {
		this.calculateDeltaOn = calculateDeltaOn;
	}
	public List<String> getJoinKeys() {
		return joinKeys;
	}
	public void setJoinKeys(List<String> joinKeys) {
		this.joinKeys = joinKeys;
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
	public String getTargetType() {
		return targetType;
	}
	public void setTargetType(String targetType) {
		this.targetType = targetType;
	}
	public String getLoadType() {
		return loadType;
	}
	public void setLoadType(String loadType) {
		this.loadType = loadType;
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
	public String getTargetConnection() {
		return targetConnection;
	}
	public void setTargetConnection(String targetConnection) {
		this.targetConnection = targetConnection;
	}
	public String getHiveTableType() {
		return hiveTableType;
	}
	public void setHiveTableType(String hiveTableType) {
		this.hiveTableType = hiveTableType;
	}
	public String getTargetPartitionKey() {
		return targetPartitionKey;
	}
	public void setTargetPartitionKey(String targetPartitionKey) {
		this.targetPartitionKey = targetPartitionKey;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getRowTag() {
		return rowTag;
	}
	public void setRowTag(String rowTag) {
		this.rowTag = rowTag;
	}
	public String getServerIp() {
		return serverIP;
	}
	public void setServerIP(String serverIP) {
		this.serverIP = serverIP;
	}
	public String getFileDelimeter() {
		return fileDelimeter;
	}
	public void setFileDelimeter(String fileDelimeter) {
		this.fileDelimeter = fileDelimeter;
	}
	public String getFileSchemaPath() {
		return fileSchemaPath;
	}
	public void setFileSchemaPath(String fileSchemaPath) {
		this.fileSchemaPath = fileSchemaPath;
	}
}