var bdtApp = angular.module("bdt-app", [
    'ui.router'
]);
bdtApp.value('config',{title:'半导体郗'});
bdtApp.url="www.test.bandaotixi.cc";
bdtApp.getUrl="";
bdtApp.postUrl="";

bdtApp.config(function ($logProvider,$stateProvider,$locationProvider,$urlRouterProvider) {
	$logProvider.debugEnabled(true);
	$locationProvider.hashPrefix('');
	$urlRouterProvider.when("", "/home");
    $stateProvider
    .state('home',{
    	url:'/home',
        templateUrl: '../pages/home/home.html',
        controller: 'homeController' //也可以写成HomeController as home
    })
    .state('about',{
    	url:'/about',
        templateUrl: '../pages/about.html'
    })
    .state('user',{
    	url:'/user/:type',
        templateUrl: '../pages/user/user.html',
        controller: 'userController' //也可以写成HomeController as home
    })
   /* $routeProvider
        .when('/home', {
            templateUrl: '../pages/home/home.html',
            controller: 'homeController'
        })
        .when('/about', {templateUrl: '../pages/about.html'})
        .when('/user/:type', {//登录
            templateUrl: '../pages/user/user.html',
            controller: 'userController'
        })
        .otherwise({redirectTo: '/home'});*/
});