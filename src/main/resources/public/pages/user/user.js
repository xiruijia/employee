bdtApp.controller('userController', function($scope, HttpService, UserService) {
	$scope.step = 'login';
	$scope.name = "";
	$scope.password = "";
	$scope.hideError = true;// 错误时，显示错误信息,false显示
	$scope.errorMsg = "";
	$scope.user = user;
	$scope.passwordType='password';
	UserService.getUser().then(function(res) {
		$scope.user = res;
		$scope.step = 'user'
	});
	// 登录
	$scope.login = function() {
		$scope.hideError = true;
		HttpService.post("/employee/login",$scope.user).then(function(res) {
			if (res.code == 0) {
				$scope.hideError = true;
				user = res.data;
				$scope.user=user;
				$scope.step = 'user';
			} else {
				$scope.hideError = false;
				$scope.errorMsg = res.msg;

			}
		});
	}
	$scope.checkMobile = function() {
		 if((/^1[3|4|5|8][0-9]\d{4,8}$/.test($scope.name))){
			 //手机号
		 }
		console.log($scope.username)
	}
	// 注册
	$scope.register = function() {

	}
	$scope.logout=function(){
		HttpService.get("/employee/logout").then(function(res){
			if(res.code==0){
				user=null;
				$scope.user=null;
				$scope.step='login';
			}
		});
	}
	
	$scope.showPassword=function(){
		$('input[ng-model="user.password"]').attr("type","text");
	}
	$scope.hidePassword=function(){
		$('input[ng-model="user.password"]').attr("type","password");
	}
});