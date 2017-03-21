bdtApp.controller('homeController', function($scope, UserService) {
	$scope.showLogin = false;
	$scope.userInfo = {};
	UserService.getUser().then(function(user) {
		$scope.userInfo = user;
	});
});