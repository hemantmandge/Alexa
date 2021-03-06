angular.module('utilityApp').controller(
		'RDBMSController',
		function($scope, $rootScope, $http, $uibModal, $location,ResourceService, $controller,$cookies) {
			var UtilityController = $scope.$new(); 
			$controller('utilityCtrl',{$scope : UtilityController });
			 $scope.codeGenRequest = {};
			 $scope.showbutton=true;
			 $scope.showbuttonDisc=false;
			 $scope.disabled = false;
			 $rootScope.isNotJobPage = true;
			 $scope.hivetableLabel = 'labelSubHeader';
			 $scope.hivetableDD = 'customDropDown';
			 $scope.class = "disabledlabel";
			 $scope.classdropdown="disabledDropDown";
			 $scope.classtextbox="disabledTextBox";
			 $scope.class1 = "disabledlabel";
			 $scope.classdropdown1="disabledDropDown";
			 $scope.class2 = "disabledlabel";
			 $scope.classdropdown2="disabledDropDown";
			 $scope.toggleTable = true;
			 $scope.toggleDisconnect = true;
			 $scope.tempClass = 'disabledTextBox';
			 $scope.disabledMultiple=true;
			 $scope.whereDisabled= true;
			 $scope.toggleAll = true;
			 $scope.isSelectedFlag = false;
			//$scope.dbconnectionRequired=false;
			 //$scope.dbNameRequired=false;
			// $scope.userNameRequired=false;
			 //$scope.passwordRequired=false;
			 $scope.dbSchemaREquired=false;
			 $scope.tableRequired=false;
			 $scope.colRequired=false;
			 $scope.caculateDeltaRequired=false;
			 $scope.joinKeyRequired=false;
			 $scope.loadTypeRequired=false;
			 $scope.targetconnRequired=false;
			 $scope.hivedbnameRequired=false;
			 $scope.hiveTableNameRequired=false;
			 $scope.hiveTableTypeRequired = false;
			 $scope.hivePartitionKeyRequired = false;
//			 $scope.isRequired=true;
			 //$scope.colRequiredchk=false;
			 $scope.archivePeriodRequired =false;
			 $scope.connectDisble=false;
			 $scope.srcTypeDisble =false;
			$scope.disableCreateButton=true;
			$scope.dbconnectionDisble=true;
			 $scope.dbnameDisble=true;
			 $scope.pkList = [];
			$scope.pageLoadDataRDBMS = function() {
                //load calls for dropdown and init functions
				$rootScope.homeRadioBtn = "RDBMS";
				$rootScope.currentSelected = "RDBMS";
				ResourceService.findByTypeAndName("DATASOURCE", "RDBMS").then(
						function(response) {
							$scope.datadbsrc = response.data;
						})

				ResourceService.findByType("TARGETCONNECTION").then(
						function(response) {
							$scope.targetcon = response.data;
						})

				$scope.codeGenRequest.loadType = "FULL";
				ResourceService.findByType("LOADTYPE").then(function(response) {
					$scope.loadType = response.data;
				});
				
				
				ResourceService.findByType("TARGETCONNECTION").then(
						function(response) {
							$scope.targetcon = response.data;
						});
			};
			
			 $scope.getDB = function(datasource) {
					 if(datasource!= undefined || datasource!= null || datasource != '')
						 {
						  $scope.dbconnectionDisble=false;
						  $scope.class1 = "labelSubHeader";
						  $scope.classdropdown1="customDropDown";
						 }
					 if(datasource== undefined || datasource== null || datasource == '')
					 {
						  $scope.dbconnectionDisble=true;
						  $scope.class1 = "disabledlabel";
						  $scope.classdropdown1="disabledDropDown";
					 }

			
					 ResourceService.findByName(datasource).then(function(response) {
						 $scope.datadbconn = response.data;
						});
				
				 };
				 
				 $scope.getSID = function(sourceSystem) {
					 // console.log(sourceSystem);
					 if(sourceSystem != undefined || sourceSystem != null || sourceSystem != '')
						 {
						 	$scope.dbnameDisble=false;
							 $scope.class2 = "labelSubHeader";
							 $scope.classdropdown2="customDropDown";
						 }
					 if(sourceSystem == undefined || sourceSystem == null || sourceSystem == '')
				        {
							 $scope.dbnameDisble=true;
							 $scope.class2 = "disabledlabel";
							 $scope.classdropdown2="disabledDropDown";
						}	 
				  ResourceService.findByName(sourceSystem).then(function(response) {
					  $scope.datadbSID = response.data;
					});
				  
				 };
				 
				 $scope.connectDB = function(username, password, host, dbName, rdbmsName) {
				//	 console.log($scope.rdbmsForm.$error.required);
					//$scope.dbconnectionRequired=true;
					 //$scope.dbNameRequired=true;
					// $scope.userNameRequired=true;
					 //$scope.passwordRequired=true;		
				  
					 
					 if($scope.rdbmsForm.$invalid){
							$scope.showValidationErrors = true;
							return false;
						}
				     $rootScope.dataLoading = true;
				     $rootScope.loadingBackgronud = true;
					 // $scope.databaseSchema = [];
				     
					 ResourceService.getSchemaDetails(username, password, host, dbName, rdbmsName)
					  .then(function(response) {
				     $scope.databaseSchema = response.data; // ajax request to
					   if (response.data) {
						   
						   // ng-req attributes
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
							 $scope.hiveTableTypeRequired = true;
//							 $scope.hivePartitionKeyRequired = true;
						   
								$scope.disableCreateButton=false;
					   $scope.toggleAll = false;
					   $scope.toggleDisconnect = true;
						 $scope.class = "labelSubHeader";
						 $scope.classdropdown="customDropDown";
						 $scope.classtextbox="customTextBox ";
						 $scope.disabledMultiple = false;
						 $scope.whereDisabled= false;
				    $rootScope.dataLoading = false;
				    $rootScope.loadingBackgronud = false;
				    $scope.connectDisble=true;
				    $scope.srcTypeDisble =true;
				    $scope.dbconnectionDisble=true;
				    $scope.dbnameDisble=true;
				 
				    $scope.showbutton=false;
				    $scope.showbuttonDisc=true;
				    $scope.rdbmsForm.$invalid = true;
				    //$scope.colRequiredchk=true;
				    
				    var modalInstance = $uibModal.open({
				     controller: 'PopupCont',
				     templateUrl: 'html/successpopupconnect.html',
				    });
				      $rootScope.loading = false;
				   } else {
				    var modalInstance = $uibModal.open({
				     controller: 'PopupCont',
				     templateUrl: 'html/errorpopupconnect.html',
				    });
				    $rootScope.dataLoading = false;
				    $rootScope.loadingBackgronud = false;
				   }
				  }) .catch(function (data) {

					  $rootScope.dataLoading = false;
					  $rootScope.loadingBackgronud = false;
					   var modalInstance = $uibModal.open({
				            controller: 'PopupCont',
				            templateUrl: 'html/errorpopupconnect.html',
				        });
						
				});
				 };
				 
				 $scope.getTables = function(username, password, host, database, rdbmsName, dbschema) {
					 $rootScope.dataLoading = true;
					 $rootScope.loadingBackgronud = true;
				  $scope.tables = []; // declare an empty array
				 
				  ResourceService.getTables(username,password,host , database , rdbmsName , dbschema )
				  .then(function(response) {
					  $scope.showUnionKeySelectAll=false;
				  $scope.tables = response.data; // ajax request to fetch
													// data into $scope.data
				   $rootScope.dataLoading = false;
				   $rootScope.loadingBackgronud = false;
				   if( $scope.tables.length>1)
					   {
					   $scope.sourceTables = response.data.slice();
					   $scope.tables.splice(0, 0, "Select All");
					   }
				   $scope.cols = null;
				  }).catch(function (data) {

					  $rootScope.dataLoading = false;
					  $rootScope.loadingBackgronud = false;
						
				});
				 };
				 
				 $scope.getCols = function(username, password, host, database, rdbmsName, tableName, dbschema) {
					 
					 $rootScope.dataLoading = true;
					 $rootScope.loadingBackgronud = true;
					  var tbllen =tableName.length;

					  $scope.cols = []; // declare an empty array
					  if (tableName.length <= 1) {
						  
					   ResourceService.getPrimaryKey(username, password , host , database, rdbmsName, tableName , dbschema)
					    .then(function(response) {
					    	$scope.pkList = response.data;
					    });
					   ResourceService.getColumns(username, password , host , database, rdbmsName, tableName , dbschema)
					   .then(function(response) {
						   $scope.showUnionKeySelectAll=false;
						   $rootScope.dataLoading = false;
							 $rootScope.loadingBackgronud = false;
							 
					    $scope.cols = response.data; // ajax request to fetch
														// data into $scope.data
					    if(tbllen==1&&   $scope.cols.length>1){
					    	   $scope.sourceCols = response.data.slice();
					      
					      $scope.cols.splice(0, 0, "Select All");
					    }
					   
					   }).catch(function (data) {

							  $rootScope.dataLoading = false;
							  $rootScope.loadingBackgronud = false;
								
						});
					  }
					 };
					 
					 $scope.onSelctedColumns = function(selectedItem) {
							console.log(selectedItem);
							if (selectedItem == 'Select All') {
						   $scope.codeGenRequest.sourceColumnNames = $scope.sourceCols;
						  }
							if (selectedItem.length > 1) {
								$scope.showUnionKeySelectAll=true;
							} else {
								$scope.showUnionKeySelectAll=false;
							}
						 };
					$scope.onSelctedJoinKey = function(selectedItem) {
								console.log(selectedItem);
								if (selectedItem == 'Select All') {
									$scope.codeGenRequest.joinKeys = $scope.codeGenRequest.sourceColumnNames;
							  }
								
							 };

						 
						 $scope.loadHiveTableType = function() {ResourceService.findByType("HIVETABLETYPE").then(function(response) {
							 $scope.hiveTableType = response.data;
							});
						 };
						 
						 $scope.disableDrop = function(selectedItem) {
							  if (selectedItem.length >= 2) {
								  $rootScope.dataLoading = false;
								  $rootScope.loadingBackgronud = false;
								  
							   $scope.disabledMultiple = true;
							   $scope.isdisabled = true;
							   $scope.codeGenRequest.targetTableType ="NON-PARTITIONED";
							  
							  } else {
							   $scope.disabledMultiple = false;
							  }
							  if (selectedItem.length == 1) 
								  {
								  $scope.hivetabledisabled=true;
								  }
							  if( $scope.codeGenRequest.sourceTableNames.length > 1 || $scope.codeGenRequest.sourceTableNames.indexOf("Select All") > -1)
								{
								   $scope.toggleTable = false;
								}
							  else
							  {
								  $scope.toggleTable = true;
							  }
							  
							 };
							 
							 $scope.changePartitinKey = function(tabletype) {
								  if (tabletype == 'NON-PARTITIONED') {
									  $scope.codeGenRequest.targetPartitionKey="";
								     $scope.isdisabled = true;
									 $scope.hivetabledisabled=false;
									 $scope.hivetableLabel = 'disabledlabel';
									 $scope.hivetableDD = 'disabledDropDown';
									 $scope.hivePartitionKeyRequired =false;
									 //$scope.colRequiredchk=false;
								 
								  } else {
									  
								   $scope.isdisabled = false;
								   $scope.hivePartitionKeyRequired =true;
								   $scope.hivetableLabel = 'labelSubHeader';
									 $scope.hivetableDD = 'customDropDown';
								  }
								 } ;
								 
								 $scope.onSelcted2 = function(selectedItem) {
									  if (selectedItem == 'Select All') {
									   $scope.codeGenRequest.targetTableType = "NON-PARTITIONED";
									   $scope.codeGenRequest.sourceTableNames = $scope.sourceTables;
									   $scope.disabledMultiple = true;
									   $scope.hivetabledisabled=true;
									   $scope.isRequired =false;
									   //$scope.colRequiredchk=false;
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
									 };
									 
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
										 } ;
										 
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
											 };
											 
											 
											 $scope.postdata = function(codeGenRequest) {
												 
												 
												 
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
												 $scope.hiveTableTypeRequired = true;
//												 $scope.hivePartitionKeyRequired = true;

												 if($scope.rdbmsForm.$invalid){
														$scope.showValidationErrors = true;
														return false;
												}
												 
												 if($rootScope.showArchiveValidation)
													{
													return false;
													}
												 $rootScope.dataLoading = true;
												 $rootScope.loadingBackgronud = true;
														   $rootScope.submitted = true;
														   var data = codeGenRequest;
														   
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
																.then($scope.RDBMS_success, UtilityController.createCodeGenRequestFailed)
																.catch(UtilityController.catchCreateCodeGenRequestError);
															   }
														   }).catch(function (data) {

																  $rootScope.dataLoading = false;
																  $rootScope.loadingBackgronud = false;
																	
															});
														   
														   
														
											 };
											 
											
											 
												$scope.RDBMS_success=function(data)
												{
													UtilityController.createCodeGenRequestSuccess(data);
													 var sourceSystem  =$scope.codeGenRequest.sourceSystem;
													 var dbConnection  = $scope.codeGenRequest.dbConnection;
													 var dbName  = $scope.codeGenRequest.dbName;
													 var username  =$scope.codeGenRequest.username;
													 var password  = $scope.codeGenRequest.password;
													$scope.codeGenRequest = {};
													
													$scope.codeGenRequest.sourceSystem =sourceSystem;
													$scope.codeGenRequest.dbConnection =dbConnection;
													$scope.codeGenRequest.dbName=dbName;
													$scope.codeGenRequest.username =username;
													$scope.codeGenRequest.password=password;
													
													  $scope.tables =null;
													  $scope.cols=null;
													  $scope.showUnionKeySelectAll=false;
													  //$scope.showbuttonDisc=false;
													 // $scope.showbutton=true;
													  $scope.srcTypeDisble =false;
													  $scope.connectDisble=false;
													  $scope.hivePartitionKeyRequired = false;
														$scope.codeGenRequest.loadType = "FULL";
														$scope.showValidationErrors = false; 
												};
											 
										 
											 $scope.enableForm =function(){
												// console.log($scope.rdbmsForm.$error.required);
												 $scope.codeGenRequest = {};
												 $scope.disableCreateButton=true;
							
											  
												 $scope.tables  =null;
												 $scope.cols=null;
												 //ng-required attributes
												 $scope.dbSchemaREquired=false;
												 $scope.tableRequired=false;
												 $scope.colRequired=false;
												 $scope.caculateDeltaRequired=false;
												 $scope.joinKeyRequired=false;
												 $scope.loadTypeRequired=false;
												 $scope.targetconnRequired=false;
												 $scope.hivedbnameRequired=false;
												 $scope.hiveTableNameRequired=false;
												 $scope.hiveTableTypeRequired = false;
												 $scope.hivePartitionKeyRequired = false;
												 //$scope.colRequiredchk=false;
												 $scope.archivePeriodRequired =false;
												 
												 $scope.toggleAll = true;
												 $scope.toggleDisconnect = false;
												// $scope.disabledMultiple = false;
												 $scope.whereDisabled= true;
												 $scope.class = "disabledlabel";
												 $scope.classdropdown="disabledDropDown";
												 $scope.classtextbox="disabledTextBox";
												if($scope.showbuttonDisc == true){
												         $scope.showbutton=true;
												         $scope.showbuttonDisc=false;
												          $scope.connectDisble=false;
												          $scope.srcTypeDisble =false;
												          $scope.dbconnectionDisble=false;
												          $scope.dbnameDisble=false;
												
												    }
											    
											};			 
											 $scope.onCancel = function() {
												 $scope.disableCreateButton=true;
												 $scope.dbSchemaREquired=false;
												 $scope.tableRequired=false;
												 $scope.colRequired=false;
												 $scope.caculateDeltaRequired=false;
												 $scope.joinKeyRequired=false;
												 $scope.loadTypeRequired=false;
												 $scope.targetconnRequired=false;
												 $scope.hivedbnameRequired=false;
												 $scope.hiveTableNameRequired=false;
												 $scope.hiveTableTypeRequired = false;
												 $scope.hivePartitionKeyRequired = false;
												 //$scope.colRequiredchk=false;
												 $scope.archivePeriodRequired =false;
//												 $scope.toggleAll = true;
												  $scope.codeGenRequest = {};
												  $scope.tables =null;
												  $scope.cols=null;
												  $scope.showbuttonDisc=false;
												  $scope.showbutton=true;
												  $scope.srcTypeDisble =false;
												  $scope.connectDisble=false;
												  $scope.codeGenRequest.loadType = "FULL";
												  $scope.disabled = false;
													 $scope.hivetableLabel = 'labelSubHeader';
													 $scope.hivetableDD = 'customDropDown';
													 $scope.class = "disabledlabel";
													 $scope.classdropdown="disabledDropDown";
													 $scope.classtextbox="disabledTextBox";
													 $scope.class1 = "disabledlabel";
													 $scope.classdropdown1="disabledDropDown";
													 $scope.class2 = "disabledlabel";
													 $scope.classdropdown2="disabledDropDown";
													 $scope.toggleTable = true;
													 $scope.toggleDisconnect = true;
													 $scope.tempClass = 'disabledTextBox';
													 $scope.disabledMultiple=true;
													 $scope.whereDisabled= true;
													 $scope.toggleAll = true;
														$scope.dbconnectionDisble=true;
														 $scope.dbnameDisble=true;
														  $scope.showUnionKeySelectAll=false;
												 }

		});