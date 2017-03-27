var user = null;
bdtApp.service('UserService', function($rootScope, HttpService, $location, $q) {
	var service = {};
	service.getUser = function() {
		var deferred = $q.defer();
		HttpService.post("/employee/getEmployee", {}).then(function(res) {
			if (res.code == 0) {
				if (res.data.mobile != null && res.data.mobile != '') {
					res.data.type += 'mobile,';
				}
				if (res.data.email != null && res.data.email != '') {
					res.data.type += 'email,';
				}
				if (res.data.idcard != null && res.data.idcard != '') {
					res.data.type += 'idcard,';
				}
				if (res.data.code != null && res.data.code != '') {
					res.data.type += 'code,';
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
		var a = $http.jsonp(bdtApp.postUrl + url, {
			params : params
		});
		a.then(function(res) {
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
	service.toastr = {};
	service.toastr.success = function(message, title, titme) {
		titme = titme || 1000;
		title = title || '提示';
		toastr.success(message, title, {
			timeOut : titme
		});
	}
	service.toastr.info = function(message, title, titme) {
		titme = titme || 1000;
		title = title || '提示';
		toastr.info(message, title, {
			timeOut : titme
		});
	}
	service.toastr.warning = function(message, title, titme) {
		titme = titme || 1000;
		title = title || '提示';
		toastr.warning(message, title, {
			timeOut : titme
		});
	}
	service.toastr.error = function(message, title, titme) {
		titme = titme || 1000;
		title = title || '提示';
		toastr.error(message, title, {
			timeOut : titme
		});
	}
	service.yesno = function(params, okCall, cancelCall) {
		var modalInstance = $uibModal.open({
			templateUrl : '../template/yesno.html',
			controller : function($scope, $uibModalInstance, items) {
				items = items || {};
				$scope.title = items.title || '提示';
				$scope.content = items.content || '是否删除?';
				$scope.okTxt = items.okTxt || '确定';
				$scope.cancelTxt = items.cancelTxt || '取消';
				$scope.ok = function() {
					if (typeof (okCall) == 'function') {
						var tag = okCall(items);
						if (tag == null || tag) {
							$uibModalInstance.close();
						}
					} else {
						$uibModalInstance.close();
					}
				};
				$scope.cancel = function() {
					if (typeof (cancelCall) == 'function') {
						var tag = cancelCall(items);
						if (tag == null || tag) {
							$uibModalInstance.close('cancel');
						}
					} else {
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
	
	service.list = function(params, okCall, cancelCall) {
		var modalInstance = $uibModal.open({
			template:'<div class="modal-header">{{title}}</div>'+
				'<div class="modal-body">'+
				'<table class="table">'+
				'	<thead>'+
				'		<tr>'+
				'			<th ng-show="type!=null"><input type="checkbox" ng-click="selectAll()" ng-model="checkAll"></th>'+
				'			<th ng-repeat="t in listTitle">{{t.title}}</th>'+
				'		</tr>'+
				'	</thead>'+
				'	<tbody>'+
				'		<tr ng-repeat="data in list">'+
				'			<td ng-show="type!=null"><input type="checkbox" ng-model="data.check" ng-click="select(data)"></td>'+
				'			<td ng-repeat="t in listTitle">{{t.code=="index"?$index+1:data[\'\'+t.code]}}</td>'+
				'		</tr>'+
				'	</tbody>'+
				'</table>'+
				'</div>'+
				'<div class="modal-footer">'+
				'<button class="btn btn-primary" type="button" ng-click="ok()">{{okTxt}}</button>'+
				'<button class="btn btn-warning" type="button" ng-click="cancel()">{{cancelTxt}}</button></div>',
			controller : function($scope, $uibModalInstance, items) {
				items = items || {};
				$scope.type=items.type || 'none';
				$scope.title = items.title || '提示';
				$scope.listTitle=items.listTitle || [{title:'数据',code:'data'}];
				$scope.listTitle.splice(0,0,{title:'序号',code:'index'});
				$scope.list = items.list || [{data:'数据'}];
				$scope.okTxt = items.okTxt || '确定';
				$scope.cancelTxt = items.cancelTxt || '取消';
				$scope.selectDatas=[];
				$scope.ok = function() {
					if (typeof (okCall) == 'function') {
						var tag = okCall($scope.selectDatas);
						if (tag == null || tag) {
							$uibModalInstance.close();
						}
					} else {
						$uibModalInstance.close();
					}
				};
				$scope.cancel = function() {
					if (typeof (cancelCall) == 'function') {
						var tag = cancelCall($scope.selectDatas);
						if (tag == null || tag) {
							$uibModalInstance.close('cancel');
						}
					} else {
						$uibModalInstance.close('cancel');
					}
				};
				$scope.selectAll=function(){
					var list=$scope.list;
					if($scope.checkAll){
						for(var i=0;i<list.length;i++){
							var data=list[i];
							data.check=true;
						}
						$scope.selectDatas=list;
					}else{
						for(var i=0;i<list.length;i++){
							var data=list[i];
							data.check=false;
						}
						$scope.selectDatas=[];
					}
				}
				$scope.select=function(data){
					var list=$scope.list;
					var index=0;
					if(data.check){
						$scope.selectDatas.push(data);
					}else{
						$scope.selectDatas.splice($scope.selectDatas.indexOf(data),1);
					}
					for(var i=0;i<list.length;i++){
						var data=list[i];
						if(data.check){
							index++;
						}
					}
					if(index==list.length){
						$scope.checkAll=true;
					}else{
						$scope.checkAll=false;
					}
				}
			},
			resolve : {
				items : function() {
					return params;
				}
			}
		});
	}
	
	service.selectList = function(params, okCall, cancelCall) {
		var modalInstance = $uibModal.open({
			template:'<div class="modal-header">{{title}}</div>'+
				'<div class="modal-body">'+
				'<table class="table">'+
				'	<thead>'+
				'		<tr>'+
				'			<th ng-repeat="t in listTitle">{{t.title}}</th>'+
				'		</tr>'+
				'	</thead>'+
				'	<tbody>'+
				'		<tr ng-repeat="data in list" ng-click="select(data)">'+
				'			<td ng-repeat="t in listTitle">{{t.code=="index"?$index+1:data[\'\'+t.code]}}</td>'+
				'		</tr>'+
				'	</tbody>'+
				'</table>'+
				'</div>',
			controller : function($scope, $uibModalInstance, items) {
				items = items || {};
				$scope.type=items.type || 'none';
				$scope.title = items.title || '提示';
				$scope.listTitle=items.listTitle || [{title:'数据',code:'data'}];
				$scope.list = items.list || [{data:'数据'}];
				$scope.okTxt = items.okTxt || '确定';
				$scope.cancelTxt = items.cancelTxt || '取消';
				$scope.selectDatas=[];
				$scope.select=function(data){
					if (typeof (okCall) == 'function') {
						var tag = okCall(data);
						if (tag == null || tag) {
							$uibModalInstance.close();
						}
					} else {
						$uibModalInstance.close();
					}
				}
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
