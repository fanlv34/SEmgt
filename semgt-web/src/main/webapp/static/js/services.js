"use strict";

app.provider({
	$sender : $SenderProvider
});

function $SenderProvider() {
	this.$get = ["$rootScope", "$http",
	function($rootScope, $http) {
		function post(url,data,fnSucess,FnError) {
			//提交前清空错误消息
			$rootScope.error(null);
			$http.post(url,data).success(function(resp) {
				if(resp) {// 存在返回数据
					if(resp._errorMessage) {// 返回错误消息
						if(angular.isFunction(FnError)) {
							FnError(resp);
						} else {
							$rootScope.error(resp._errorMessage);
						}
					} else {// 返回成功数据
						if(angular.isFunction(fnSucess)) {
							fnSucess(resp);
						} else {
							console.log("fnSucess is NOT a function!");
						}
					}
				} else {
					console.log("resp is empty!");
				}
			});
		}
		return post;
	}];
}
