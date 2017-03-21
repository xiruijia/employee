var bdtApp = angular.module("bdt-app", [
    'ngRoute'
]);
bdtApp.value('config',{title:'半导体郗'});
bdtApp.url="www.test.bandaotixi.cc";
bdtApp.getUrl="";
bdtApp.postUrl="";

bdtApp.config(function ($routeProvider, $locationProvider) {
    $locationProvider.hashPrefix('');
    $routeProvider
        .when('/home', {
            templateUrl: '../pages/home/home.html',
            controller: 'homeController'
        })
        .when('/about', {templateUrl: '../pages/about.html'})
        .when('/operators/addbanner/:id', {
            templateUrl: '/page/app/operators/banner/addbanner.html',
            controller: 'addBannerCtrl'
        })
        .when('/user', {//登录
            templateUrl: '../pages/user/user.html',
            controller: 'userController'
        })
        .otherwise({redirectTo: '/home'});
});