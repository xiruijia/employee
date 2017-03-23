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

	$scope.placement = {
		options : [ 5, 10, 20, 50 ],
		pageSize : 5,
		selected : 20,
		changeFn : function(res) {
			// 执行的函数
			console.log($scope.placement.selected);
		}
	};

	// 翻页
	$scope.totalItems = 1000; // 所有页面中的项目总数
	$scope.currentPage = 4; // 当前页
	$scope.setPage = function(pageNo) {
		$scope.currentPage = pageNo;
	};
	$scope.maxSize = 5;
});