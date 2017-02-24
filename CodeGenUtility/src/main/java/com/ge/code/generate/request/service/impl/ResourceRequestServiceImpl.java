package com.ge.code.generate.request.service.impl;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.dbutils.DbUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ge.code.generate.request.controller.ConstantUtils;
import com.ge.code.generate.request.repository.ListOfValuesRepository;
import com.ge.code.generate.request.repository.entity.ListOfValues;
import com.ge.code.generate.request.service.ResourceRequestService;

@Service
public class ResourceRequestServiceImpl implements ResourceRequestService {
	@Autowired
	ListOfValuesRepository listOfValuesRepository;

	@Override
	public List<ListOfValues> findByType(String type) {
		List<ListOfValues> listOfValues = new ArrayList<ListOfValues>();
		listOfValuesRepository.findByType(type).forEach(listOfValues :: add);
		return listOfValues;
	}

	@Override
	public List<ListOfValues> findByName(String name) {
		List<ListOfValues> listOfValues = new ArrayList<ListOfValues>();
		listOfValuesRepository.findByName(name).forEach(listOfValues :: add);
		return listOfValues;
	}

	@Override
	public List<ListOfValues> findByTypeAndName(String type, String name) {
		List<ListOfValues> listOfValues = new ArrayList<ListOfValues>();
		listOfValuesRepository.findByTypeAndName(type, name).forEach(listOfValues :: add);
		return listOfValues;
	}

	@Override
	public List<String> getSchemaDetails(String userid, String password, String host, String database,
			String rdbmsName) {
		Connection connection = getConnection(userid, password, host, database, rdbmsName);
		ResultSet schemas = null;
		List<String> schemaList = new ArrayList<String>();
		try {
			DatabaseMetaData metaData = connection.getMetaData();
			schemas = metaData.getSchemas();
			while (schemas.next()) {
				String tableSchema = schemas.getString(1);    // "TABLE_SCHEM"
				String tableCatalog = schemas.getString(2); //"TABLE_CATALOG"
				System.out.println("tableSchema = "+tableSchema);
				schemaList.add(tableSchema);
				System.out.println("tableCatalog = "+tableCatalog);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(connection);
			DbUtils.closeQuietly(schemas);
			System.out.println("Closing Connection!!!");
		}
		return schemaList;
	}
	
	@Override
	public List<String> getTables(String userid, String password, String host, String database, String rdbmsName, String schema) {
		Connection connection = getConnection(userid, password, host, database, rdbmsName);
		ResultSet tables = null;
		List<String> tableList = new ArrayList<String>();

		String   catalog          = null;
		String   tableNamePattern = null;
		String   schemaPattern     = schema;
		String[] types            = null;
		
		try {
			DatabaseMetaData metaData = connection.getMetaData();
			tables = metaData.getTables(catalog, schemaPattern, tableNamePattern, types);
			while (tables.next()) {
				String tableName = tables.getString(3);    // "TABLE_SCHEM"
				System.out.println("tableSchema = "+tableName);
				tableList.add(tableName);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(connection);
			DbUtils.closeQuietly(tables);
			System.out.println("Closing Connection!!!");
		}
		return tableList;
	}

	@Override
	public List<String> getColumns(String userid, String password, String host, String database, String rdbmsName,
			String tableName, String schema) {
		Connection connection = getConnection(userid, password, host, database, rdbmsName);
		ResultSet columns = null;
		List<String> columnsList = new ArrayList<String>();

		String   catalog           = null;
		String   schemaPattern     = schema;
		String   tableNamePattern  = tableName;
		String   columnNamePattern = null;
		
		try {
			DatabaseMetaData metaData = connection.getMetaData();
			columns = metaData.getColumns(catalog, schemaPattern,  tableNamePattern, columnNamePattern);
			while (columns.next()) {
				String columnName = columns.getString(4);    // "TABLE_SCHEM"
				System.out.println("tableSchema = "+columnName);
				columnsList.add(columnName);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(connection);
			DbUtils.closeQuietly(columns);
			System.out.println("Closing Connection!!!");
		}
		return columnsList;
	}
	
	@Override
	public List<String> getPrimaryKey(String userid, String password, String host, String database, String rdbmsName,
			String tableName, String schema) {
		Connection connection = getConnection(userid, password, host, database, rdbmsName);
		ResultSet primaryKey = null;
		List<String> primarykeyList = new ArrayList<String>();

		String   catalog           = null;
		String   schemaPattern     = schema;
		String   tableNamePattern  = tableName;
		
		try {
			DatabaseMetaData metaData = connection.getMetaData();
			primaryKey = metaData.getPrimaryKeys(catalog, schemaPattern, tableNamePattern);
			while (primaryKey.next()) {
				String columnName = primaryKey.getString("COLUMN_NAME");    // "COLUMN_NAME"
				System.out.println("tableSchema = "+columnName);
				primarykeyList.add(columnName);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			DbUtils.closeQuietly(connection);
			DbUtils.closeQuietly(primaryKey);
			System.out.println("Closing Connection!!!");
		}
		return primarykeyList;
	}
	
	private Connection getConnection (String userid, String password, String host, String database,
			String rdbmsName) {


		System.out.println("-------- JDBC Connection Testing ------");

		try {
			if (rdbmsName.equalsIgnoreCase(ConstantUtils.ORACLE)) {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			} else if (rdbmsName.equalsIgnoreCase(ConstantUtils.MSSQL)) {
				Class.forName("com.microsoft.sqlserver.jdbc.SqlServerDriver");
			} else if (rdbmsName.equalsIgnoreCase(ConstantUtils.TERADATA)) {
				Class.forName("com.teradata.jdbc.TeraDriver");
			} else if (rdbmsName.equalsIgnoreCase(ConstantUtils.GREENPLUM)) {
				Class.forName("oracle.jdbc.driver.OracleDriver");
			}  
		} catch (ClassNotFoundException e) {

			System.out.println("JDBC Driver ?");
			e.printStackTrace();
		}

		System.out.println("Oracle JDBC Driver Registered!");

		Connection connection = null;

		try {
			if (rdbmsName.equalsIgnoreCase(ConstantUtils.ORACLE)) {
				connection = DriverManager.getConnection("jdbc:oracle:thin:@" + host + ":" + database, userid, password);
			} else if (rdbmsName.equalsIgnoreCase(ConstantUtils.MSSQL)) {
				connection = DriverManager.getConnection("jdbc:sqlserver://" + host + ";databaseName=" + database, userid, password);
			} else if (rdbmsName.equalsIgnoreCase(ConstantUtils.TERADATA)) {
				connection = DriverManager.getConnection("jdbc:teradata://" + host, userid, password);
			} else if (rdbmsName.equalsIgnoreCase(ConstantUtils.GREENPLUM)) {
				connection = DriverManager.getConnection("jdbc:oracle:thin:@" + host + ":" + database, userid, password);
			} 
		} catch (SQLException e) {

			System.out.println("Connection Failed! Check output console");
			e.printStackTrace();
		}

		if (connection != null) {
			System.out.println("You made it, take control your database now!");
		} else {
			System.out.println("Failed to make connection!");
		}
		return connection;
	}
}
