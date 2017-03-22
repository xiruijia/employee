bdtApp.controller('userController', function($scope,$stateParams, HttpService, UserService,ModalService) {
	$scope.step = $stateParams.type;
	$scope.name = "";
	$scope.password = "";
	$scope.hideError = true;// 错误时，显示错误信息,false显示
	$scope.errorMsg = "";
	$scope.user = user;
	$scope.roles={};
	UserService.getUser().then(function(res) {
		$scope.user = res.user;
		$scope.roles=res.roles;
		$scope.step = 'user';
	});
	// 登录
	$scope.login = function() {
		$scope.hideError = true;
		HttpService.post("/employee/login",$scope.user).then(function(res) {
			if (res.code == 0) {
				$scope.hideError = true;
				$scope.user=res.data.user;
				$scope.roles=res.data.roles;
				$scope.step = 'user';
			} else {
				$scope.hideError = false;
				$scope.errorMsg = res.msg;

			}
		});
	}
	//检查注册类型
	$scope.checkMobile = function() {
		$scope.hideError = true;
		$scope.user.type='';
		if($scope.user==null)$scope.user={};
		 if((/^1[3|4|5|8][0-9]\d{8}$/.test($scope.user.name))){
			 //手机号
			 $scope.user.type='mobile';
		 }else if(/[\w!#$%&'*+/=?^_`{|}~-]+(?:\.[\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\w](?:[\w-]*[\w])?\.)+[\w](?:[\w-]*[\w])?/.test($scope.user.name)){
			 //邮箱
			 $scope.user.type='email';
		 }else if(/^(\d{6})(\d{4})(\d{2})(\d{2})(\d{3})([0-9]|X)$/.test($scope.user.name)){
			 //身份证
			 $scope.user.type='idcard';
		 }else if(!(/[\u4e00-\u9fa5]/.test($scope.user.name) || /\s/.test($scope.user.name))){
			 //code
			 $scope.user.type='code';
		 }else{
			 //错误
			 $scope.hideError = false;
			 $scope.errorMsg = "格式错误";
		 }
	}
	// 注册
	$scope.register = function() {
		HttpService.post("/employee/register",$scope.user).then(function(res) {
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
	//注销
	$scope.logout=function(){
		HttpService.get("/employee/logout").then(function(res){
			if(res.code==0){
				user=null;
				$scope.user=null;
				$scope.step='login';
			}
		});
	}
	//删除角色
	$scope.delRole=function(role){
		ModalService.yesno({content:'是否删除角色:'+role.name},
			function(role){
			HttpService.post("/employee/delRole",role).then(function(res) {
				if (res.code == 0) {
					debugger
				} else {
					debugger
				}
			});
			})
	}
	$scope.showPassword=function(){
		$('input[ng-model="user.password"]').attr("type","text");
	}
	$scope.hidePassword=function(){
		$('input[ng-model="user.password"]').attr("type","password");
	}
});