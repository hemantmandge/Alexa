angular.module('utilityApp').controller('FileController', 
		function($scope, $rootScope, $http, $uibModal,$location, ResourceService, $controller,$cookies) {
	
	var UtilityController = $scope.$new(); 
	$controller('utilityCtrl',{$scope : UtilityController });
	$scope.file = {};
	$scope.joinKeysList = [];
	 $scope.disabled = false;
	//variables for file 
	$scope.filePath=true;
	$scope.fileSchemaPath=true;
	$scope.hivepartionkeydisabled=false;
	//$scope.fileType =false;
	$scope.serverIP=true;
	$scope.rowTag=true;
	$scope.fileDelimeter=true;
	$scope.calculateDeltaOn=true;
	$scope.joinKeys=true;
	$scope.archivePeriod =true; 
	

	//variables for required check 
	$scope.fileTypeRequired=true;
	$scope.filePathRequired=false;
	$scope.fileSchemaPathRequired=false;
	$scope.rowTagRequired=false;
	$scope.fileDelimeterRequired=false;
	$scope.joinKeysRequired=false;
	$scope.archivePeriodRequired =false; 
	$scope.hivepartionkeyrequired=false;
	
//	$scope.toggleFileResources = false;
	/**
	 * calling web services for File Data source Start
	 */
	$scope.pageLoadDataFile = function() {
        if ($rootScope.isAuthenticated) { // if user is authenticated and $rootScope.isAuthenticated is not undefined call loadRDBMSData
        	$rootScope.homeRadioBtn = "Files";
        	$scope.loadFileInitialData();
            
	    } 
        else { // if $rootScope.isAuthenticated is undefined (It may required to have explicit check for undefined)
        	if($cookies.getObject('userName'))
        	{
        		$rootScope.isAuthenticated = true;
        		$rootScope.homeRadioBtn = "Files";
  		      	$location.path("/Files");
        		$scope.loadFileInitialData();
        	}
        	else
        	{
        		$rootScope.isAuthenticated = false;
  		      	$location.path("/");
        	}
	    }
	};
	
	$scope.loadFileInitialData = function() {
	
		$rootScope.homeRadioBtn = "Files";
		$scope.file.sourceType = "FILE";
		$scope.file.loadType = "FULL";
	
		ResourceService.findByType("FILETYPE").then(function(response) {
			$scope.fileType = response.data;
		});
		ResourceService.findByName("SFTP").then(function(response) {
			$scope.SFTPserverIPList = response.data;
		});
		ResourceService.findByType("LOADTYPE").then(function(response) {
			$scope.loadType = response.data;
		});
		
		ResourceService.findByType("HIVETABLETYPE").then(function(response) {
			 $scope.hiveTableType = response.data;
		});
		
		ResourceService.findByType("TARGETCONNECTION").then(
				function(response) {
					$scope.targetcon = response.data;
				});
	
	};
	/** 
	 * THIS FUNCTION IS TO SUBMIT THE FILE DATA 
	 */
	$scope.createFileCodeGenRequest = function(requestData) {
		if( $scope.file.fileForm.$invalid){
			$scope.showValidationErrors = true; 
			return false;
		}
		$rootScope.dataLoading = true;
		$rootScope.loadingBackgronud = true;
		$scope.submitted = true;
   
		var data = requestData;
		$scope.joinKeysList.push(data.joinKeys);
		data.joinKeys = $scope.joinKeysList;
		ResourceService.createCodeGenRequest(data)
			.then(UtilityController.createCodeGenRequestSuccess, UtilityController.createCodeGenRequestFailed)
			.catch(UtilityController.catchCreateCodeGenRequestError);
	};
	
	 $scope.onCancelFile = function() {
		  $scope.file={};
		  $scope.file.sourceType="FILE";

		 }
	
	$scope.changeTableType=function(hiveTableType)
	{
		console.log(hiveTableType);
		if  (hiveTableType == 'NON-PARTITIONED')
			{
			$scope.hivepartionkeydisabled=true;
			$scope.file.targetPartitionKey="";
			$scope.hivepartionkeyrequired=false;
			}
		else
			{
			$scope.hivepartionkeydisabled=false;
			$scope.hivepartionkeyrequired=true;
			}
	}
	
	$scope.onChangeOfFileType=function()
	{
		if($scope.file.fileType== undefined || $scope.file.fileType== null || $scope.file.fileType == '')
			{
			$scope.fileTypeRequired=true;
			}
		else
			{
			$scope.fileTypeRequired=false;
			}
	
		if($scope.file.fileType=="XML")
			{
			$scope.filePath=true;
			$scope.fileSchemaPath=false;
			$scope.serverIP=true;
			$scope.rowTag=false;
			$scope.fileDelimeter=true;
			$scope.calculateDeltaOn=true;
			$scope.joinKeys=true;
			$scope.archivePeriod =true; 
			
			//set the variables to blank todo: for server sftp
			  $scope.file.filePath="" ;
			  $scope.file.fileDelimeter="";
			  $scope.file.calculateDeltaOn="" ;
			  $scope.file.joinKeys=""; 
			  $scope.file.SFTPserverIp="";
			//  $scope.file.rowTag  ="";
			  $scope.file.archivePeriod="";
			
			//required
			
			$scope.filePathRequired=false;
			$scope.fileSchemaPathRequired=true;
			$scope.rowTagRequired=true;
			$scope.fileDelimeterRequired=false;
			$scope.joinKeysRequired=false;
			$scope.archivePeriodRequired =false; 
			
			
			}
		else if($scope.file.fileType=="JSON")
		{
			$scope.filePath=true;
			$scope.fileSchemaPath=false;
			$scope.serverIP=true;
			$scope.rowTag=true;
			$scope.fileDelimeter=true;
			$scope.calculateDeltaOn=true;
			$scope.joinKeys=true;
			$scope.archivePeriod =true; 
			
//			$scope.file.fileSchemaPath="";
			  $scope.file.filePath="" ;
			  $scope.file.fileDelimeter="";
			  $scope.file.calculateDeltaOn="" ;
			  $scope.file.joinKeys=""; 
			  $scope.file.rowTag  ="";
			  $scope.file.archivePeriod="";
			  $scope.file.SFTPserverIp="";
			
			//required
			$scope.filePathRequired=false;
			$scope.fileSchemaPathRequired=true;
			$scope.rowTagRequired=false;
			$scope.fileDelimeterRequired=false;
			$scope.joinKeysRequired=false;
			$scope.archivePeriodRequired =false; 
		}
		else if($scope.file.fileType=="DELIMITED")
			{
			$scope.filePath=false;
			$scope.fileSchemaPath=false;
			$scope.serverIP=false;
			$scope.rowTag=true;
			$scope.fileDelimeter=false;
			$scope.calculateDeltaOn=false;
			$scope.joinKeys=false;
			$scope.archivePeriod =false; 
			  $scope.file.rowTag  ="";
			
			//required
			$scope.filePathRequired=true;
			$scope.fileSchemaPathRequired=true;
			$scope.rowTagRequired=false;
			$scope.fileDelimeterRequired=true;
			$scope.joinKeysRequired=true;
			$scope.archivePeriodRequired =true; 
			
			}
		else
			{
			$scope.filePath=true;
			$scope.fileSchemaPath=true;
			//$scope.fileType =false;
			$scope.serverIP=true;
			$scope.rowTag=true;
			$scope.fileDelimeter=true;
			$scope.calculateDeltaOn=true;
			$scope.joinKeys=true;
			$scope.archivePeriod =true; 
		
			}
	};
		 
});
