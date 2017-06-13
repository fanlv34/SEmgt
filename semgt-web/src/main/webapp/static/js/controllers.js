"use strict";
var ModalInstanceCtrl=null;
app.controller("semgtCtrl", function($scope, $http, $state, $sender, $uibModal) {
	$sender("/semgt/json/condition.json",null,function(conditions) {
		$scope.isEndConditions = conditions.isEndConditions;
		$scope.isAbandoneds = conditions.isAbandoned;
		//默认值
		$scope.isAbandoned = 1;// 在追剧
	});

	$scope.search = function() {
		$scope.seList = null;
		
		$scope.clearPageable();
		var params = {
			"isEndCondition": $scope.isEndCondition,
			"isAbandoned": $scope.isAbandoned,
			"uri": "/semgt/queryAllSeries.do"
		};
		$scope.setPageableParam(params);
		$sender(params.uri, params, function(res) {
			$scope.setPageableData(res);
			$scope.pageableQryCallBack(res);
		});
	}
	
	$scope.open = function(opr,params) {
		var modalInstance = $uibModal.open({
	      animation: true,//打开时的动画开关
	      templateUrl: "/semgt/static/modal/oprSeries.html",//模态框的页面内容,这里的url是可以自己定义的,也就意味着什么都可以写
	      controller: "oprSeriesCtrl",//这是模态框的控制器,是用来控制模态框的
	      backdrop: "static",//控制点击空白不自动关闭
	      resolve: {//这是一个入参,这个很重要,它可以把主控制器中的参数传到模态框控制器中
	        items: function () {//items是一个回调函数
	        	return params;//这个值会被模态框的控制器获取到
	        }
	      }
	    });
		
		modalInstance.result.then(function () {//这是一个接收模态框返回值的函数
			$scope.search();
		});
    };
    
	// 操作剧集 opr-1 添加 opr-2 修改
	$scope.doOperate = function(opr,row) {
		if("1"==opr) {
			// 设置默认值
			$scope.oprSeries = {
				"oprState": opr,
				"modalTitle": "添加",
				"headSeason" : 1,
				"currentSeason" : 1,
				"episode" : 1,
				"isEnd" : "B",
				"isAbandoned" : 1,
				"fuzzyDate" : "N",
				"updateWeekday" : "0"
			};
			$scope.open(opr,$scope.oprSeries);
		} else if("2"==opr) {
			$scope.oprSeries = {};
			angular.copy(row, $scope.oprSeries);
			$scope.oprSeries.oprState = opr;
			$scope.oprSeries.modalTitle = "修改";
			$scope.open(opr,$scope.oprSeries);
		}
	}
	
	// 删除
	$scope.delSeries = function(row) {
		if(confirm("确定要删除《" + row.seriesNameCN + "》吗？")) {
			$sender("/semgt/delSeries.do", {"seriesId":row.seriesId},function(resp) {
				$scope.search();
			});
		}
	}
	
	// 更新剧集
	$scope.toUpdate = function(row) {
		var params = row.downloadUrl.split("|");
		window.open("http://" + params[1] + params[2] + row.seriesNameCN);
	}

	// 显示快捷按钮
	$scope.showQuickBtn = function($event) {
		$($event.target).children(".btn-link").show();
	}
	// 隐藏快捷按钮
	$scope.hideQuickBtn = function($event) {
		$($event.target).children(".btn-link").hide();
	}
	
	// 快捷按钮 减少数量
	$scope.minus = function(row, target) {
		if(row[target] > 1) {
			$sender("/semgt/quickOperation.do", {"target":target,"seriesId":row.seriesId,"operation":"minus"},function(){
				row[target] = row[target]-1;
			});
		}
	}
	// 快捷按钮 增加数量
	$scope.plus = function(row, target) {
		$sender("/semgt/quickOperation.do", {"target":target,"seriesId":row.seriesId,"operation":"plus"},function(resp) {
			row[target] = row[target]+1;
		});
	}
});

//操作剧集模态框
app.controller("oprSeriesCtrl", function($scope, $http, $rootScope,$compile, $sender, $uibModalInstance, items){
	
	// 取得初值
	$scope.oprSeries = items;
	$scope.ratOptions = {
		initialValue : $scope.oprSeries.rating
	}
	$scope.dpOptions = {
		viewMode: "years",
		format: "YYYY",
		locale: "zh-cn",
		showClear: true,
		showClose: true,
		useCurrent: true,
		showTodayButton: true
	}
	
	$scope.$watch("oprSeries.fuzzyDate",function() {
		if(!$scope.isEmpty($scope.oprSeries.fuzzyDate)) {
			if($scope.oprSeries.fuzzyDate == "N") {
				$("#dpm").addClass("hidden");
			} else {
				$("#dpm").removeClass("hidden");
				if($scope.oprSeries.fuzzyDate == "Y") {
					$scope.dpOptions.format="YYYY";
				} else if($scope.oprSeries.fuzzyDate == "M") {
					$scope.dpOptions.format="YYYY-MM";
				} else if($scope.oprSeries.fuzzyDate == "C") {
					$scope.dpOptions.format="YYYY-MM-DD";
				}
			}
				
		}
	});
	
	// 隐藏日期选择控件 El:要操作的jquery对象 isHide:true-隐藏 false-显示
	$scope.hideDatePicker = function(El,isHide) {
		if(isHide) {
			$(El[0]).addClass("hidden");
		} else {
			$(El[0]).removeClass("hidden");
		}
		$(El[0]).html($compile($(El[0]).html())($scope));
	}
	
	// 从cache加载更新地址列表
	$rootScope.getCache("downloadUrl",function() {
		if("1"==$scope.oprSeries.oprState) {
			$scope.oprSeries.downloadUrl = $rootScope.cache.downloadUrl[0].ruleId;
		} else if("2"==$scope.oprSeries.oprState) {
			for (var i = 0; i < $rootScope.cache.downloadUrl.length; i++) {
				if($scope.oprSeries.urlType == $rootScope.cache.downloadUrl[i].ruleId) {
					$scope.oprSeries.downloadUrl = $rootScope.cache.downloadUrl[i].ruleId;
				}
			}
		}
	});
	
	// 提交方法
	$scope.submit = function() {
		$scope.submitScuess = function(resp) {
			$scope.oprSeries = {};
			$uibModalInstance.close(); 
		}
		if("1"==$scope.oprSeries.oprState) {
			$sender("/semgt/addSeries.do", $scope.oprSeries,$scope.submitScuess);
		} else if("2"==$scope.oprSeries.oprState) {
			$sender("/semgt/modifySeries.do", $scope.oprSeries,$scope.submitScuess);
		}
	}
	
	// 关闭
	$scope.close = function() {
		$uibModalInstance.dismiss("cancel");
	}
});
