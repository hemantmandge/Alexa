package com.ge.code.generate.request.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ge.code.generate.request.repository.entity.ListOfValues;
import com.ge.code.generate.request.service.ResourceRequestService;

@CrossOrigin
@RestController
@RequestMapping("/resources")
public class ResourceRequestController {

	@Autowired
	private ResourceRequestService resourceRequestService;
	
	@RequestMapping("/jar")
    public void downloadBuild(HttpServletRequest request, HttpServletResponse response) {
        //If user is not authorized - he should be thrown out from here itself
         
        //Authorized user will download the file
        String dataDirectory = "C:/Users/hemant.mandge/workspace/CodeGenUtility/target";
        Path file = Paths.get(dataDirectory, "CodeGenUtility-0.0.1-SNAPSHOT.jar");
        if (Files.exists(file)) 
        {
            response.setContentType("application/pdf");
            response.addHeader("Content-Disposition", "attachment; filename="+"CodeGenUtility.jar");
            try
            {
                Files.copy(file, response.getOutputStream());
                response.getOutputStream().flush();
            } 
            catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
	@RequestMapping("/user")
	public Principal user(Principal user) {
		return user;
	}
	
	@RequestMapping("/refreshSession")
	public void refreshSession(HttpSession session) {
		System.out.println(session.getCreationTime());
	}

	@RequestMapping("/findByType")
	public List<ListOfValues> findByType(@RequestParam("type") String type) {
		return resourceRequestService.findByType(type);
	}

	@RequestMapping("/findByName")
	public List<ListOfValues> findByName(@RequestParam("name") String name) {
		return resourceRequestService.findByName(name);
	}

	@RequestMapping("/findByTypeAndName")
	public List<ListOfValues> findByTypeAndName(@RequestParam("type") String type, @RequestParam("name") String name) {
		return resourceRequestService.findByTypeAndName(type, name);
	}

	@RequestMapping("/getSchemaDetails")
	public List<String> getSchemaDetails(@RequestParam("userid") String userid,
			@RequestParam("password") String password, @RequestParam("host") String host,
			@RequestParam("database") String database, @RequestParam("rdbmsName") String rdbmsName) {
		return resourceRequestService.getSchemaDetails(userid, password, host, database, rdbmsName);
	}

	@RequestMapping("/getTables")
	public List<String> getTables(@RequestParam("userid") String userid, @RequestParam("password") String password,
			@RequestParam("host") String host, @RequestParam("database") String database,
			@RequestParam("rdbmsName") String rdbmsName, @RequestParam("schema") String schema) {
		return resourceRequestService.getTables(userid, password, host, database, rdbmsName, schema);
	}

	@RequestMapping("/getColumns")
	public List<String> getColumns(@RequestParam("userid") String userid, @RequestParam("password") String password,
			@RequestParam("host") String host, @RequestParam("database") String database,
			@RequestParam("rdbmsName") String rdbmsName, @RequestParam("tableName") String tableName,
			@RequestParam("schema") String schema) {
		return resourceRequestService.getColumns(userid, password, host, database, rdbmsName, tableName, schema);
	}
	/*
	 * @RequestMapping("/getRequestLoadType") public String getRequestLoadType()
	 * { return codeGenRequestService.getAllCodeGenRequests(); }
	 * 
	 * @RequestMapping("/getHiveTableType") public String getHiveTableType() {
	 * return codeGenRequestService.getAllCodeGenRequests(); }
	 * 
	 * @RequestMapping("/getSFTPSourceDetails") public String
	 * getSFTPSourceDetails() { return
	 * codeGenRequestService.getAllCodeGenRequests(); }
	 * 
	 * @RequestMapping("/getFileType") public String getFileType() { return
	 * codeGenRequestService.getAllCodeGenRequests(); }
	 */
}
