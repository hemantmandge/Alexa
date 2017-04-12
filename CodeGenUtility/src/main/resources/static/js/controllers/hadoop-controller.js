angular.module('utilityApp').controller('HadoopController', 
		function($scope, $rootScope, $http, $uibModal,$location, ResourceService, $controller,$cookies) {
	
	var UtilityController = $scope.$new(); 
	$controller('utilityCtrl',{$scope : UtilityController });
	$scope.hadoop={};
	$scope.rowtagDisable=true;
	$rootScope.isNotJobPage = true;
	$scope.fileDelimeter=true;
	$scope.rowTagRequired=false;
	$scope.fileDelimeterRequired=false;
	$scope.hivePartitionKeyDisable=false;

	
	$scope.pageLoadHadoopData=function() {
		$rootScope.homeRadioBtn = "Hadoop";
		$scope.hadoop.sourceSystem="Hadoop";
		$scope.hadoop.loadType="FULL";
		$rootScope.currentSelected = "Hadoop";

		ResourceService.findByType("FILETYPE").then(function(response) {
			$scope.fileTypeHadoop = response.data;
		});
		
		ResourceService.findByTypeAndName("SOURCECONNECTION","HADOOP").then(function(response) {
			$scope.SFTPserverIPListHadoop = response.data;
		});
		
		ResourceService.findByType("LOADTYPE").then(function(response) {
			$scope.loadType = response.data;
		});
		
		ResourceService.findByType("HIVETABLETYPE").then(function(response) {
			$scope.hiveTableType = response.data;
		});
		ResourceService.findByType("TARGETCONNECTION").then( function(response) {
			$scope.targetcon = response.data;
		});
		ResourceService.findByName("FILESCHEMAPATH").then(function(response) {
			$scope.fileSchemaPathStart = response.data[0].value;
		});
	};

	
	$scope.changeFileType=function() {
		if($scope.hadoop.fileType=="XML")
			{
			$scope.hadoop.fileSchemaPath = $scope.fileSchemaPathStart+"/HADOOP_XML";
			$scope.rowTagRequired=true;
			$scope.fileDelimeterRequired=false;
			$scope.rowtagDisable=false;
			$scope.fileDelimeter=true;
			$scope.hadoop.fileDelimeter="";
			}
		else if ($scope.hadoop.fileType=="DELIMITED") {
			$scope.hadoop.fileSchemaPath = $scope.fileSchemaPathStart+"/HADOOP_DELIMITED";
			$scope.fileDelimeter=false;
			$scope.fileDelimeterRequired=true;
			$scope.rowtagDisable=true;
			$scope.hadoop.rowTag="";
			$scope.rowTagRequired=false;
			}
		else if ($scope.hadoop.fileType=="JSON"){
			$scope.hadoop.fileSchemaPath = $scope.fileSchemaPathStart+"/HADOOP_JSON";
			$scope.rowtagDisable=false;
			$scope.fileDelimeter=true;
			$scope.rowTagRequired=true;
			$scope.fileDelimeterRequired=false;
			$scope.hadoop.fileDelimeter="";
		//	$scope.hadoop.rowTag="";
			}
		else{
			$scope.hadoop.fileSchemaPath = "";
			$scope.hadoop.fileDelimeter="";
			$scope.rowtagDisable=true;
			$scope.fileDelimeter=true;
			$scope.rowTagRequired=false;
			$scope.fileDelimeterRequired=false;
		}
		
	};
	
	$scope.onChangeTableType= function(hiveTableType)
	{
		if  (hiveTableType == 'NON-PARTITIONED')
			{
			$scope.hivePartitionKeyDisable=true;
			$scope.hadoop.targetPartitionKey="";
			}
		else
			{
			$scope.hivePartitionKeyDisable=false;
			}
	};
	
	$scope.onCancelOfHadoop =function()
	{
		$scope.hadoop={};
		$scope.rowtagDisable=true;
		$scope.fileDelimeter=true;
		$scope.rowTagRequired=false;
		$scope.fileDelimeterRequired=false;
		$scope.hivePartitionKeyDisable=false;
		  $scope.hadoop.sourceSystem="Hadoop";
		  $scope.hadoop.loadType = "FULL";
	};
	
	$scope.createHadoopCodeGenRequest = function(requestData) {
	if( $scope.hadoopForm.$invalid){
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
			$scope.isExist=response.data;
			if($scope.isExist)
			   {
			   //popup
			   	var isRecordFound = confirm("This record Exists. Do you want to override?");
			   	if(isRecordFound){
			   		$scope.isExist = false;
			   	}
			    $rootScope.dataLoading = false;
				$rootScope.loadingBackgronud = false;
			   }
		   if(!$scope.isExist)
			   {
			   ResourceService.createCodeGenRequest(data)
				.then($scope.hadoopSuccess, UtilityController.createCodeGenRequestFailed)
				.catch(UtilityController.catchCreateCodeGenRequestError);
			   }
		   }).catch(function (data) {

				  $rootScope.dataLoading = false;
				  $rootScope.loadingBackgronud = false;
					
			});
		
		
	};
	
	$scope.hadoopSuccess=function(data)
	{
		UtilityController.createCodeGenRequestSuccess(data);
		$scope.hadoop = {};
		  $scope.hadoop.sourceSystem="Hadoop";
			$scope.hadoop.loadType = "FULL";
			$scope.showValidationErrors = false; 
	};
	
});