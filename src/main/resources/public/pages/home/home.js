bdtApp.controller('homeController', function($scope, UserService, HttpService,
		ModalService) {
	$scope.showLogin = false;
	$scope.userInfo = {};
	UserService.getUser().then(function(res) {
		$scope.userInfo = res;
	});
	$scope.roleAll = [];
	// 获取所有角色，和用户角色
	$scope.getRoles = function() {
		HttpService.post("/role/findRole", {
			pageNum : 1
		}).then(function(res) {
			if (res.code == 0) {
				$scope.roleAll = res.data.list;
				$scope.myRoles = res.myRoles;
			} else {
				ModalService.toastr.error("网络错误");
			}
		});
	}
	$scope.getRoles();

	// 翻页
	$scope.totalItems = 1000; // 所有页面中的项目总数
	$scope.currentPage = 4; // 当前页
	$scope.maxSize = 8;
});