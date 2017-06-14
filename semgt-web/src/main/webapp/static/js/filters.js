"use strict";

app.filter("isEndFilter",function() {
	return function(input) {
		switch(input) {
		case "S":
		  return "本季终";
		  break;
		case "B":
		  return "在播"
		  break;
		case "E":
			  return "剧终"
			  break;
		case "N":
			  return "未开播"
			  break;
		default:
		  return "";
		}
	};
});

app.filter("isAbandonedFilter",function() {
	return function(input) {
		switch(input) {
		case 0:
			return "想看"
			break;
		case 1:
			return "在追";
			break;
		case 2:
			return "半弃";
			break;
		case 3:
			return "已弃";
			break;
		default:
			return "--";
		}
	};
});

app.filter("weekdayFilter",function() {
	return function(input) {
		switch(input) {
		case "0":
			return "--";
			break;
		case "1":
			return "星期一"
			break;
		case "2":
			return "星期二"
			break;
		case "3":
			return "星期三"
			break;
		case "4":
			return "星期四"
			break;
		case "5":
			return "星期五"
			break;
		case "6":
			return "星期六"
			break;
		case "7":
			return "星期日"
			break;
		default:
		  return "--";
		}
	};
});

app.filter("ruledefFilter",function() {
	return function(input,param) {
		var arr = input.split("|");
		return arr[param];
	};
});

app.filter("emptyFilter",function() {
	return function(input) {
		if(input=="" || input==undefined || input==null) {
			return "--";
		} else {
			return input;
		}
	};
});
