

app.config(function($routeProvider, $locationProvider){
	$locationProvider.hashPrefix('');
	$routeProvider
		
       . when('/',{
            templateUrl : 'html/login.html',
            controller:'utilityCtrl'
        })
        .when('/home',{
			templateUrl : 'html/home.html',
            controller:'utilityCtrl'
		})
       
         .when('/login',{
            templateUrl : 'html/login.html',
            controller:'utilityCtrl'
        })
         .when('/RDBMS',{
            templateUrl : 'html/requestFormRDBMS.html',
            controller:'utilityCtrl'
        })
        .when('/Hadoop',{
            templateUrl : 'html/requestFormHadoop.html',
            controller:'utilityCtrl'
        })
        .when('/Files',{
            templateUrl : 'html/requestForm.html',
            controller:'utilityCtrl'
        })
        .when('/job',{
            templateUrl : 'html/jobstatus.html',
            controller:'utilityCtrl'
        })
        .when('/S3',{
            templateUrl : 'html/requestFormS3.html',
            controller:'utilityCtrl'
        })
        .when('/popup',{
            templateUrl : 'html/successpopup.html',
            controller:'utilityCtrl'
        })
        
		.otherwise({
			redirectTo: '/'
		})


    //  $locationProvider.html5Mode(true);
});



