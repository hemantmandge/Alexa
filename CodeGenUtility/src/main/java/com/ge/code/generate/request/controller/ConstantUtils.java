package com.ge.code.generate.request.controller;

public interface ConstantUtils {
	String ORACLE = "ORACLE";
	String LOAD_TYPE_FULL_LOAD = "FULL LOAD";
	String LOAD_TYPE_INCREMENTAL_LOAD = "INCREMENTAL LOAD";
	String LOAD_TYPE_OVERWRITE = "OVERWRITE";
	String LOAD_TYPE_APPEND = "APPEND";
	String REFRESH_TYPE_FULL = "FULL";
	String REFRESH_TYPE_PARTIAL = "PARTIAL";
	String DATACODEGEN_BASE = "datacodegen/base";
	String ORACLE_TEMPLATE = "Oracle_Template";
	String ORACLE_SCRIPT_NAME = "merge.pig";
	String ORACLE_PARAMETER_FILE_LOCATION = "param_generic.txt";
	String UNIX_TIME_STAMP = "1483228800";
	String DEFAULT_DATE = "2017-01-01 00:00:00";
	String DATE_FORMAT = "yyyy.MM.dd HH:mm:ss";
}
