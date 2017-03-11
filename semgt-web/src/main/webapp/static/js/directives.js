"use strict";

app.directive("rating", function() {
	return {
        restrict: 'EA',
        link: function (scope, element, attrs, ctrls, ngModel) {
        	element.rating({"initialValue":scope.ratOptions.initialValue});
        }
	}
});

app.directive("seDate", function() {
	return {
		restrict: "A",
		templateUrl: "/semgt/static/segment/datepicker.html",
		link: function(scope, element, attrs, ctrls) {
			// 返回值
//			scope.seDateVal = null;
			// 计算当前年到未来5年
			scope.currDate = new Date();
			scope.years = [scope.currDate.getFullYear()];
			for (var i = 1; i < 5; i++) {
				scope.years.push(scope.currDate.getFullYear()+i);
			}
			scope.seDateY = scope.years[0].toString();
			// 控制datepicker显示
			scope.yearFlag = false;
			scope.$watch(attrs.seDate, function() {
				switch(scope.$eval(attrs.seDate)) {
				case "Y":
					scope.yearFlag = true;
					scope.seDateVal = scope.seDateY;
					break;
				case "M":
					scope.yearFlag = false;
					scope.seDateVal = scope.seDateMC;
					$(element).children().attr("type","month");
					break;
				case "C":
					scope.yearFlag = false;
					scope.seDateVal = scope.seDateMC;
					$(element).children().attr("type","date");
					break;
				}
			});
			
			// 选中后立即赋值
			scope.transferVal = function(val) {
				scope.seDateVal = scope.$eval(val);
			}
		}
	};
});

app.directive("seNumber", function($parse) {
	return {
		restrict: "E",
		templateUrl: "/semgt/static/segment/seNumber.html",
		transclude: true,
		link: function(scope, element, attrs, ctrls) {
			var numInput = element.find("input");
			var upBtn = element.find("button[sign='up']");
			var downBtn = element.find("button[sign='down']");
			numInput.css("text-align","center");
			
			var min = numInput.attr("min");
            var max = numInput.attr("max");
			
			var setText = function(n) {
				if ((min && n < min) || (max && n > max)) {
					return false;
				}
				
				var model = $parse(numInput.attr("ng-model"));//取得ng-model的变量 返回
				model.assign(scope,n);//往ng-model对应的变量里设值
		        scope.$apply();
		        
				numInput.focus().val(n);
				return true;
			}
			
			// onkeydown
			numInput.bind("keydown",function(e) {
				if ($.inArray(e.keyCode, [46, 8, 9, 27, 13, 110]) !== -1 ||
					(e.keyCode == 65 && e.ctrlKey === true) ||
					(e.keyCode >= 35 && e.keyCode <= 39)) {
				    return;
				}
				if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
				    e.preventDefault();
				}
			});
			
			// blur
			numInput.bind("blur",function(e) {
				var c = String.fromCharCode(e.which);
				var n = parseInt(numInput.val() + c);
				if ((min && n < min)) {
					setText(min);
				}
				else if (max && n > max) {
					setText(max);
				}
			});
			
			// up click
			upBtn.bind("click",function() {
				 setText(parseInt(numInput.val()) + 1);
			});
			
			//down click
			downBtn.bind("click",function() {
				 setText(parseInt(numInput.val()) - 1);
			});
		}
	}
});
