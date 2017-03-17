angular.module('utilityApp').controller('HadoopController', 
		function($scope, $rootScope, $http, $uibModal,$location, ResourceService, $controller,$cookies) {
	
	var UtilityController = $scope.$new(); 
	$controller('utilityCtrl',{$scope : UtilityController });
	$scope.hadoop={};
	$scope.rowtagDisable=true;
	$scope.fileDelimeter=true;
	$scope.rowTagRequired=false;
	$scope.fileDelimeterRequired=false;
	$scope.hivePartitionKeyDisable=false;
	$scope.joinKeysList = [];
	
	$scope.pageLoadHadoopData=function()
	{
		if ($rootScope.isAuthenticated) { // if user is authenticated and $rootScope.isAuthenticated is not undefined call loadRDBMSData
        	$rootScope.homeRadioBtn = "Hadoop";
        	$scope.LoadHadoopData();
	    } 
        else { // if $rootScope.isAuthenticated is undefined (It may required to have explicit check for undefined)
        	if($cookies.getObject('userName'))
        	{
        		$rootScope.isAuthenticated = true;
        		$rootScope.homeRadioBtn = "Hadoop";
  		      	$location.path("/Hadoop");
        		$scope.LoadHadoopData();
        	}
        	else
        	{
        		$rootScope.isAuthenticated = false;
  		      	$location.path("/");
        	}
	    }
	};

	$scope.LoadHadoopData = function()
	{
		$scope.hadoop.sourceType="Hadoop";
		$scope.hadoop.loadType="FULL";
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
		ResourceService.findByType("TARGETCONNECTION").then(
				function(response) {
					$scope.targetcon = response.data;
				});
	};
	
	$scope.changeFileType=function()
	{
		if($scope.hadoop.fileType=="XML")
			{
			$scope.rowTagRequired=true;
			$scope.fileDelimeterRequired=false;
			$scope.rowtagDisable=false;
			$scope.fileDelimeter=true;
			$scope.hadoop.fileDelimeter="";
			}
		else if ($scope.hadoop.fileType=="DELIMITED")
			{
			$scope.fileDelimeter=false;
			$scope.fileDelimeterRequired=true;
			$scope.rowtagDisable=true;
			$scope.hadoop.rowTag="";
			$scope.rowTagRequired=false;
			}
		else
			{
			$scope.rowtagDisable=true;
			$scope.fileDelimeter=true;
			$scope.rowTagRequired=false;
			$scope.fileDelimeterRequired=false;
			$scope.hadoop.fileDelimeter="";
			$scope.hadoop.rowTag="";
			}
		
	};
	
	$scope.onChangeTableType= function(hiveTableType)
	{
		if  (hiveTableType == 'NON-PARTITIONED')
			{
			$scope.hivePartitionKeyDisable=true;
			}
		else
			{
			$scope.hivePartitionKeyDisable=false;
			}
	};
	
	$scope.onCancelOfHadoop =function()
	{
		  $scope.hadoop={};
		  $scope.hadoop.sourceType="Hadoop";
	};
	
	$scope.createHadoopCodeGenRequest = function(requestData) {
	if( $scope.hadoopForm.$invalid){
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
	
});