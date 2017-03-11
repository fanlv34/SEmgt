"use strict";

var app = angular.module("loginApp",["ngAnimate", "ui.bootstrap"]);

app.run(function($rootScope,$sender,$timeout) {
	$rootScope.errorMessage = null;
	$rootScope.error = function(_errMsg) {
		if(_errMsg) {
			if($(".modal").length > 0) {// 打开了模态框
				$rootScope.errorMessageOfModal = _errMsg;
				// 去掉hide样式 以显示警告框
				$("#modalAlert").removeClass("hide");
				// 5秒钟后重新添加hide样式 以隐藏警告框
				$timeout(function(){
					$("#modalAlert").addClass("hide");
				},5000);
			} else {
				$rootScope.errorMessage = _errMsg;
				// 去掉hide样式 以显示警告框
				$("#mainAlert").removeClass("hide");
				// 5秒钟后重新添加hide样式 以隐藏警告框
				$timeout(function(){
					$("#mainAlert").addClass("hide");
				},5000);
			}
			
			
		} else {
			$rootScope.errorMessage = null;
		}
	}
	
	$rootScope.isEmpty = function(obj) {
		if(obj=="" || obj == null || obj==undefined) {
			return true;
		} else {
			return false;
		}
	}
});

//路由
//app.config(["$stateProvider", function($stateProvider) {
//	$stateProvider
//		.state("register", {
//	        url: "/register",
//	        templateUrl: "/semgt/static/login/register.html",
//	        controller: "registerCtrl"
//	    })
//		.state("close", {
//    	url: "",
//    	template: ""
//		});
//}]);

app.controller("loginCtrl", function($scope, $http, $sender, $uibModal) {
	$("#username").focus();

	$scope.doLogin = function() {
		if($scope.isEmpty($scope.username)) {
			$scope.error("用户名不能为空");
			return;
		}
		if($scope.isEmpty($scope.password)) {
			$scope.error("密码不能为空");
			return;
		}
		var formData = {
			"username" : $scope.username,
			"password" : hex_sha1($scope.password)
		};
		$sender("/semgt/login.do", formData,function(resp) {
			window.location.replace("/semgt/static/main/main.html");
		});
	}
	
	$scope.doReg = function() {
		var modalInstance = $uibModal.open({
	      animation: true,//打开时的动画开关
	      templateUrl: "/semgt/static/modal/register.html",//模态框的页面内容,这里的url是可以自己定义的,也就意味着什么都可以写
	      controller: "registerCtrl",//这是模态框的控制器,是用来控制模态框的
	      backdrop: "static"//控制点击空白不自动关闭
	    });
		
		modalInstance.result.then(function () {//这是一个接收模态框返回值的函数
			$scope.search();
		});
    };
});

app.controller("registerCtrl", function($scope, $http, $sender, $uibModalInstance) {
	$scope.user = {};
	
	$scope.reg = function() {
		if($scope.isEmpty($scope.user.password)) {
			$scope.error("密码不能为空");
			return;
		}
		if($scope.isEmpty($scope.user.passwordCfm)) {
			$scope.error("重复密码不能为空");
			return;
		}
		$scope.user.password = hex_sha1($scope.user.password);
		$scope.user.passwordCfm = hex_sha1($scope.user.passwordCfm);
		
		$sender("/semgt/register.do", $scope.user, function(r) {
			$scope.user = {};
			$scope.close();
		}, function(e) {
			$scope.error(e._errorMessage);
			$scope.user.password = "";
			$scope.user.passwordCfm = "";
		})
	}
	
	$scope.close = function() {
		$uibModalInstance.dismiss("cancel");
	}
});
