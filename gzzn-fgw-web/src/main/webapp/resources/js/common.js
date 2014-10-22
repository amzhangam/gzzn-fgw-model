/**
 * 弹出框初始化
 * @param ele
 * @param t
 * @param w
 * @param h
 */
function winInit(ele,t,w,h){
	$(ele).dialog({
		autoOpen : false,
		title : t,
		closeText : "关闭",
		modal : true,
		width:w ,
		height: h,
		draggable : false,
		resizable : false
	});
	winJuZhong(ele);
}

/**
 * 弹出窗口居中
 * @param ele
 */
function winJuZhong(ele){
	$(ele).dialog("option","position",[
		(window.innerWidth-$(ele).dialog("option","width"))/2,
		(window.innerHeight-$(ele).dialog("option","height"))/2]);
}

/**
 * 关闭窗口事件
 * @param ele
 */
function closeDialog(ele){
	$(ele).dialog("close");
}






function deleteAction(url,message){
	var m = message;
	if(m == null || m.length == 0){
		m = "删除将不可恢复，确定要删除此记录吗？";
	}
	mac.confirm('<p>'+m+'</p>', function(){
		window.location = url;
	}, null);
	/*
	if(window.confirm(m)){
		window.location = url;
	}*/
}

function changePage(page,size){
	$("#currentPage").val(page);
	$("#pageSize").val(size);
	document.forms[0].submit();
}

//将列表框选中的值放到另一个列表框中，Source为源列表框的id,Target为目标列表框的id
function moveItem(Source, Target) {
	var ObjSource = document.getElementById(Source);
	var ObjTarget = document.getElementById(Target);
	for (var i = 0; i < ObjSource.options.length; i++) {
		if (ObjSource.options[i].selected) {
			var opt = document.createElement("OPTION");
			opt.value = ObjSource.options[i].value;
			opt.text = ObjSource.options[i].text;
			ObjTarget.add(opt);
			//ObjSource.options.removeChild(ObjSource.options[i--]);
			ObjSource.options.remove(i--);
		}
	}
}

//处理已选择的列表值
function handleSelected(id){
	var str = "";	
	var selected = document.getElementById(id);
	for (var i = 0; i < selected.length; i++) {
		str = str + "@" + selected.options[i].value;
	}	
	if(str != ""){
		str = str.substring(1);//去掉开头的@字符	
	}
	return str;
}

function checkNotEmpty(obj){
	if(obj.val().length == 0){
		showError(obj,"不允许为空");
		return false;
	}
	return true;
}

function checkLength(obj,len){
	if(obj.val().replace(/[^\x00-\xff]/g, "**").length > len){
		showError(obj,"请输入一个长度最多是 "+len+ "的字符串");
		return false;
	}
	return true;
}
function checkTelephone(obj){
	var reg = /^([+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+[,\/])*[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[ ]){1,12})+$/;
	if(!reg.test(obj.val())){
		showError(obj,"请输入合法的电话号码");
		return false;
	}
	return true;
}



function showError(inputObj,msg){
	var o = inputObj.parent();
	if(o.find("span") != null){
		o.find("span").remove();
	}
	o.append('<span class="inputError">'+msg+'</span>');
}


function showalone(){
	$(".isShowAlone").addClass("hide");
	var url = $("form")[0].action.replace(".ac",".json");
	
	//alert(url);
	$("form").append('<input type="hidden" name="isShowAlone" value="1" />');
	$("form")[0].action = url;
}




//jquery自动完成默认设置,支持单选
var defaultOptions = {
	max : 5000,
	width : 200,
	minChars : 0,
	// autoFill: true,
	// mustMatch: true,
	scrollHeight : 200,
	selectFirst : false,
	delay : 1
};
//jquery支持多选
var multipleOptions = {
	max : 5000,
	width : 150,
	minChars : 0,
	autoFill : false,
	scrollHeight : 250,
	selectFirst : false,
	delay : 1,
	multiple : true
};
//jquery必须匹配才能选择：用于添加、修改数据
var mustMatchOptions = {
	max : 5000,
	width : 200,
	minChars : 0,
	mustMatch : true,
	scrollHeight : 160,
	selectFirst : false,
	delay : 1
};

//jquery必须匹配才能选择：用于添加、修改数据
var inputMustMatchOptions ={
    max : 50,
	width : 400,
	minChars : 0,
	mustMatch : true,
	matchContains: true,
	scrollHeight : 300,
	selectFirst : false,
	delay : 1,
    formatItem: function(row, i, max) {
       return "【" + row.id + "】" + row.name;
    },
    formatMatch: function(row, i, max) {
       return "【" + row.id + "】" + row.name;
    },
    formatResult: function(row) {
        return row.name;
    }
};


//jquery必须匹配才能选择：用于添加、修改数据
var inputDefaultOptions ={
    max : 50,
	width : 400,
	minChars : 0,
	matchContains: true,
	scrollHeight : 300,
	selectFirst : false,
	delay : 1,
    formatItem: function(row, i, max) {
       return "【" + row.id + "】" + row.name;
    },
    formatMatch: function(row, i, max) {
       return "【" + row.id + "】" + row.name;
    },
    formatResult: function(row) {
        return row.name;
    }
};

/**
 * 自动填充下拉框实现方法：用于编辑数据
 * @param dataJson  自动下拉框的数据
 * @param putNameId 存放名称的id
 * @param putKeyId 存放主键的id
 * @param mustMatch 是否完全匹配
 */
function inputAutoComplete(dataJson, putNameId, putKeyId, mustMatch){
	var options = mustMatch==true?inputMustMatchOptions:inputDefaultOptions;
	$("#"+putNameId).unbind();//当一个文本框多次被设置不同的dataJson值时使用
	$("#"+putNameId).autocomplete(dataJson, options).result(function(event, row, formatted) {
		if(row!=undefined){
			$("#"+putKeyId).val(row.id);
		}
    });
}

/**
 * 自动填充下拉框实现方法：用于查询数据
 * @param dataJson  自动下拉框的数据
 * @param putNameId 存放名称的id
 * @param mustMatch 是否完全匹配
 */
function searchAutoComplete(dataJson, putNameId, mustMatch){
	var options = mustMatch==true?inputMustMatchOptions:inputDefaultOptions;
	$("#"+putNameId).unbind();//当一个文本框多次被设置不同的dataJson值时使用
	$("#"+putNameId).autocomplete(dataJson, options);
}

/**
*
*  URL encode / decode
*  http://www.webtoolkit.info/
* 注意，使用方法 Url.encode(string) 得到的是UTF-8编码的数据
**/
 
var Url = {
 
		// public method for url encoding
		encode : function (string) {
				return escape(this._utf8_encode(string));
		},
 
		// public method for url decoding
		decode : function (string) {
				return this._utf8_decode(unescape(string));
		},
 
		// private method for UTF-8 encoding
		_utf8_encode : function (string) {
				string = string.replace(/\r\n/g,"\n");
				var utftext = "";
 
				for (var n = 0; n < string.length; n++) {
 
						var c = string.charCodeAt(n);
 
						if (c < 128) {
								utftext += String.fromCharCode(c);
						}
						else if((c > 127) && (c < 2048)) {
								utftext += String.fromCharCode((c >> 6) | 192);
								utftext += String.fromCharCode((c & 63) | 128);
						}
						else {
								utftext += String.fromCharCode((c >> 12) | 224);
								utftext += String.fromCharCode(((c >> 6) & 63) | 128);
								utftext += String.fromCharCode((c & 63) | 128);
						}
 
				}
 
				return utftext;
		},
 
		// private method for UTF-8 decoding
		_utf8_decode : function (utftext) {
				var string = "";
				var i = 0;
				var c = c1 = c2 = 0;
 
				while ( i < utftext.length ) {
 
						c = utftext.charCodeAt(i);
 
						if (c < 128) {
								string += String.fromCharCode(c);
								i++;
						}
						else if((c > 191) && (c < 224)) {
								c2 = utftext.charCodeAt(i+1);
								string += String.fromCharCode(((c & 31) << 6) | (c2 & 63));
								i += 2;
						}
						else {
								c2 = utftext.charCodeAt(i+1);
								c3 = utftext.charCodeAt(i+2);
								string += String.fromCharCode(((c & 15) << 12) | ((c2 & 63) << 6) | (c3 & 63));
								i += 3;
						}
 
				}
 
				return string;
		}
 
}
