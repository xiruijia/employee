bdtApp.controller('homeController', function($scope, UserService) {
	$scope.showLogin = false;
	$scope.userInfo = {};
	UserService.getUser().then(function(res) {
		$scope.userInfo = res;
	});
});