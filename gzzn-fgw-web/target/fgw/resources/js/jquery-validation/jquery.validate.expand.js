/*!
 * jQuery Validation Plugin 扩展功能
 * 
 * 1、验证字符不能全为空格；
 * 2、实现 字符长度计算时，一个汉字算两个字符；
 * 3、加入QQ、邮政编码、IP;
 * 4、加入电话号码、手机号码、固定电话、多个电话号码【多个间使用,或/隔开  】
 *
 * Copyright 2013 罗海群
 * 2013-12-04
 * 
 */
//验证字符不能全为空格
jQuery.validator.addMethod("allSpace", function(value, element) {  
    return this.optional(element) || (value.length>0 && /^\s+$/.test(value));
}, "数据不能全为空格");


//最小字符长度【字数最少是 {0}的非空字符串】
jQuery.validator.addMethod("byteMinLength", function(value, element, param) {
	if(value.length>0 && /^\s+$/.test(value)){//验证字符不能全为空格
		return false;
	}
	var length = value.replace(/[^\x00-\xff]/g, "**").length;
	return this.optional(element) || (length >= param);
}, $.validator.format("请输入一个长度最少是 {0} 的字符串"));

//最大字符长度【字数最多是{0}的非空字符串】
jQuery.validator.addMethod("byteMaxLength", function(value, element, param) {
	if(value.length>0 && /^\s+$/.test(value)){//验证字符不能全为空格
		return false;
	}
	var length = value.replace(/[^\x00-\xff]/g, "**").length;
	return this.optional(element) || (length <= param);
}, $.validator.format("请输入一个长度最多是 {0} 的字符串"));

//字符长度在[a, b]之间，【字数介于 {0} 和 {1} 之间的非空字符串】
jQuery.validator.addMethod("byteRangeLength",function(value, element, param) {
	if(value.length>0 && /^\s+$/.test(value)){//验证字符不能全为空格
		return false;
	}
	var length = $.trim(value).replace(/[^\x00-\xff]/g, "**").length;
	return this.optional(element) || (length >= param[0] && length <= param[1]);
}, $.validator.format("请输入一个长度介于 {0} 和 {1} 之间的字符串"));


//最大字符长度【字数最多是{0}的非空字符串】
jQuery.validator.addMethod("byteMaxFileNameLength", function(value, element, param) {
	if(value.length>0){
		value = value.substring(value.lastIndexOf("\\")+1,value.length);
	}
	var length = value.replace(/[^\x00-\xff]/g, "**").length;
	
	return this.optional(element) || (length <= param);
}, $.validator.format("请输入一个长度最多是 {0} 的字符串"));


//验证用户登录密码的复杂度
jQuery.validator.addMethod("complexPwd", function(value, element) {    
	var reg = /^(?![!{}\[\],.<>@$%&^()_+=]+$)(?![0-9]+$)(?![a-z]+$)(?![A-Z]+$)[!{}\[\],.<>@$%&^()_+=0-9a-zA-Z]{6,}$/;
	return this.optional(element) || (reg.test(value));
}, "密码不符合复杂度规范，请重新输入");
// 邮政编码验证    
jQuery.validator.addMethod("zipCode", function(value, element) {    
    var reg = /^[1-9]\d{5}$/;
    return this.optional(element) || (reg.test(value));
}, "请输入合法的邮政编码");

//QQ    
jQuery.validator.addMethod("QQ", function(value, element) {    
    var reg = /^[1-9]\d{4,8}$/;
    return this.optional(element) || (reg.test(value));
}, "请输入合法的QQ");

//IP   
jQuery.validator.addMethod("IP", function(value, element) {    
  var reg = /^[\d\.]{7,15}$/;
  return this.optional(element) || (reg.test(value));
}, "请输入合法的IP");

//多个电话号码间使用,或/隔开   
jQuery.validator.addMethod("mulTel", function(value, element) {    
  var reg = /^([+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+[,\/])*[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/;
  return this.optional(element) || (reg.test(value));
}, "请输入合法的电话号码，多个间用,或/隔开");


//中国电话号码（包括移动和固定电话） 
jQuery.validator.addMethod("Tel", function(value, element) {    
var reg = /(\(\d{3,4}\)|\d{3,4}-|\s)?\d{7,14}$/ ;
return this.optional(element) || (reg.test(value));
}, "请输入合法的电话号码");

//中国手机号码
jQuery.validator.addMethod("Mobile", function(value, element) {    
  var reg = /^(86)*0*1[358]\d{9}$/;
  return this.optional(element) || (reg.test(value));
}, "请输入合法的手机号码");

//中国固定电话号码:    
jQuery.validator.addMethod("Phone", function(value, element) {    
  var reg = /(\(\d{3,4}\)|\d{3,4}-|\s)?\d{8}$/ ;
  return this.optional(element) || (reg.test(value));
}, "请输入合法的固定电话");

/* 小数验证，小数点位数按照max参数的小数点位数进行判断
 * 只能输入数字 */
$.validator.addMethod("isDecimal", function(value, element, params) {
	if(isNaN(params[0])) {
		console.error("参数错误，decimal验证的min只能为数字");
		return false;
	}
	if(isNaN(params[1])) {
		console.error("参数错误，decimal验证的max只能为数字");
		return false;
	}
	if(isNaN(params[2])) {
		console.error("参数错误，decimal验证的accuracy只能为数字");
		return false;
	}
	if(isNaN(value)) {
		return false;
	}
	//if(typeof(value) == undefined || value == "") {
	//	return false;
	//}
	var min = Number(params[0]);
	var max = Number(params[1]);
	var testVal = Number(value);
	if(typeof(params[2]) == undefined || params[2] == 0) {
		var regX = /^\d+$/;
	} else {
		var regxStr = "^\\d+(\\.\\d{1,"+params[2]+"})?$";
		var regX = new RegExp(regxStr);
	}
//	console.debug("regX: %o, value: %o, test: %o", regX, value, regX.test(value));
    return this.optional(element) || (regX.test(value) && testVal >= min && testVal <max);
 }, $.validator.format("请正确输入在{0}到{1}之间，最多只保留小数点后{2}的数值"));


