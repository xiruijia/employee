var bdtApp = angular.module("bdt-app", [ 'ui.router', 'ui.bootstrap' ]);
bdtApp.value('config', {
	title : '半导体郗'
});
bdtApp.url = "www.test.bandaotixi.cc";
bdtApp.getUrl = "";
bdtApp.postUrl = "";
bdtApp.config([ '$qProvider', function($qProvider) {
	$qProvider.errorOnUnhandledRejections(false);
} ]);
toastr.options = {
	"closeButton" : true,
	"debug" : true,
	"newestOnTop" : true,
	"progressBar" : true,
	"positionClass" : "toast-top-center",
	"preventDuplicates" : true,
	"showDuration" : "300",
	"hideDuration" : "1000",
	"timeOut" : "3000",
	"extendedTimeOut" : "1000",
	"showEasing" : "swing",
	"hideEasing" : "linear",
	"showMethod" : "fadeIn",
	"hideMethod" : "fadeOut"
}
bdtApp.config(function($logProvider, $stateProvider, $locationProvider, $urlRouterProvider) {
	$logProvider.debugEnabled(true);
	$locationProvider.hashPrefix('');
	$urlRouterProvider.when("", "/home");
	$stateProvider.state('home', {
		url : '/home',
		templateUrl : '../pages/home/home.html',
		controller : 'homeController' // 也可以写成HomeController as home
	}).state('about', {
		url : '/about',
		templateUrl : '../pages/about.html'
	}).state('user', {
		url : '/user/:type',
		templateUrl : '../pages/user/user.html',
		controller : 'userController' // 也可以写成HomeController as home
	})
});