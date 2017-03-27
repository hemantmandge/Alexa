package com.ge.code.generate.request.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ge.code.generate.request.repository.entity.RequestHistory;
import com.ge.code.generate.request.service.CodeGenRequestService;
import com.ge.code.generate.request.value.object.CodeGenRequest;

@CrossOrigin
@RestController
@RequestMapping("/codeGenRequests")
public class CodeGenRequestController {
	
	@Autowired
	private CodeGenRequestService codeGenRequestService;
	
	@RequestMapping("/requests")
	public List<RequestHistory> getAllCodeGenRequests(String sourceType, String sourceSystem, String dbConnection, String dbName, String loadType, @RequestParam("fromDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date fromDate, @RequestParam("toDate") @DateTimeFormat(pattern="yyyy-MM-dd")  Date toDate) {
		return codeGenRequestService.getAllCodeGenRequests(sourceType, sourceSystem, dbConnection, dbName, loadType, fromDate, toDate);
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/create")
	public void create(@RequestBody CodeGenRequest codeGenRequest){
		codeGenRequestService.create(codeGenRequest);
	}
	
	@RequestMapping("/generateReport")
	public void generateReport(HttpServletRequest request, HttpServletResponse response, String sourceType, String sourceSystem, String dbConnection, String dbName, String loadType, @RequestParam("fromDate") @DateTimeFormat(pattern="yyyy-MM-dd") Date fromDate, @RequestParam("toDate") @DateTimeFormat(pattern="yyyy-MM-dd")  Date toDate) throws FileNotFoundException, IOException {
		List<RequestHistory> requestHistories =  codeGenRequestService.getAllCodeGenRequests(sourceType, sourceSystem, dbConnection, dbName, loadType, fromDate, toDate);
		Workbook workbook = new XSSFWorkbook();
	    Sheet sheet = workbook.createSheet();
	    createHeaderRow(sheet);
	    int rowCount = 0;
	 
	    for (RequestHistory requestHistory : requestHistories) {
	        Row row = sheet.createRow(++rowCount);
	        writeBook(requestHistory, row);
	    }
	 
	    response.setContentType("application/vnd.ms-excel");
        response.addHeader("Content-Disposition", "attachment; filename="+"Job Request Report.xlsx");
        workbook.write( response.getOutputStream());
        response.getOutputStream().flush();
        workbook.close();
	}
	
	private void writeBook(RequestHistory requestHistory, Row row) {
	    Cell cell = row.createCell(1);
	    cell.setCellValue(requestHistory.getRequestId());
	 
	    cell = row.createCell(2);
	    cell.setCellValue(requestHistory.getSourceType());
	 
	    cell = row.createCell(3);
	    cell.setCellValue(requestHistory.getSourceSystem());
	    
	    cell = row.createCell(4);
	    cell.setCellValue(requestHistory.getDbConnection());
	    
	    cell = row.createCell(5);
	    cell.setCellValue(requestHistory.getDbName());
	    
	    cell = row.createCell(6);
	    cell.setCellValue(requestHistory.getSource());
	    
	    cell = row.createCell(7);
	    cell.setCellValue(requestHistory.getSourceTableName());
	    
	    cell = row.createCell(8);
	    cell.setCellValue(requestHistory.getLoadType());
	    
	    cell = row.createCell(9);
	    cell.setCellValue(requestHistory.getTargetConnection());
	    
	    cell = row.createCell(10);
	    cell.setCellValue(requestHistory.getTargetDBName());
	    
	    cell = row.createCell(11);
	    cell.setCellValue(requestHistory.getTargetTableName());
	    
	    cell = row.createCell(12);
	    cell.setCellValue(requestHistory.getTargetTableType());
	    
	    cell = row.createCell(13);
	    cell.setCellValue(requestHistory.getTargetPartitionKey());
	    
	    cell = row.createCell(14);
	    cell.setCellValue(requestHistory.getCreateTimeStamp());
	    
	    cell = row.createCell(15);
	    cell.setCellValue(requestHistory.getSourceColumnName());
	    
	    cell = row.createCell(16);
	    cell.setCellValue(requestHistory.getCalculateDeltaOn());
	    
	    cell = row.createCell(17);
	    cell.setCellValue(requestHistory.getJoinKey());
	    
	    cell = row.createCell(18);
	    cell.setCellValue(requestHistory.getWhereCondition());
	    
	    cell = row.createCell(19);
	    cell.setCellValue(requestHistory.getSourceDirectory());
	    
	    cell = row.createCell(20);
	    cell.setCellValue(requestHistory.getFillerThree());
	    
	    cell = row.createCell(21);
	    cell.setCellValue(requestHistory.getArchivePeriod());
	    
	    cell = row.createCell(22);
	    cell.setCellValue(requestHistory.getFillerOne());
	    
	    cell = row.createCell(23);
	    cell.setCellValue(requestHistory.getFillerThree());
	}
	
	private void createHeaderRow(Sheet sheet) {
		 
	    CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
	    Font font = sheet.getWorkbook().createFont();
	    font.setBold(true);
	    font.setFontHeightInPoints((short) 16);
	    cellStyle.setFont(font);
	 
	    Row headerRow = sheet.createRow(0);
	    
	    Cell requestIdCell = headerRow.createCell(1);
	    requestIdCell.setCellStyle(cellStyle);
	    requestIdCell.setCellValue("REQUEST ID");
	 
	    Cell dataSourceCell = headerRow.createCell(2);
	    dataSourceCell.setCellStyle(cellStyle);
	    dataSourceCell.setCellValue("DATASOURCE");
	 
	    Cell dataSourceTypeCell = headerRow.createCell(3);
	    dataSourceTypeCell.setCellStyle(cellStyle);
	    dataSourceTypeCell.setCellValue("DATASOURCE TYPE");
	    
	    Cell sourceServerCell = headerRow.createCell(4);
	    sourceServerCell.setCellStyle(cellStyle);
	    sourceServerCell.setCellValue("SOURCE SERVER");
	    
	    Cell sourceDbNameCell = headerRow.createCell(5);
	    sourceDbNameCell.setCellStyle(cellStyle);
	    sourceDbNameCell.setCellValue("SOURCE DB NAME / SID");
	    
	    Cell sourceDBSchemaCell = headerRow.createCell(6);
	    sourceDBSchemaCell.setCellStyle(cellStyle);
	    sourceDBSchemaCell.setCellValue("SOURCE DB SCHEMA");
	    
	    Cell sourceTableCell = headerRow.createCell(7);
	    sourceTableCell.setCellStyle(cellStyle);
	    sourceTableCell.setCellValue("SOURCE TABLE");
	    
	    Cell loadTypeCell = headerRow.createCell(8);
	    loadTypeCell.setCellStyle(cellStyle);
	    loadTypeCell.setCellValue("LOAD TYPE");
	    
	    Cell targetServerCell = headerRow.createCell(9);
	    targetServerCell.setCellStyle(cellStyle);
	    targetServerCell.setCellValue("TARGET SERVER");
	    
	    Cell hiveDBCell = headerRow.createCell(10);
	    hiveDBCell.setCellStyle(cellStyle);
	    hiveDBCell.setCellValue("HIVE DB");
	    
	    Cell hiveTableCell = headerRow.createCell(11);
	    hiveTableCell.setCellStyle(cellStyle);
	    hiveTableCell.setCellValue("HIVE TABLE");
	    
	    Cell hiveTableTypeCell = headerRow.createCell(12);
	    hiveTableTypeCell.setCellStyle(cellStyle);
	    hiveTableTypeCell.setCellValue("HIVE TABLE TYPE");
	    
	    Cell hivePartitionKeyCell = headerRow.createCell(13);
	    hivePartitionKeyCell.setCellStyle(cellStyle);
	    hivePartitionKeyCell.setCellValue("HIVE PARTITION KEY");
	    
	    Cell submittedOnCell = headerRow.createCell(14);
	    submittedOnCell.setCellStyle(cellStyle);
	    submittedOnCell.setCellValue("SUBMITTED ON");
	    
	    Cell sourceColumnCell = headerRow.createCell(15);
	    sourceColumnCell.setCellStyle(cellStyle);
	    sourceColumnCell.setCellValue("SOURCE COLUMNS");
	    
	    Cell calculateDeltaOncell = headerRow.createCell(16);
	    calculateDeltaOncell.setCellStyle(cellStyle);
	    calculateDeltaOncell.setCellValue("CALCULATE DELTA ON");
	    
	    Cell uniqueKeyCell = headerRow.createCell(17);
	    uniqueKeyCell.setCellStyle(cellStyle);
	    uniqueKeyCell.setCellValue("UNIQUE KEY");
	    
	    Cell whereConditionCell = headerRow.createCell(18);
	    whereConditionCell.setCellStyle(cellStyle);
	    whereConditionCell.setCellValue("WHERE CONDITION");
	    
	    Cell sourceDirectoryCell = headerRow.createCell(19);
	    sourceDirectoryCell.setCellStyle(cellStyle);
	    sourceDirectoryCell.setCellValue("SOURCE DIRECTORY");
	    
	    Cell sourceFileSchemaPath = headerRow.createCell(20);
	    sourceFileSchemaPath.setCellStyle(cellStyle);
	    sourceFileSchemaPath.setCellValue("SOURCE FILE SCHEMA PATH");
	    
	    Cell cell = headerRow.createCell(21);
	    cell.setCellStyle(cellStyle);
	    cell.setCellValue("SOURCE SERVER");
	    
	    Cell archivalPeriodCell = headerRow.createCell(22);
	    archivalPeriodCell.setCellStyle(cellStyle);
	    archivalPeriodCell.setCellValue("ARCHIVE PERIOD");
	    
	    Cell fileDelimiterCell = headerRow.createCell(23);
	    fileDelimiterCell.setCellStyle(cellStyle);
	    fileDelimiterCell.setCellValue("FILE DELIMITER");
	    
	    Cell rowTagCell = headerRow.createCell(23);
	    rowTagCell.setCellStyle(cellStyle);
	    rowTagCell.setCellValue("ROW TAG");
	    
	}
}
