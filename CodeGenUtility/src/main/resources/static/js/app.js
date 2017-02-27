var app = angular.module("utilityApp", ['ngRoute', 'angularUtils.directives.dirPagination', 'ui.bootstrap', 'utility.config']);
app.controller("utilityCtrl", ['$scope', '$rootScope', '$window', '$location', '$http', '$uibModal', 'Base_URL', function($scope, $rootScope, $window, $location, $http, $uibModal, Base_URL) {
 $scope.username = "admin";
 $scope.password = "1234";
 $scope.showHideHDFS = false;
 $scope.selected = false;
 $scope.rdbms = {};
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
 $scope.datasrc = 'RDBMS';
 $scope.rdbms.username = "admin";
 $scope.rdbms.password = "gepoc";
 $scope.homeShow = false;
 $scope.readonlyAttr = false;

$scope.showbutton=true;
$scope.showbuttonDisc=false;
$scope.connectDisble=false;


$scope.enableForm =function(){
if($scope.showbuttonDisc == true){
         $scope.readonlyAttr=false;
         $scope.showbutton=true;
         $scope.showbuttonDisc=false;
          $scope.connectDisble=false;

    }
    
}

$scope.go =function(){
 //alert ("in go button");
  $rootScope.showHome=false;
     //alert("hi in go button"+$rootScope.showHome);   
        }



 if ($location.path() == "/home") {
  $scope.homeShow = false;
 } else {
  $scope.homeShow = true;
 }
 $scope.onCancel = function() {
  //  alert ("in cancel button");
  $scope.rdbms = {};
  $location.path('/home');
 }
 $scope.pageLoadData = function() {
  $http.get("resources/findByTypeAndName?type=DATASOURCE&name=RDBMS").then(function(response) {
   $scope.datadbsrc = response.data; //ajax request to fetch data into $scope.data
  });
  $http.get("resources/findByType?type=TARGETCONNECTION").then(function(response) {
   //alert(response.data);
   $scope.targetcon = response.data; //ajax request to fetch data into $scope.data
  });
  $scope.rdbms.targetType = "HIVE";
  if ($scope.rdbms.targetType == 'HIVE') {
   //alert("hellp trt ddd"+$scope.rdbms.targetType);
   $scope.rdbms.loadType = "FULL";
   $http.get("resources/findByType?type=LOADTYPE").then(function(response) {
    $scope.loadType = response.data; //ajax request to fetch data into $scope.data
   });
  }
 }
 setTimeout(function() {
  $scope.loading = true;
  $scope.welcome = true;
 }, 1000);
 //alert(Base_URL);
 $scope.showHiveBlk = function(data) {
  if (data == 'HIVE') {
   //  alert(data);
   $scope.disabled = false;
   $scope.isdisabled = false;
   $http.get("resources/findByType?type=LOADTYPE").then(function(response) {
    $scope.loadType = response.data; //ajax request to fetch data into $scope.data
   });
  } else {
   //alert(data);
   $scope.rdbms.targetTableName = "";
   $scope.rdbms.targetDBName = "";
   $scope.rdbms.hiveTableType = "";
   $scope.rdbms.targetPartitionKey = "";
   $scope.disabled = true;
   $scope.isdisabled = true;
   $http.get("resources/findByName?name=FULL").then(function(response) {
    $scope.loadType = response.data; //ajax request to fetch data into $scope.data
   });
  }
 }
 $scope.SubmitLogin = function() {
     $rootScope.showHome=false;
  if ($scope.username == 'admin') {
   $location.path('/home');
  } else {
   console.log("login failed ");
  }
 };
 $scope.submitResult = function(datasrc) {
  $scope.homeShow = true;
  $rootScope.showHome=true;
  // alert("submitResult" + $scope.neha);
  $scope.datasrc = datasrc;
  if (datasrc == 'RDBMS') {
   $location.path('/RDBMS');
  } else if (datasrc == 'Hadoop') {
   $location.path('/Hadoop');
  } else if (datasrc == 'Files') {
   $location.path('/Files');
  } else if (datasrc == 'S3') {
   $location.path('/S3');
  } else {
   $location.path('/RDBMS');
  }
 }
 $scope.postJob = function() {
  $location.path('/job');
 }
 $scope.users = []; //declare an empty array
 $http.get("data/data.json").then(function(response) {
  $scope.users = response.data; //ajax request to fetch data into $scope.data
 });
 $scope.getDB = function(datasrc) {
  $scope.datasrc = datasrc;
  $scope.datadbconn = []; //declare an empty array
  $http.get("resources/findByName?name=" + datasrc).then(function(response) {
   // alert(response);
   $scope.datadbconn = response.data; //ajax request to fetch data into $scope.data
  });
 }
 $scope.getSID = function(srctype) {
  // alert("hjjkhjk"+$scope.dbSID);
  $scope.srcdbport = srctype;
  //alert($scope.rdbms.srcdbport);
  $scope.datadbSID = []; //declare an empty array
  $http.get("resources/findByName?name=" + srctype).then(function(response) {
   //  alert(response);
   $scope.datadbSID = response.data; //ajax request to fetch data into $scope.data
  });
 }
 $scope.connectDB = function(username, password, host, dbName, rdbmsName) {
     $scope.dataLoading = true;
    
  $scope.databaseSchema = [];
  $http.get("resources/getSchemaDetails?userid=" + username + "&password=" + password + "&host=" + host + "&database=" + dbName + "&rdbmsName=" + rdbmsName + "").then(function(response) {
   // alert("http://10.174.132.12:8080/resources/getSchemaDetails?userid="+username+"&password="+password+"&host="+host+"&database="+database+"&rdbmsName="+rdbmsName+"");
   $scope.databaseSchema = response.data; //ajax request to fetch data into $scope.data
   if (response.data) {
       $scope.dataLoading = false;
    $scope.readonlyAttr=true;
    $scope.connectDisble=true;

    
    $scope.showbutton=false;
    $scope.showbuttonDisc=true;

    
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
   }
  });
 }
 $scope.getTables = function(username, password, host, database, rdbmsName, dbschema) {
  $scope.tables = []; //declare an empty array
  $http.get("resources/getTables?userid=" + username + "&password=" + password + "&host=" + host + "&database=" + database + "&rdbmsName=" + rdbmsName + "&schema=" + dbschema + "").then(function(response) {
   $scope.tables = response.data; //ajax request to fetch data into $scope.data
   $scope.sourceTables = response.data.slice();
   $scope.tables.splice(0, 0, "Select All");
  });
 }
 $scope.sourceCols={};
 $scope.getCols = function(username, password, host, database, rdbmsName, tableName, dbschema) {
  // alert(tableName);
  $scope.cols = []; //declare an empty array
  if (tableName.length <= 1) {
   $http.get("/resources/getColumns?userid=" + username + "&password=" + password + "&host=" + host + "&database=" + database + "&rdbmsName=" + rdbmsName + "&tableName=" + tableName + "&schema=" + dbschema + "").then(function(response) {
    //   alert("http://10.174.132.12:8080/resources/getColumns?userid="+username+"&password="+password+"&host="+host+"&database="+database+"&rdbmsName="+rdbmsName+"&tableName="+tableName+"");
    $scope.cols = response.data; //ajax request to fetch data into $scope.data
    $scope.sourceCols = response.data.slice();
    $scope.cols.splice(0, 0, "Select All");
   });
  }
  $scope.srcclumn = $scope.cols;
  $scope.joinKeys = $scope.cols;
 }
 $scope.popup = function() {
  var modalInstance = $uibModal.open({
   controller: 'PopupCont',
   templateUrl: 'html/successpopup.html',
  });
 }
 $scope.changePartitinKey = function(tabletype) {
  //alert(tabletype);
  console.log('type is', tabletype);
  if (tabletype == 'NON-PARTITIONED') {
   $scope.isdisabled = true;
  } else {
   $scope.isdisabled = false;
  }
 }
 $scope.disableDrop = function(selectedItem) {
  if (selectedItem.length >= 2) {
   $scope.disabledMultiple = true;
   $scope.isdisabled = true;
   $scope.rdbms.hiveTableType = "NON-PARTITIONED";
   if ($scope.rdbms.targetType == "HDFS") {
    $scope.rdbms.targetTableName = "";
    $scope.rdbms.targetDBName = "";
    $scope.rdbms.hiveTableType = "";
    $scope.rdbms.targetPartitionKey = "";
   }
   //$scope.rdbms.hiveTableType=
  } else {
   $scope.disabledMultiple = false;
  }
 }
 $scope.postdata = function(rdbmsdata) {
  //alert($scope.rdbmsForm.$valid);
  if ($scope.rdbmsForm.$valid) {
   var data = rdbmsdata;
    $scope.dataLoading = true;
   //Call the services
   $http.post('codeGenRequests/create', JSON.stringify(data)).then(function(response) {
    if (response.data)
     $scope.msg = "Post Data Submitted Successfully!";
        $scope.dataLoading = false;
    var modalInstance = $uibModal.open({
     controller: 'PopupCont',
     templateUrl: 'html/successpopup.html',
    });
   }, function(response) {
    $scope.msg = "Service not Exists";
    $scope.dataLoading = false;
    $scope.statusval = response.status;
    $scope.statustext = response.statusText;
    $scope.headers = response.headers();
    var modalInstance = $uibModal.open({
     controller: 'PopupCont',
     templateUrl: 'html/errorpopup.html',
    });
   });
  } else {
   $scope.rdbmsForm.$invalid = 'true';
  }
 };
 
 $scope.postdataFile = function(fileData) {
	  //alert($scope.rdbmsForm.$valid);
	  if ($scope.fileForm.$valid) {
	   var data = fileData;
	    $scope.dataLoading = true;
	   //Call the services
	   $http.post('codeGenRequests/create', JSON.stringify(data)).then(function(response) {
	    if (response.data)
	     $scope.msg = "Post Data Submitted Successfully!";
	        $scope.dataLoading = false;
	    var modalInstance = $uibModal.open({
	     controller: 'PopupCont',
	     templateUrl: 'html/successpopup.html',
	    });
	   }, function(response) {
	    $scope.msg = "Service not Exists";
	    $scope.dataLoading = false;
	    $scope.statusval = response.status;
	    $scope.statustext = response.statusText;
	    $scope.headers = response.headers();
	    var modalInstance = $uibModal.open({
	     controller: 'PopupCont',
	     templateUrl: 'html/errorpopup.html',
	    });
	   });
	  } else {
	   $scope.fileForm.$invalid = 'true';
	  }
	 };
 
 $scope.hiveTableType = {};
 $scope.loadHiveTableType = function() {
  $http.get("resources/findByType?type=HIVETABLETYPE").then(function(response) {
   //  alert(response);
   $scope.hiveTableType = response.data; //ajax request to fetch data into $scope.data
  });
 }
 $scope.colRequiredchk = true;
 $scope.onSelcted2 = function(selectedItem) {
  if (selectedItem == 'Select All') {
   // alert($scope.tables);
   $scope.rdbms.hiveTableType = "NON-PARTITIONED";
   $scope.rdbms.sourceTableNames = $scope.sourceTables;
   $scope.selectedAllTables = !$scope.selectedAllTables;
   $scope.disabledMultiple = true;
   $scope.colRequiredchk = false;
   $scope.isdisabled = true;
    $scope.rdbms.sourceColumnNames="";
  } else {
   $scope.colRequiredchk = false;
  }
 }


 $scope.selectAllClass = function(selectedItem) {
  return selectedItem == 'Negative' ? 'warning' : 'ok';
 }
 $scope.selectAlltables = function(isChecked) {
  $scope.isSelectedFlag = isChecked;
  if (isChecked) {
   $scope.rdbms.sourceTableNames = $scope.sourceTables.slice();
  } else {
   $scope.rdbms.sourceTableNames = {};
  }
 }
 $scope.getHiveTableNames = function(tables) {
  var myselected = "";
  angular.forEach(tables, function(value) {
   myselected = myselected + value + ',';
   //  alert(myselected);
  });
  $scope.rdbms.targetTableName = myselected;
  // alert("assigned value is"+  $scope.rdbms.targetTableName);
  $scope.myvalue = myselected;
 }


$scope.onSelctedColumns = function(selectedItem) {
    // alert(selectedItem);
  if (selectedItem == 'Select All') {
   //alert('m in if');
  
   $scope.rdbms.sourceColumnNames = $scope.sourceCols;

   $scope.srcclumn = $scope.sourceCols;
  $scope.joinKeys = $scope.sourceCols;
 
  } else {
   $scope.colRequiredchk = false;
  }
 }


}]);
angular.module('utilityApp').controller('PopupCont', ['$scope', '$uibModalInstance', function($scope, $uibModalInstance) {
 $scope.close = function() {
  $uibModalInstance.dismiss('cancel');
 };
}]);