app.factory('ResourceService',function($http) {
	
		var resources ={};
		
		resources.findByName =  function(resourceName){
			return $http.get("resources/findByName?name=" + resourceName);
		};
		
		resources.findByType =  function(resourceType){
			return $http.get("resources/findByType?type=" + resourceType);
		};
		
		resources.findByTypeAndName =  function(resourceType, resourceName){
			return $http.get("resources/findByTypeAndName?type=" + resourceType + "&name=" + resourceName);
		};
		
		resources.createCodeGenRequest = function(requestData) {
			return $http.post('codeGenRequests/create', angular.fromJson(requestData));
		};
		
		resources.getSchemaDetails = function( username , password, host , dbName,rdbmsName) {
			return $http.get("resources/getSchemaDetails?userid=" + username + "&password=" + password + "&host=" + host + "&database=" + dbName + "&rdbmsName=" + rdbmsName );
		};
		
		resources.getTables = function(username,password,host , database , rdbmsName , dbschema )
		{ 
			return  $http.get("resources/getTables?userid=" + username + "&password=" + password + "&host=" + host + "&database=" + database + "&rdbmsName=" + rdbmsName + "&schema=" + dbschema )
		};
		
		resources.getColumns = function(username, password , host , database, rdbmsName, tableName , dbschema) {
			   return $http.get("/resources/getColumns?userid=" + username + "&password=" + password + "&host=" + host + "&database=" + database + "&rdbmsName=" + rdbmsName + "&tableName=" + tableName + "&schema=" + dbschema);
		}
		
		resources.getJobRequests = function(sourceType,sourceSystem,dbConnection,dbname,loadType,fromDate,toDate,page,size) {
			return $http.get("codeGenRequests/requests?sourceType="+sourceType+"&sourceSystem="+sourceSystem+"&dbConnection="+dbConnection+"&dbName="+dbname+"&loadType="+loadType+"&fromDate="+fromDate+"&toDate="+toDate+"&page="+page+"&size="+size)
		}
		
		return resources;
});
