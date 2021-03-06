angular.module('utilityApp').controller('FileController', 
		function($scope, $rootScope, $http, $uibModal,$location, ResourceService, $controller,$cookies) {
	
	var UtilityController = $scope.$new(); 
	$controller('utilityCtrl',{$scope : UtilityController });
	$scope.file = {};
	$rootScope.isNotJobPage = true;
	//$scope.joinKeysList = [];
	 $scope.disabled = false;
	//variables for file 
	$scope.filePath=true;
	//$scope.fileSchemaPath=true;
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
	 //$scope.fileSchemaPath=false;
	$scope.rowTagRequired=false;
	$scope.fileDelimeterRequired=false;
	$scope.joinKeysRequired=false;
 
	$scope.hivepartionkeyrequired=false;
	
//	$scope.toggleFileResources = false;v
	/**
	 * calling web services for File Data source Start
	 */
	$scope.pageLoadDataFile = function() {
		$rootScope.homeRadioBtn = "Files";
		$rootScope.currentSelected = "Files";
		$scope.file.sourceSystem = "FILE";
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
		
		ResourceService.findByName("FILESCHEMAPATH").then(function(response) {
			$scope.fileSchemaPathStart = response.data[0].value;
		});
	
	};
	
	/** 
	 * THIS FUNCTION IS TO SUBMIT THE FILE DATA 
	 */
	$scope.createFileCodeGenRequest = function(requestData) {
		if( $scope.fileForm.$invalid){
			$scope.showValidationErrors = true; 
			return false;
		}
		if($rootScope.showArchiveValidation)
		{
		return false;
		}
		$rootScope.dataLoading = true;
		$rootScope.loadingBackgronud = true;
		$scope.submitted = true;
		var joinKeysList = [];
		var data = requestData;
		joinKeysList.push(data.joinKeyString);
		data.joinKeys = joinKeysList;
		
		ResourceService.isExists(data).then(function(response) {
			//$scope.isExist=response.data;
			var isCreateRecord=false;
			if(response.data) {
				$rootScope.dataLoading = false;
				$rootScope.loadingBackgronud = false;
			   	var isOverride = confirm("This record Exists. Do you want to override?");
			   	if(isOverride){
			   		isCreateRecord=true;
			   	}
			} else {
				isCreateRecord = true;
			}
		   if(isCreateRecord){
			   ResourceService.createCodeGenRequest(data)
				.then($scope.fileSuccess, UtilityController.createCodeGenRequestFailed)
				.catch(UtilityController.catchCreateCodeGenRequestError);
		   }
		   }).catch(function (data) {
				  $rootScope.dataLoading = false;
				  $rootScope.loadingBackgronud = false;
					
			});
		   
		
		
	
	};
	
	$scope.fileSuccess=function(data)
	{
		UtilityController.createCodeGenRequestSuccess(data);
		$scope.file = {};
		  $scope.file.sourceSystem="FILE";
			$scope.file.loadType = "FULL";
			$scope.showValidationErrors = false; 
	};
	
	
	 $scope.onCancelFile = function() {
		 $scope.file = {};
		 $scope.file.sourceSystem="FILE";
			$scope.file.loadType = "FULL";
			//$scope.joinKeysList = [];
			 $scope.disabled = false;
			//variables for file 
			$scope.filePath=true;
		//	$scope.fileSchemaPath=true;
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
			 //$scope.fileSchemaPath=false;
			$scope.rowTagRequired=false;
			$scope.fileDelimeterRequired=false;
			$scope.joinKeysRequired=false;
		 
			$scope.hivepartionkeyrequired=false;

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
			$scope.file.fileSchemaPath = ""+$scope.fileSchemaPathStart+"/FILE_XML";
			$scope.filePath=false;
		//	$scope.fileSchemaPath=false;
			$scope.serverIP=false;
			$scope.rowTag=false;
			$scope.fileDelimeter=true;
			$scope.calculateDeltaOn=true;
			$scope.joinKeys=false;
			$scope.archivePeriod =false; 
			
			//set the variables to blank todo: for server sftp
		//	 $scope.file.filePath="" ;
			 $scope.file.fileDelimeter="";
			 $scope.file.calculateDeltaOn="" ;
			 //$scope.file.joinKeys=""; 
			// $scope.file.serverIP="";
			// $scope.file.rowTag  ="";
			// $scope.file.archivePeriod="";
			
			//required
			
			$scope.filePathRequired=true;
			 //$scope.fileSchemaPath=true;
			$scope.rowTagRequired=true;
			$scope.fileDelimeterRequired=false;
			$scope.joinKeysRequired=true;
	
			
			
			}
		else if($scope.file.fileType=="JSON")
		{
			$scope.file.fileSchemaPath= ""+$scope.fileSchemaPathStart+"/FILE_JSON";

			$scope.filePath=false;
		//	$scope.fileSchemaPath=false;
			$scope.serverIP=false;
			$scope.rowTag=false;
			$scope.fileDelimeter=true;
			$scope.calculateDeltaOn=true;
			$scope.joinKeys=false;
			$scope.archivePeriod =false; 
			
				//$scope.file.fileSchemaPath="";
				//$scope.file.filePath="" ;
				$scope.file.fileDelimeter="";
				$scope.file.calculateDeltaOn="" ;
				//$scope.file.joinKeys=""; 
				//$scope.file.rowTag  ="";
				//$scope.file.archivePeriod="";
				//$scope.file.serverIP="";
			
			//required
			$scope.filePathRequired=true;
			 //$scope.fileSchemaPath=true;
			$scope.rowTagRequired=true;
			$scope.fileDelimeterRequired=false;
			$scope.joinKeysRequired=true;
			 
		}
		else if($scope.file.fileType=="DELIMITED")
			{
			$scope.file.fileSchemaPath= ""+$scope.fileSchemaPathStart+"/FILE_DELIMITED";

			$scope.filePath=false;
		//	$scope.fileSchemaPath=false;
			$scope.serverIP=false;
			$scope.rowTag=true;
			$scope.fileDelimeter=false;
			$scope.calculateDeltaOn=false;
			$scope.joinKeys=false;
			$scope.archivePeriod =false; 
			
			//$scope.file.fileSchemaPath="";
			//$scope.file.filePath="" ;
			//$scope.file.fileDelimeter="";
			//$scope.file.calculateDeltaOn="" ;
			//$scope.file.joinKeys=""; 
			$scope.file.rowTag  ="";
			//$scope.file.archivePeriod="";
			//$scope.file.serverIP="";
			
			//required
			$scope.filePathRequired=true;
			 //$scope.fileSchemaPath=true;
			$scope.rowTagRequired=false;
			$scope.fileDelimeterRequired=true;
			$scope.joinKeysRequired=true;
		 
			
			}
		else
			{
			$scope.file.fileSchemaPath= "";
			$scope.filePath=true;
		//	$scope.fileSchemaPath=true;
			//$scope.fileType =false;
			$scope.serverIP=true;
			$scope.rowTag=true;
			$scope.fileDelimeter=true;
			$scope.calculateDeltaOn=true;
			$scope.joinKeys=true;
			$scope.archivePeriod =true; 
			
			$scope.fileTypeRequired=true;
			$scope.filePathRequired=false;
			 //$scope.fileSchemaPath=false;
			$scope.rowTagRequired=false;
			$scope.fileDelimeterRequired=false;
			$scope.joinKeysRequired=false;
		 
			$scope.hivepartionkeyrequired=false;
		
			}
	};
		 
});
