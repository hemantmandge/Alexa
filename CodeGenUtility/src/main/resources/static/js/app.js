var app = angular.module('utilityApp', ['ngRoute', 'angularUtils.directives.dirPagination', 'ui.bootstrap', 'utility.config','ngPatternRestrict','ngCookies']);
app.controller('utilityCtrl', ['$scope', '$rootScope','$window', '$location', '$http', '$uibModal','$cookies','$cookieStore',
	function($scope, $rootScope, $window, $location, $http, $uibModal,$cookies, $cookieStore) {
 $rootScope.loading = false;
 $scope.showLoginValidationErrors = false;
 $scope.loadinitialLogin= function() {
	 
	 if ($rootScope.isAuthenticated) { // if user is authenticated and $rootScope.isAuthenticated is not undefined call loadRDBMSData
		 $rootScope.homeRadioBtn = "RDBMS";
		 $location.path("/RDBMS");	
		 $scope.loadLoginInit();
	    } 
     else { // if $rootScope.isAuthenticated is undefined (It may required to have explicit check for undefined)
	    	 if($cookies.getObject('userName')) {
	     		$rootScope.isAuthenticated = true;
	     		$rootScope.homeRadioBtn = "RDBMS";
			    $location.path("/RDBMS");
			    $scope.loadLoginInit();
	     	}
	     	else {
	     		$rootScope.isAuthenticated = false;
			    $location.path("/");
	     	}	
	    }
	 };

     $scope.loadLoginInit = function() {
    	 if( $rootScope.isAuthenticated )
    	 { 
    		 $location.path("/RDBMS");
    		 $rootScope.homeRadioBtn = "RDBMS";
    	 }
     };	

	 function loginSuccessCallback(data){
		 if (data) {
			 $rootScope.homeRadioBtn = "RDBMS";
			 $location.path("/RDBMS");
			 $scope.error = false;
			 $rootScope.isAuthenticated = true;
			 $cookieStore.put('userName',data.data);
		 } else {
			 $location.path("/");
			 $scope.error = true;
			 $rootScope.isAuthenticated = false
		 }
	 };
	 function loginErrorCallback(data){
		 $location.path("/");
		 $scope.error = true;
		 $rootScope.isAuthenticated = false;
		 $scope.showLoginValidationErrors = true;
	 }
 	 $scope.SubmitLogin = function(credentials){
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
	  
	$scope.createCodeGenRequestSuccess = function(response){
		$rootScope.dataLoading = false;
		$rootScope.loadingBackgronud = false; 
		if (response.data)
			$scope.msg = "Post Data Submitted Successfully!";
		var modalInstance = $uibModal.open({
			controller: 'PopupCont',
			templateUrl: 'html/successpopup.html',
		});
	};
	
	$scope.createCodeGenRequestFailed = function(response){
		$scope.msg = "Service not Exists";
		$rootScope.dataLoading = false;
		$rootScope.loadingBackgronud=false;
		$rootScope.statusval = response.status;
		$rootScope.statustext = response.statusText;
		$rootScope.headers = response.headers();
		var modalInstance = $uibModal.open({
			controller: 'PopupCont',
			templateUrl: 'html/errorpopup.html',
		});
	};
	
	$scope.catchCreateCodeGenRequestError = function (data){
		$rootScope.dataLoading = false;
		$rootScope.loadingBackgronud = false;
		var modalInstance = $uibModal.open({
			controller: 'PopupCont',
			templateUrl: 'html/errorpopup.html',
        });
	};
	
	 $rootScope.redirectToPage = function(homeRadioBtn){
	     if (homeRadioBtn == 'RDBMS') {
	         $location.path('/RDBMS');
	     } else if (homeRadioBtn == 'Hadoop') {
	         $location.path('/Hadoop');
	     } else if (homeRadioBtn == 'Files') {
	         $location.path('/Files');
	     } else if (homeRadioBtn == 'S3') {
	         $location.path('/S3');
	     }
	 };
}]);

angular.module('utilityApp').controller('PopupCont', function($scope, $uibModalInstance){
	$scope.close = function() {
	$uibModalInstance.dismiss('cancel');
};
});