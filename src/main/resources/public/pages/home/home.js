bdtApp.controller('homeController', function($scope, UserService, HttpService, ModalService) {
	$scope.showLogin = false;
	$scope.userInfo = {};
	UserService.getUser().then(function(res) {
		$scope.userInfo = res;
	});
	$scope.roleAll = [];
	$scope.currentPage = 1; // 当前页
	// 获取所有角色，和用户角色
	$scope.getRoles = function() {
		HttpService.post("/role/findRole", {
			pageNum : $scope.currentPage
		}).then(function(res) {
			if (res.code == 0) {
				var roles=res.data.roles;
				var myRoles=res.data.myRoles;
				$scope.isLastPage = roles.isLastPage;
				var myRoleStatus={};
				for(var i=0;i<myRoles.length;i++){
					myRoleStatus[myRoles[i].code]=myRoles[i].myStatus;
				}
				for (var i = 0; i < roles.list.length; i++) {
					var data=roles.list[i];
					data.myStatus=myRoleStatus[data.code];
					if(data.myStatus==null || data.status==0){
						data.btnTitle="申请";
					}else if(data.myStatus==1){
						data.btnTitle="已有";
					}else if(data.myStatus==2){
						data.btnTitle="申请中"
					}else if(data.myStatus==3){
						data.btnTitle="重新申请";
					}
					$scope.roleAll.push(data);
				}
			} else {
				ModalService.toastr.error("网络错误");
			}
		});
	}
	// 加载更多
	$scope.loadMoreRole = function() {
		$scope.currentPage += 1;
		$scope.getRoles();
	}
	// 申请权限
	$scope.reqRole = function(role) {
		var list=$scope.roleAll;
		HttpService.post("/employee/getEmployeeByRole",{roleCode:'admin'}).then(function(res){
			if(res.code==0){
				ModalService.selectList({
					title : '请选择申请人',
					listTitle : [
						{title : '姓名',code : 'name'},
						{title:'手机号',code:'mobile'},
						],
						list:res.data.list
				}, function success(res) {
					if(res.id!=null){
						HttpService.post("/role/reqRole",{roleId:role.id,empId:res.id}).then(function(res){
							ModalService.toastr.success("申请成功");
							$scope.roleAll = [];
							$scope.currentPage = 1; // 当前页
							$scope.getRoles();
						}).then(function(res){
							ModalService.toastr.error(res.msg);
						});
					}
				});
			}else{
				ModalService.toastr.error(res.msg);
			}
		})
	}
	$scope.getRoles();
});