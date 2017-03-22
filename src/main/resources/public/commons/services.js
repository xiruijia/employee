var user = null;
bdtApp.service(
		'UserService',
		function($rootScope, HttpService, $location, $q) {
			var service = {};
			service.getUser = function() {
				var deferred = $q.defer();
				HttpService.post("/employee/getEmployee", {}).then(
						function(res) {
							if (res.code == 0) {
								if (res.data.user.mobile != null
										&& res.data.user.mobile != '') {
									res.data.user.type += 'mobile,';
								}
								if (res.data.user.email != null
										&& res.data.user.email != '') {
									res.data.user.type += 'email,';
								}
								if (res.data.user.idcard != null
										&& res.data.user.idcard != '') {
									res.data.user.type += 'idcard,';
								}
								if (res.data.user.code != null
										&& res.data.user.code != '') {
									res.data.user.type += 'code,';
								}
								deferred.resolve(res.data);
							} else {
								// 跳转到登录页面
								$location.path("/user/login");
							}
						});
				return deferred.promise;
			}
			service.register = function(user) {
				var deferred = $q.defer();
				HttpService.post("/employee/register", {}).then(function(res) {
					if (res.code == 0) {
						user = res.data;
						deferred.resolve(res.data);
					} else {
						// 跳转到登录页面
						$location.path("/user");
					}
				});
				return deferred.promise;
			}
			return service;
		})

.service('HttpService', function($rootScope, $http, $q) {
	var http = {};
	var service = {};
	service.get = function(url, params) {
		var deferred = $q.defer(); // 声明延后执行，表示要去监控后面的执行
		var a = $http({
			method : 'GET',
			url : bdtApp.postUrl + url,
			params : params
		}).then(function(res) {
			if (res.status == 200) {
				deferred.resolve(res.data);
			} else {
				console.log("接口调用失败")
				deferred.reject(res);
			}
		});
		return deferred.promise;
	}

	service.post = function(url, params) {
		var deferred = $q.defer(); // 声明延后执行，表示要去监控后面的执行
		var a = $http({
			method : 'POST',
			url : bdtApp.postUrl + url,
			params : params
		}).then(function(res) {
			if (res.status == 200) {
				deferred.resolve(res.data);
			} else {
				console.log("接口调用失败")
				deferred.reject(res);
			}
		});
		return deferred.promise;
	}

	return service;
}).service('ModalService', function($rootScope, $uibModal, $uibTooltip) {
	var service = {};
	service.yesno = function(params,okCall,cancelCall) {
		var modalInstance = $uibModal.open({
			templateUrl : '../template/yesno.html',
			controller : function($scope, $uibModalInstance, items) {
				items = items || {};
				$scope.title=items.title || '提示';
				$scope.content=items.content || '是否删除?';
				$scope.okTxt=items.okTxt || '确定';
				$scope.cancelTxt=items.cancelTxt || '取消';
				$scope.ok = function() {
					if(typeof(okCall)=='function'){
						var tag=okCall(items);
						if(tag==null || tag){
							$uibModalInstance.close();
						}
					}else{
						$uibModalInstance.close();
					}
				};
				$scope.cancel = function() {
					if(typeof(cancelCall)=='function'){
						var tag=cancelCall(items);
						if(tag==null || tag){
							$uibModalInstance.close('cancel');
						}
					}else{
						$uibModalInstance.close('cancel');
					}
				};
			},
			resolve : {
				items : function() {
					return params;
				}
			}
		});
	}
	return service;
})
