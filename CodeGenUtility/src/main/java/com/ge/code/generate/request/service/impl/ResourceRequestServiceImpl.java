package com.ge.code.generate.request.service.impl;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
		ResultSet schemas;
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
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return schemaList;
	}
	
	@Override
	public List<String> getTables(String userid, String password, String host, String database, String rdbmsName, String schema) {
		Connection connection = getConnection(userid, password, host, database, rdbmsName);
		ResultSet tables;
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
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return tableList;
	}

	@Override
	public List<String> getColumns(String userid, String password, String host, String database, String rdbmsName,
			String tableName) {
		Connection connection = getConnection(userid, password, host, database, rdbmsName);
		ResultSet columns;
		List<String> columnsList = new ArrayList<String>();

		String   catalog           = null;
		String   schemaPattern     = null;
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
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return columnsList;
	}
	
	private Connection getConnection (String userid, String password, String host, String database,
			String rdbmsName) {


		System.out.println("-------- Oracle JDBC Connection Testing ------");

		try {

			Class.forName("oracle.jdbc.driver.OracleDriver");

		} catch (ClassNotFoundException e) {

			System.out.println("Where is your Oracle JDBC Driver?");
			e.printStackTrace();
		}

		System.out.println("Oracle JDBC Driver Registered!");

		Connection connection = null;

		try {

			//connection = DriverManager.getConnection("jdbc:oracle:thin:@10.76.177.167:1521:xe", "admin", "gepoc ");
			connection = DriverManager.getConnection("jdbc:oracle:thin:@" + host + ":" + database, userid, password);

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
