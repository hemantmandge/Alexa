angular.module('utilityApp').controller('JobController',
		function($scope, $rootScope, $http, $uibModal, $location,ResourceService) {

	$scope.dataSourceList=["RDBMS","FILE","HADOOP"];
	$scope.jobstatus={};
	$scope.testData = null;
	$scope.jobstatus.sourceType="";$scope.jobstatus.sourceSystem="";$scope.jobstatus.serverIP="";
	$scope.jobstatus.dbName="";
	$scope.jobstatus.loadType="";$scope.jobstatus.fromDate ="";$scope.jobstatus.toDate ="";
	$scope.sourceDbDisable=true;
	$scope.dataSourceChange = function() {
		
		if($scope.jobstatus.sourceType=="RDBMS")
			{
			ResourceService.findByTypeAndName("DATASOURCE", "RDBMS").then(
					function(response) {
						$scope.sourceSystemList = response.data;
					})
			}
		else
			{
			ResourceService.findByType("FILETYPE").then(function(response) {
				$scope.sourceSystemList = response.data;
			});
			}
		
	};
	
	$scope.sourcetypeChange= function()
	{
	if($scope.jobstatus.sourceType=="RDBMS")
		{
		 ResourceService.findByName($scope.jobstatus.sourceSystem).then(function(response) {
			 $scope.sourceServerList = response.data;
			});
		}
	else if($scope.jobstatus.sourceType=="FILE")
		{
		ResourceService.findByName("SFTP").then(function(response) {
			$scope.sourceServerList = response.data;
		});
		}
	else
		{
		ResourceService.findByTypeAndName("SOURCECONNECTION","HADOOP").then(function(response) {
			$scope.sourceServerList = response.data;
		});
		}
	};
	
	
	$scope.sourceServerChange=function()
	{
		if($scope.jobstatus.sourceType=="RDBMS")
			{
			 ResourceService.findByName($scope.jobstatus.serverIP).then(function(response) {
				  $scope.sourceDBList = response.data;
				});
			 $scope.sourceDbDisable=false;
			}
		else
			{
			$scope.sourceDbDisable=true; 
			
			$scope.sourceDBList="";
			}
		
		 
	};
	
	$scope.onSubmitPopulate = function(page, size) {
		if($scope.jobstatus.dbName == undefined)
				$scope.jobstatus.dbName = "";
		if($scope.jobstatus.serverIP == undefined)
			$scope.jobstatus.serverIP ="";
		
		ResourceService.getJobRequests($scope.jobstatus.sourceType,$scope.jobstatus.sourceSystem,$scope.jobstatus.serverIP,$scope.jobstatus.dbName,
				$scope.jobstatus.loadType,$scope.jobstatus.fromDate,$scope.jobstatus.toDate, page,size).then(function(response) {
					$scope.testData = response.data;
					$scope.currentPage = $scope.testData.number+1;
					if($scope.testData.totalElements < 1){
						var modalInstance = $uibModal.open({
							controller: 'PopupCont',
							templateUrl: 'html/noRecordsFound.html'
				        });
					}
				});
	
	}
	
	$scope.onSubmit = function(page,size) {
		if($scope.testData == null || ($scope.currentPage !== "" && $scope.currentPage <= $scope.testData.totalPages && $scope.currentPage > 0)){
			if($scope.jobstatus.dbName == undefined)
				$scope.jobstatus.dbName = "";
			if($scope.jobstatus.serverIP == undefined)
				$scope.jobstatus.serverIP ="";
			ResourceService.getJobRequests($scope.jobstatus.sourceType,$scope.jobstatus.sourceSystem,$scope.jobstatus.serverIP,$scope.jobstatus.dbName,
					$scope.jobstatus.loadType,$scope.jobstatus.fromDate,$scope.jobstatus.toDate, page,size).then(function(response) {
						$scope.testData = response.data;
						$scope.currentPage = $scope.testData.number+1;
					});
		} else{
			alert("Please enter a valid page number.");
			$scope.currentPage = $scope.testData.number+1;
		}
	}
	
	$scope.exportToExcel = function(page,size) {
		if($scope.jobstatus.dbName == undefined)
			$scope.jobstatus.dbName = "";
		if($scope.jobstatus.serverIP == undefined)
			$scope.jobstatus.serverIP ="";
		ResourceService.exportToExcel($scope.jobstatus.sourceType,$scope.jobstatus.sourceSystem,$scope.jobstatus.serverIP,$scope.jobstatus.dbName,
				$scope.jobstatus.loadType,$scope.jobstatus.fromDate,$scope.jobstatus.toDate, page,size);
		};
	

	$scope.jobInit = function() {
		$rootScope.isNotJobPage = false;
		//$scope.jobstatus.loadType = "FULL";
		$scope.currentPage=1;
		ResourceService.findByType("LOADTYPE").then(function(response) {
			$scope.loadType = response.data;
		});		
		
	$scope.clickOpenPopUp = function(job){
			$rootScope.dataLoading = false;
			$rootScope.loadingBackgronud = false;
			var modalInstance = $uibModal.open({
				controller: 'JobStatusPopUp',
				templateUrl: 'html/job.html',
				resolve: {
	                param: function () {
	                    return {'data':job};
	                }
	            }
	        });
		};
		$scope.onSubmit(0,10);
	}
});

