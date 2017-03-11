"use strict";

var app = angular.module("semgtApp", ["ui.router","ngAnimate", "ui.bootstrap", "ae-datetimepicker"]);

app.run(function($rootScope,$sender,$timeout) {
	$rootScope.cache = {};// 缓存数据
	
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
	
	$rootScope.getCache = function(cacheName, aftDo) {
		$sender("/semgt/getCache.do", {"cacheName":cacheName, "cacheType":"List"}, function(resp) {
			var arr=[]; 
			arr[cacheName] = resp[cacheName];
			// 通过数组 动态向对象中添加对象
			for(var i in arr) {
				$rootScope.cache[i]=arr[i]; 
			}
			//回调函数
			if(typeof(aftDo) === "function") {
				aftDo();
			}
		});
	}
	
	// --------------------分页实现 Begin--------------------
	$rootScope._page = {
		"pageSize": 10,
		"currentPage": 1
	}
	
	$rootScope.pageableParam = {};//上送参数
	$rootScope.setPageableParam = function(params) {
		// 如果params为空 则重置pageableParam的值
		// 否则 按照params设置pageableParam
		if($rootScope.isEmpty(params)) {
			$rootScope.pageableParam = {};
		} else {
			$rootScope.pageableParam = params;
		}
		// 外部调用时此值会拼入pageableParam 内部调用时此值会被覆盖
		$rootScope.pageableParam.pageSize = $rootScope._page.pageSize;
		$rootScope.pageableParam.currentPage = 1;
	}
	
	$rootScope.pageableSel = null;
	$rootScope.pageableData = {};//返回参数
	$rootScope.setPageableData = function(u) {
		$rootScope.pageableData = u.pageableData;
		$rootScope.pageableSel = [];
		for(var i=0, l=$rootScope.pageableData.totalPage;i<l;i++) {
			$rootScope.pageableSel.push(i+1);
		}
	}
	
	$rootScope.prevPage = function() {
		if($rootScope._page.currentPage > 1) {
			$rootScope._page.currentPage = $rootScope._page.currentPage - 1;
			$rootScope.goToPage();
		}
	}
	$rootScope.nextPage = function() {
		if($rootScope._page.currentPage < $rootScope.pageableData.totalPage) {
			$rootScope._page.currentPage = $rootScope._page.currentPage + 1;
			$rootScope.goToPage();
		}
	}
	
	$rootScope.pageableQryCallBack = function(res) {
		// 根据当前页号 禁用上页下页按钮
		$("ul[class='pagination'] > li").removeClass("disabled");
		if($rootScope.pageableParam.currentPage == 1) {
			$("ul[class='pagination'] > li[class='previous']").addClass("disabled");
		}
		if($rootScope.pageableParam.currentPage == res.pageableData.totalPage) {
			$("ul[class='pagination'] > li[class='next']").addClass("disabled");
		}
		$rootScope._page.currentPage = $rootScope.pageableParam.currentPage;
	}
	
	$rootScope.goToPage = function() {
		$rootScope.pageableParam.pageSize = $rootScope._page.pageSize;
		$rootScope.pageableParam.currentPage = $rootScope._page.currentPage;
		$sender($rootScope.pageableParam.uri, $rootScope.pageableParam, function(res) {
			$rootScope.setPageableData(res);
			$rootScope.pageableQryCallBack(res);
		});
	}
	// --------------------分页实现 End--------------------
});

//路由
//app.config(["$stateProvider", function($stateProvider) {
//	$stateProvider
//		.state("oprSeries", {
//			url: "/oprSeries",
//			templateUrl: "/semgt/static/oprSeries.html"
//		})
//	    .state("close", {
//	    	url: "",
//	    	template: ""
//	    });
//}]);