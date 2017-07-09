"use strict";
	// 删除左右两端的空格
	function trim(str) {
	     return str.replace(/(^\s*)|(\s*$)/g, '');
	}
	
	// 删除左边的空格
	function ltrim(str) {
	     return str.replace(/(^\s*)/g, '');
	}
	
	// 删除右边的空格
	function rtrim(str) {
	     return str.replace(/(\s*$)/g, '');
	}
	
	// 判断字段为空
	function isEmpty(value) {
		return (typeof value == 'undefined') || trim(value) === '' || value === null || value !== value;
	}