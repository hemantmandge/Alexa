var app = angular.module('utilityApp', [ 'ngRoute', 'ui.bootstrap',
		'utility.config', 'ngPatternRestrict', 'ngCookies','720kb.datepicker']);
app.controller('utilityCtrl', [
		'$scope',
		'$rootScope',
		'$window',
		'$location',
		'$http',
		'$uibModal',
		'$cookies',
		'$cookieStore',
		function($scope, $rootScope, $window, $location, $http, $uibModal,
				$cookies, $cookieStore) {
			$rootScope.loading = false;
			$scope.showLoginValidationErrors = false;
			$rootScope.isDirty = false;
			$rootScope.showArchiveValidation=false;
			$scope.loadinitialLogin = function() {
				if ($rootScope.isAuthenticated) {
					$location.path("/RDBMS");
					$rootScope.homeRadioBtn = "RDBMS";
					$rootScope.isNotJobPage = true;
				}
			};
			function loginSuccessCallback(loginData) {
				if (loginData.data.username) {
					$rootScope.homeRadioBtn = "RDBMS";
					$scope.error = false;
					$rootScope.isAuthenticated = true;
					$rootScope.isNotJobPage = true;
					var today = new Date();
					var expirationTime = new Date(today);
					// Set 'expires' option in 15 minute
					expirationTime.setMinutes(today.getMinutes() + 30);
					$cookies.putObject('userName', loginData.data, {
						expires : expirationTime
					});
					$location.path("/RDBMS");
				} else {
					$scope.error = true;
					$rootScope.isAuthenticated = false;
					$rootScope.isNotJobPage = true;
					$location.path("/");
				}
			}
			;
			function loginErrorCallback(data) {
				$scope.error = true;
				$rootScope.isAuthenticated = false;
				$rootScope.isNotJobPage = true;
				$scope.showLoginValidationErrors = true;
				$location.path("/");
				alert("User Name or Password is incorrect.");
			}
			$rootScope.logout = function() {
				$http.get("logout").then(function(response) {
					$rootScope.isAuthenticated = false;
					$rootScope.isNotJobPage = true;
					$cookies.remove("userName");
					$location.path("/");
				});
			}
			$scope.SubmitLogin = function(credentials) {
				var config = {
					headers : {
						'Content-Type' : 'application/x-www-form-urlencoded'
					}
				};

				var params = {
					username : credentials.username,
					password : credentials.password
				};
				$http({
					url : 'authenticate',
					method : 'POST',
					params : params,
					headers : config
				}).then(loginSuccessCallback, loginErrorCallback);
			};

			$scope.createCodeGenRequestSuccess = function(response) {
				$rootScope.dataLoading = false;
				$rootScope.loadingBackgronud = false;
				if (response.data)
					$scope.msg = "Post Data Submitted Successfully!";
				var modalInstance = $uibModal.open({
					controller : 'PopupCont',
					templateUrl : 'html/successpopup.html',
				});
			};

			$scope.createCodeGenRequestFailed = function(response) {
				$scope.msg = "Service not Exists";
				$rootScope.dataLoading = false;
				$rootScope.loadingBackgronud = false;
				$rootScope.statusval = response.status;
				$rootScope.statustext = response.statusText;
				$rootScope.headers = response.headers();
				var modalInstance = $uibModal.open({
					controller : 'PopupCont',
					templateUrl : 'html/errorPopup.html',
				});
			};

			$scope.catchCreateCodeGenRequestError = function(data) {
				$rootScope.dataLoading = false;
				$rootScope.loadingBackgronud = false;
				var modalInstance = $uibModal.open({
					controller : 'PopupCont',
					templateUrl : 'html/errorPopup.html',
				});
			};

			$rootScope.callHome =function()
			{
				$rootScope.isDirty=false;
			};
		
			$rootScope.checkarchivePeriod=function(archivePeriod)
			{
				//alert("hi------"+archivePeriod);
				if(Number(archivePeriod)>Number(365))
					{
		//		alert("Archive Period should be in between 1 to 365" +$rootScope.showArchiveValidation);
				$rootScope.showArchiveValidation=true;
					}
				else
					{
					$rootScope.showArchiveValidation=false;
					}
			};
		
				$rootScope.callJobStatus=function()
			{
				if(($scope.rdbmsForm && $scope.rdbmsForm.$dirty)
				|| ($scope.hadoopForm && $scope.hadoopForm.$dirty)
				|| ($scope.fileForm && $scope.fileForm.$dirty))
					{
					 $rootScope.isDirty=true;
					}
				else
					{
					 $rootScope.isDirty=false;
					}
			};
			$rootScope.redirectToPage = function(homeRadioBtn) {
				$rootScope.isDirty = ($scope.rdbmsForm && $scope.rdbmsForm.$dirty)
					|| ($scope.hadoopForm && $scope.hadoopForm.$dirty)
					|| ($scope.fileForm && $scope.fileForm.$dirty);
				if (homeRadioBtn == 'RDBMS') {
					$location.path('/RDBMS');
					$rootScope.isNotJobPage = true;
				} else if (homeRadioBtn == 'Hadoop') {
					$location.path('/Hadoop');
					$rootScope.isNotJobPage = true;
				} else if (homeRadioBtn == 'Files') {
					$location.path('/Files');
					$rootScope.isNotJobPage = true;
				}
			};
		} ]);
		
app.run([ '$rootScope', '$cookies', '$location',
		function($rootScope, $cookies, $location) {
			$rootScope.$on('$routeChangeStart', function(event) {
				if ($cookies.getObject('userName')) {
					$rootScope.isAuthenticated = true;
					$rootScope.isNotJobPage = true;
				} else {
					$rootScope.isAuthenticated = false;
					$rootScope.isNotJobPage = true;
					$location.path("/");
				}
			//	$rootScope.redirectToPage("");
				if ($rootScope.isDirty) {
					var isNavigate = confirm("You have made changes on the screen. If you navigate to other screen your changes will be lost. Are you sure you want to navigate ?");
					if (!isNavigate) {
						$rootScope.homeRadioBtn = $rootScope.currentSelected;
						event.preventDefault();
						return;
					}
				}
			});
		} ]);
angular.module('utilityApp').controller('PopupCont',
		function($scope, $uibModalInstance, $location) {
			$scope.close = function() {
				$uibModalInstance.close();
			};
			$scope.navigateToJobStatusPage = function() {
				$uibModalInstance.close();
				$location.path("/job");
			};
		});

angular.module('utilityApp').controller('JobStatusPopUp',
		function($scope, $uibModalInstance, param) {
			$scope.job = param.data;
			$scope.close = function() {
				$uibModalInstance.close();
			};
		});