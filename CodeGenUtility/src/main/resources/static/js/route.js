app.config(function($routeProvider, $locationProvider){
	$locationProvider.hashPrefix('');
	$routeProvider		
       . when('/',{
            templateUrl : 'html/login.html',
            controller:'utilityCtrl'
        })
        .when('/Files',{
            templateUrl : 'html/requestForm.html',
            controller:'FileController'
        })
        .when('/RDBMS',{
            templateUrl : 'html/requestFormRDBMS.html',
            controller:'RDBMSController'
        })
        .when('/Hadoop',{
            templateUrl : 'html/requestFormHadoop.html',
            controller:'HadoopController'
        })
        .when('/job',{
            templateUrl : 'html/jobstatus.html',
            controller:'utilityCtrl'
        })
        .when('/S3',{
            templateUrl : 'html/requestFormS3.html',
            controller:'utilityCtrl'
        })
		.otherwise({
			redirectTo: '/'
		})
});
