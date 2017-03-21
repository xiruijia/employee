var user=null;
bdtApp.service('UserService', function($rootScope, HttpService, $location,$q) {
	var service = {};
	service.getUser = function() {
		var deferred = $q.defer();
		if(user!=null){
			deferred.resolve(user);
		}else{
			HttpService.post("/employee/getEmployee", {}).then(function(res) {
				if (res.code == 0) {
					user=res.data;
					deferred.resolve(res.data);
				} else {
					// 跳转到登录页面
					$location.path("/user");
				}
			});
		}
		return deferred.promise;
	}
	service.register=function(user){
		var deferred = $q.defer();
		HttpService.post("/employee/register", {}).then(function(res) {
			if (res.code == 0) {
				user=res.data;
				deferred.resolve(res.data);
			} else {
				// 跳转到登录页面
				$location.path("/user");
			}
		});
		return deferred.promise;
	}
	return service;
});

bdtApp.service('HttpService', function($rootScope, $http, $q) {
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
});
