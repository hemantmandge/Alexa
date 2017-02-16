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
		if (rdbmsName.equalsIgnoreCase("ORACLE")) {
			Connection connection = getConnection();
			ResultSet schemas;
			try {
				DatabaseMetaData metaData = connection.getMetaData();
				schemas = metaData.getSchemas();
				while (schemas.next()) {
					String tableSchema = schemas.getString(1);    // "TABLE_SCHEM"
					String tableCatalog = schemas.getString(2); //"TABLE_CATALOG"
					System.out.println("tableSchema = "+tableSchema);
					System.out.println("tableCatalog = "+tableCatalog);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}
	
	private Connection getConnection () {


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

			connection = DriverManager.getConnection("jdbc:oracle:thin:@10.76.177.167:1521:xe", "admin", "gepoc ");

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
