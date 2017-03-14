var app = angular.module("utilityApp", ['ngRoute', 'angularUtils.directives.dirPagination', 'ui.bootstrap', 'utility.config','ngPatternRestrict']);
app.controller("utilityCtrl", ['$scope', '$rootScope', '$window', '$location', '$http', '$uibModal', 'Base_URL', function($scope, $rootScope, $window, $location, $http, $uibModal, Base_URL) {
 
	
	
	
 $scope.username = "admin";
 $scope.password = "1234";
 $scope.showHideHDFS = false;
 $scope.selected = false;
 $scope.codeGenRequest = {};
 $scope.file={};
 $scope.disabled = false;
 $scope.loading = false;
 $scope.welcome = false;
 $scope.loadType = {};
 $scope.sourceTables = {};
 $scope.selectedAllTables = false;
 $scope.datadbsrc = []; //declare an empty array
 $scope.isSelectedFlag = false;
 $scope.targetType = "";
 $scope.targetcon = {};
 $rootScope.homeRadioBtn = "RDBMS";
 //$scope.codeGenRequest.username = "admin";
 //$scope.codeGenRequest.password = "gepoc";
 //$scope.codeGenRequest.archivePeriod="4";
 $scope.createNewButton=true;
 
 // parameters to disable enable classes
 $scope.class = "disabledlabel";
 $scope.classdropdown="disabledDropDown";
 $scope.classtextbox="disabledTextBox";
 $scope.class1 = "disabledlabel";
 $scope.classdropdown1="disabledDropDown";
 $scope.class2 = "disabledlabel";
 $scope.classdropdown2="disabledDropDown";
 $scope.disabledMultiple=true;
 $scope.toggleAll = true;
 $scope.toggleTable = true;
 $scope.toggleDisconnect = true;
$scope.tempClass = 'disabledTextBox';
$scope.whereDisabled= true;
$scope.homeShow = false;
 $scope.readonlyAttr = false;
$scope.joinKeysList = [];
$scope.showbutton=true;
$scope.showbuttonDisc=false;
$scope.connectDisble=false;

$scope.srcTypeDisble =false;
$scope.dbconnectionDisble=true;
$scope.dbnameDisble=true;

$scope.showDBName=true;

//required variable for rdbms

$rootScope.dbconnectionRequired=false;
$rootScope.dbNameRequired=false;
$rootScope.userNameRequired=false;
$rootScope.passwordRequired=false;

$rootScope.dbSchemaREquired=false;
$rootScope.tableRequired=false;
$rootScope.colRequired=false;
$rootScope.caculateDeltaRequired=false;
$rootScope.joinKeyRequired=false;
$rootScope.archivePeriodRequired=false;
$rootScope.loadTypeRequired=false;
$rootScope.targetconnRequired=false;
$rootScope.hivedbnameRequired=false;
$rootScope.hiveTableNameRequired=false;



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
$scope.isRequired=true;
$scope.colRequiredchk=true;


//variables for required check 
$scope.fileTypeRequired=true;
$scope.filePathRequired=false;
$scope.fileSchemaPathRequired=false;
$scope.rowTagRequired=false;
$scope.fileDelimeterRequired=false;
$scope.joinKeysRequired=false;
$scope.archivePeriodRequired =false; 


/**
 * calling web services for File Data source Start 
 */
$scope.loadFileInitialData=function()
{
	  $scope.authenticate(); 
	 $scope.file.sourceType="FILE";
	
	  $http.get("resources/findByType?type=FILETYPE").then(function(response) {
		   $scope.fileType = response.data; //ajax request to fetch data into $scope.data
		   
		  });
	  
	  $http.get("resources/findByName?name=SFTP").then(function(response) {
		    $scope.SFTPserverIPList = response.data; //ajax request to fetch data into $scope.data
		   });
	  
	//  $scope.file.targetType = "HIVE";
	  
	   $scope.file.loadType = "FULL";
	   $http.get("resources/findByType?type=LOADTYPE").then(function(response) {
	    $scope.loadType = response.data; //ajax request to fetch data into $scope.data
	   });
	  
	};
	
	
	$scope.changeTableType=function(hiveTableType)
	{
		console.log(hiveTableType);
		if  (hiveTableType == 'NON-PARTITIONED')
			{
			$scope.hivepartionkeydisabled=true;
			$scope.file.targetPartitionKey="";
			}
		else
			{
			$scope.hivepartionkeydisabled=false;
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
		//	$scope.file.fileSchemaPath="";
			  $scope.file.filePath="" ;
			  $scope.file.fileDelimeter="";
			  $scope.file.calculateDeltaOn="" ;
			  $scope.file.joinKeys=""; 
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
			
//			$scope.file.fileSchemaPath="";
			//  $scope.file.filePath="" ;
			//  $scope.file.fileDelimeter="";
			//  $scope.file.calculateDeltaOn="" ;
			//  $scope.file.joinKeys=""; 
			  $scope.file.rowTag  ="";
			 // $scope.file.archivePeriod="";
			
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
	

$scope.enableForm =function(){
	
	 $scope.toggleAll = true;
	 $scope.toggleDisconnect = false;
	// $scope.disabledMultiple = false;
	 $scope.whereDisabled= true;
	 $scope.class = "disabledlabel";
	 $scope.classdropdown="disabledDropDown";
	 $scope.classtextbox="disabledTextBox";
	if($scope.showbuttonDisc == true){
	         $scope.readonlyAttr=false;
	         $scope.showbutton=true;
	         $scope.showbuttonDisc=false;
	          $scope.connectDisble=false;
	          $scope.srcTypeDisble =false;
	          $scope.dbconnectionDisble=false;
	          $scope.dbnameDisble=false;
	
	    }
    
}

  $scope.navigatePath =function(){
    $rootScope.showHome=false;
    }



 
 $scope.onCancel = function() {
  $scope.codeGenRequest = {};
  $scope.file={};
  $scope.file.sourceType="FILE";

  $location.path('/home');
  $rootScope.homeRadioBtn="RDBMS";
 }
 $scope.pageLoadData = function() {
  $scope.authenticate(); 
  $http.get("resources/findByTypeAndName?type=DATASOURCE&name=RDBMS").then(function(response) {
   $scope.datadbsrc = response.data; //ajax request to fetch data into $scope.data
 
  });
  $http.get("resources/findByType?type=TARGETCONNECTION").then(function(response) {
   $scope.targetcon = response.data; //ajax request to fetch data into $scope.data
  
  });
  $scope.codeGenRequest.targetType = "HIVE";
  if ($scope.codeGenRequest.targetType == 'HIVE') {
	  $scope.colRequiredchk =true;
   $scope.codeGenRequest.loadType = "FULL";
   $http.get("resources/findByType?type=LOADTYPE").then(function(response) {
    $scope.loadType = response.data; //ajax request to fetch data into $scope.data
   });
  }
  else
	  {
	  $scope.colRequiredchk =false;
	  }
 }
 setTimeout(function() {
  $scope.loading = true;
  $scope.welcome = true;
 }, 1000);
 $scope.showHiveBlk = function(data) {
	 
  if (data == 'HIVE') {
   $scope.disabled = false;
   $scope.isdisabled = false;
   $scope.colRequiredchk =true;
   $scope.hivetabledisabled=false;
   $http.get("resources/findByType?type=LOADTYPE").then(function(response) {
    $scope.loadType = response.data; //ajax request to fetch data into $scope.data
   });
   if($scope.codeGenRequest.sourceTableNames.length>1)
	   {
	   $scope.codeGenRequest.targetTableName = $scope.codeGenRequest.sourceTableNames;
	   $scope.hivetabledisabled=true;
	   }
   if($scope.codeGenRequest.sourceTableNames.length==1)
   {
   $scope.codeGenRequest.targetTableName = $scope.codeGenRequest.sourceTableNames;
   $scope.hivetabledisabled=false;
   }

   
  } else {
	  $scope.hivedbnameRequired=false;
	  $scope.hiveTableNameRequired=false;
	  
   $scope.codeGenRequest.targetTableName = "";
   $scope.codeGenRequest.targetDBName = "";
   $scope.codeGenRequest.hiveTableType = "";
   $scope.codeGenRequest.targetPartitionKey = "";
   $scope.disabled = true;
   $scope.colRequiredchk =false;
   $scope.isdisabled = true;
   $scope.hivetabledisabled= true;
   $http.get("resources/findByName?name=FULL").then(function(response) {
    $scope.loadType = response.data; //ajax request to fetch data into $scope.data
   });
  }
 }
 
  function loginSuccessCallback(data) {
	 if (data) {
		 $location.path("/home");
		 $scope.error = false;
		 $rootScope.isAuthenticated = true;
	 } else {
		 $location.path("/login");
		 $scope.error = true;
		 $rootScope.isAuthenticated = false
	 }
 };
 function loginErrorCallback(data) {
	 $location.path("/login");
	 $scope.error = true;
	 $rootScope.isAuthenticated = false;
 }
  $scope.SubmitLogin = function(credentials) {
  var config = {
          headers: {'Content-Type': 'application/x-www-form-urlencoded'}
      };

  var params = {username: credentials.username, password: credentials.password};
   $http({
        url: 'authenticate',
        method: 'POST',
        params: params,
        headers: config
    }).then(loginSuccessCallback, loginErrorCallback);
  };

$scope.authenticate = function() {
    $http.get('resources/user').then(function(userData) {
      if (userData.data.name) {
        $rootScope.isAuthenticated = true;
      } else {
        $rootScope.isAuthenticated = false;
        $location.path("/login")
      }
    },function() {
      $rootScope.isAuthenticated = false;
      $location.path("/login")
    });
  }
 $scope.postJob = function() {
  $location.path('/job');
 }
 $scope.users = []; //declare an empty array
 $http.get("data/data.json").then(function(response) {
  $scope.users = response.data; //ajax request to fetch data into $scope.data
 });
 $scope.getDB = function(datasrc) {
  //$scope.datasrc = datasrc;
	 if(datasrc !== undefined)
		 {
		  $scope.dbconnectionDisble=false;
		  $scope.class1 = "labelSubHeader";
		  $scope.classdropdown1="customDropDown";
		 }
	 if(datasrc== undefined || datasrc== null || datasrc == '')
	 {
		  $scope.dbconnectionDisble=true;
		  $scope.class1 = "disabledlabel";
		  $scope.classdropdown1="disabledDropDown";
	 }

  $scope.datadbconn = []; //declare an empty array
  $http.get("resources/findByName?name=" + datasrc).then(function(response) {
	 if(response.data) {
		 $scope.datadbconn = response.data; //ajax request to fetch data into $scope.data
	 }
	/* else{
		 
	 }*/
  });
 if(datasrc=="ORACLE"||datasrc=="GREENPLUM")
	 {
	 $scope.showDBName=true;
	 $scope.dbNameRequiredchk= true;
	 }
 else
	 {
	 $scope.showDBName=false;
	 $scope.dbNameRequiredchk= false;
	 }
 }
 $scope.getSID = function(srctype) {
	 // console.log(srctype);
	 if(srctype !== undefined)
		 {
		 	$scope.dbnameDisble=false;
			 $scope.class2 = "labelSubHeader";
			 $scope.classdropdown2="customDropDown";
		 }
	 if(srctype == undefined || srctype == null || srctype == '')
        {
			 $scope.dbnameDisble=true;
			 $scope.class2 = "disabledlabel";
			 $scope.classdropdown2="disabledDropDown";
		}	 
  $scope.srcdbport = srctype;
  $scope.datadbSID = []; //declare an empty array
  $http.get("resources/findByName?name=" + srctype).then(function(response) {
   $scope.datadbSID = response.data; //ajax request to fetch data into $scope.data
  });
 }
 $scope.connectDB = function(username, password, host, dbName, rdbmsName) {
	 $scope.dbconnectionRequired=true;
	 $scope.dbNameRequired=true;
	 $scope.userNameRequired=true;
	 $scope.passwordRequired=true;
	

 $scope.dbschemaDisable = false;

	 $scope.dbSchemaREquired=false;
	 $scope.tableRequired=false;
	 $scope.colRequired=false;
	 $scope.caculateDeltaRequired=false;
	 $scope.joinKeyRequired=false;
	 $scope.archivePeriodRequired=false;
	 $scope.loadTypeRequired=false;
	 $scope.targetconnRequired=false;
	 $scope.hivedbnameRequired=false;
	 $scope.hiveTableNameRequired=false;
	 //console.log("Inside ConnectDB : $scope.codeGenRequest.rdbmsForm.$invalid = ",$scope.codeGenRequest.rdbmsForm.$invalid )
	 if($scope.codeGenRequest.rdbmsForm.$invalid){
		 //console.log("Inside ConnectDB : $scope.codeGenRequest.rdbmsForm.$invalid = ",$scope.codeGenRequest.rdbmsForm.$invalid )
			$scope.showValidationErrors = true;
			return false;
		}
	 

	 
     $scope.dataLoading = true;
     $scope.loadingBackgronud = true;
	  $scope.databaseSchema = [];
	  $http.get("resources/getSchemaDetails?userid=" + username + "&password=" + password + "&host=" + host + "&database=" + dbName + "&rdbmsName=" + rdbmsName + "").then(function(response) {
   $scope.databaseSchema = response.data; //ajax request to fetch data into $scope.data
   if (response.data) {
	   $scope.toggleAll = false;
	   $scope.toggleDisconnect = true;
		// Parameter to change class from disable to enable
		 $scope.class = "labelSubHeader";
		 $scope.classdropdown="customDropDown";
		 $scope.classtextbox="customTextBox ";
		 
		 $scope.disabledMultiple = false;
		 $scope.whereDisabled= false;
	   $scope.createNewButton=false;
    $scope.dataLoading = false;
    $scope.loadingBackgronud = false;
    $scope.readonlyAttr=true;
    $scope.connectDisble=true;
    $scope.srcTypeDisble =true;
    $scope.dbconnectionDisble=true;
    $scope.dbnameDisble=true;
 
    $scope.showbutton=false;
    $scope.showbuttonDisc=true;
    $scope.codeGenRequest.rdbmsForm.$invalid = true;
    
    var modalInstance = $uibModal.open({
     controller: 'PopupCont',
     templateUrl: 'html/successpopupconnect.html',
    });
      $scope.loading = false;
   } else {
    var modalInstance = $uibModal.open({
     controller: 'PopupCont',
     templateUrl: 'html/errorpopupconnect.html',
    });
    $scope.dataLoading = false;
    $scope.loadingBackgronud = false;
   }
  }) .catch(function (data) {

	  $scope.dataLoading = false;
	  $scope.loadingBackgronud = false;
	   var modalInstance = $uibModal.open({
            controller: 'PopupCont',
            templateUrl: 'html/errorpopupconnect.html',
        });
		
});
 }
 $scope.getTables = function(username, password, host, database, rdbmsName, dbschema) {
	 $scope.dataLoading = true;
	 $scope.loadingBackgronud = true;
  $scope.tables = []; //declare an empty array
  $http.get("resources/getTables?userid=" + username + "&password=" + password + "&host=" + host + "&database=" + database + "&rdbmsName=" + rdbmsName + "&schema=" + dbschema + "").then(function(response) {
  $scope.tables = response.data; //ajax request to fetch data into $scope.data
   $scope.dataLoading = false;
   $scope.loadingBackgronud = false;
   if( $scope.tables.length>1)
	   {
	   $scope.sourceTables = response.data.slice();
	   $scope.tables.splice(0, 0, "Select All");
	   }
  });
 }
 $scope.sourceCols={};
 $scope.getCols = function(username, password, host, database, rdbmsName, tableName, dbschema) {
  var tbllen =tableName.length;

  $scope.cols = []; //declare an empty array
  if (tableName.length <= 1) {
   $http.get("/resources/getColumns?userid=" + username + "&password=" + password + "&host=" + host + "&database=" + database + "&rdbmsName=" + rdbmsName + "&tableName=" + tableName + "&schema=" + dbschema + "").then(function(response) {
    $scope.cols = response.data; //ajax request to fetch data into $scope.data
    if(tbllen==1&&   $scope.cols.length>1){
    	   $scope.sourceCols = response.data.slice();
      
      $scope.cols.splice(0, 0, "Select All");
    }
   
   });
  }
 // $scope.srcclumn = $scope.cols;
//  $scope.joinKeys = $scope.cols;
 }
 $scope.popup = function() {
  var modalInstance = $uibModal.open({
   controller: 'PopupCont',
   templateUrl: 'html/successpopup.html',
  });
 }
 $scope.changePartitinKey = function(tabletype) {
  // console.log('type is', tabletype);
  if (tabletype == 'NON-PARTITIONED') {
	  $scope.codeGenRequest.targetPartitionKey="";
    $scope.isdisabled = true;
   $scope.hivetabledisabled=false;
  } else {
   $scope.isdisabled = false;
 
  }
 }
 $scope.disableDrop = function(selectedItem) {
  if (selectedItem.length >= 2) {
   $scope.disabledMultiple = true;
   $scope.isdisabled = true;
   $scope.codeGenRequest.hiveTableType ="NON-PARTITIONED";
   if ($scope.codeGenRequest.targetType == "HDFS") {
    $scope.codeGenRequest.targetTableName = "";
    $scope.codeGenRequest.targetDBName = "";
    $scope.codeGenRequest.hiveTableType = "";
    $scope.codeGenRequest.targetPartitionKey = "";
   }
  } else {
   $scope.disabledMultiple = false;
  }
  if (selectedItem.length == 1 && $scope.codeGenRequest.targetType == "HDFS") 
	  {
	  $scope.hivetabledisabled=true;
	  }
  if( $scope.codeGenRequest.sourceTableNames.length > 1 || $scope.codeGenRequest.sourceTableNames.indexOf("Select All") > -1)
	{
	   // console.log("inside if",$scope.codeGenRequest.sourceTableNames);
	   $scope.toggleTable = false;
	   //disable some columns
	}
  else
  {
	  //	  enable rest
	  $scope.toggleTable = true;
  }
  
 }
 $scope.postdata = function(codeGenRequest) {
	 // console.log("inside pose data");
	/* $scope.dbconnectionRequired=true;
	 $scope.dbNameRequired=true;
	 $scope.userNameRequired=true;
	 $scope.passwordRequired=true;*/
	 $scope.dbSchemaREquired=true;
	 $scope.tableRequired=true;
	 $scope.colRequired=true;
	 $scope.caculateDeltaRequired=true;
	 $scope.joinKeyRequired=true;
	 $scope.archivePeriodRequired=true;
	 $scope.loadTypeRequired=true;
	 $scope.targetconnRequired=true;
	 $scope.hivedbnameRequired=true;
	 $scope.hiveTableNameRequired=true;
	 
		if($scope.codeGenRequest.rdbmsForm.$invalid){
			$scope.showValidationErrors = true;
			return false;
		}
		
			 $scope.dataLoading = true;
			   $scope.loadingBackgronud = true;
			   $scope.submitted = true;
			  /*if ($scope.rdbmsForm.$valid) {*/
				 
			   var data = codeGenRequest;
			    $scope.dataLoading = true;
			    $scope.loadingBackgronud=true;
			   //Call the services
			   $http.post('codeGenRequests/create', angular.fromJson(data)).then(function(response) {
				   $scope.dataLoading = false;
				   $scope.loadingBackgronud = false; 
			    if (response.data)
			     $scope.msg = "Post Data Submitted Successfully!";
			    var modalInstance = $uibModal.open({
			     controller: 'PopupCont',
			     templateUrl: 'html/successpopup.html',
			    });
			   }, function(response) {
			    $scope.msg = "Service not Exists";
			    $scope.dataLoading = false;
			    $scope.loadingBackgronud=false;
			    $scope.statusval = response.status;
			    $scope.statustext = response.statusText;
			    $scope.headers = response.headers();
			    var modalInstance = $uibModal.open({
			     controller: 'PopupCont',
			     templateUrl: 'html/errorpopup.html',
			    });
			   }).catch(function (data) {
					  $scope.dataLoading = false;
					  $scope.loadingBackgronud = false;
					   var modalInstance = $uibModal.open({
		                    controller: 'PopupCont',
		                    templateUrl: 'html/errorpopup.html',
		                });
						
				});
			 /* } else {
			   $scope.codeGenRequest.rdbmsForm.$invalid = 'true';
			  }*/
			

 };
 
 $scope.postdataFile = function(filedata) {
	 
	 console.log("inside pose data",filedata);	 
		 console.log("in post data : $scope.file.fileForm.$invalid =", $scope.file.fileForm.$invalid);
		 console.log("in post data : $scope.file.fileForm.$valid =", $scope.file.fileForm.$valid);
		 console.log("in post data : $scope.file.fileForm.$valid =" ,$scope.file.fileForm.$error.required);
		 
			
			  if( $scope.file.fileForm.$invalid){ 
			 
				  console.log("in post data Invali Form:  $scope.file.fileForm.$invalid=", $scope.file.fileForm.$invalid);
			  $scope.showValidationErrors = true; 
			  return false;
			  }
			 
			
				 $scope.dataLoading = true;
				   $scope.loadingBackgronud = true;
				   $scope.submitted = true;
				   
				  /* if ($scope.rdbmsForm.$valid) { */
				   var data = filedata;
				   $scope.joinKeysList.push(data.joinKeys);
				   data.joinKeys = $scope.joinKeysList;
				   console.log("inside pose data",filedata);	
				  
				    $scope.dataLoading = true;
				    $scope.loadingBackgronud=true;
				   // Call the services
				   $http.post('codeGenRequests/create', angular.fromJson(data)).then(function(response) {
					   $scope.dataLoading = false;
					   $scope.loadingBackgronud = false; 
				    if (response.data)
				     $scope.msg = "Post Data Submitted Successfully!";
				    var modalInstance = $uibModal.open({
				     controller: 'PopupCont',
				     templateUrl: 'html/successpopup.html',
				    });
				   }, function(response) {
				    $scope.msg = "Service not Exists";
				    $scope.dataLoading = false;
				    $scope.loadingBackgronud=false;
				    $scope.statusval = response.status;
				    $scope.statustext = response.statusText;
				    $scope.headers = response.headers();
				    var modalInstance = $uibModal.open({
				     controller: 'PopupCont',
				     templateUrl: 'html/errorpopup.html',
				    });
				   }).catch(function (data) {
						  $scope.dataLoading = false;
						  $scope.loadingBackgronud = false;
						   var modalInstance = $uibModal.open({
			                    controller: 'PopupCont',
			                    templateUrl: 'html/errorpopup.html',
			                });
							
					});
				 /*
					 * } else { $scope.rdbmsForm.$invalid = 'true'; }
					 */
				

	 };
 
 
 
 $scope.hiveTableType = {};
 $scope.loadHiveTableType = function() {
  $http.get("resources/findByType?type=HIVETABLETYPE").then(function(response) {
   $scope.hiveTableType = response.data; //ajax request to fetch data into $scope.data
  });
 }

 $scope.onSelcted2 = function(selectedItem) {
  if (selectedItem == 'Select All') {
   $scope.codeGenRequest.hiveTableType = "NON-PARTITIONED";
   $scope.codeGenRequest.sourceTableNames = $scope.sourceTables;
   $scope.selectedAllTables = !$scope.selectedAllTables;
   $scope.disabledMultiple = true;
   $scope.hivetabledisabled=true;
   $scope.isRequired =false;
  
   $scope.isdisabled = true;
    $scope.sourceColumnNames=[];
    
    $scope.colRequired=false;
    $scope.caculateDeltaRequired=false;
    $scope.joinKeyRequired=false;
  } else
  {
	  $scope.isRequired =true;
	   if(selectedItem.length==1  )
		   {
		   		$scope.hivetabledisabled=false;
		   }
	   else
		   {
		  $scope.hivetabledisabled=true;
	   $scope.colRequired=false;
		    $scope.caculateDeltaRequired=false;
	    $scope.joinKeyRequired=false;
	   }
	   
  }
 }


 $scope.selectAllClass = function(selectedItem) {
  return selectedItem == 'Negative' ? 'warning' : 'ok';
 }
 $scope.selectAlltables = function(isChecked) {
  $scope.isSelectedFlag = isChecked;
  if (isChecked) {
   $scope.codeGenRequest.sourceTableNames = $scope.sourceTables.slice();
  } else {
   $scope.codeGenRequest.sourceTableNames = {};
  }
 }
 $scope.getHiveTableNames = function(tables) {
  var myselected = "";
	  angular.forEach(tables, function(value) {
		  if (myselected == "") {
			  myselected = value;
		  } else {
			  myselected = myselected + ',' + value;
		  }
	  });
	  $scope.codeGenRequest.targetTableName = myselected;
	  $scope.myvalue = myselected;
 } 

 

$scope.onSelctedColumns = function(selectedItem) {
	console.log(selectedItem);
	if (selectedItem == 'Select All') {
   $scope.codeGenRequest.sourceColumnNames = $scope.sourceCols;
  } 
 };


}]);
angular.module('utilityApp').controller('PopupCont', ['$scope', '$uibModalInstance', function($scope, $uibModalInstance) {
 $scope.close = function() {
	  $scope.file={};
	  $scope.codeGenRequest = {};
	 $uibModalInstance.dismiss('cancel');
	
	
	 
 
 };
}]);