/**
 * 发送短信
 * @param path访问url前缀
 * @param title弹出窗口显示标题
 * @param smsUse短信用途
 * @param eventid事件id
 */
function sendMsg(path,title,smsUse,eventid,eventTitle,content){
	parent.mac.opendlg({
		title : title,
		message : $("#msgDiv").html(),
		width : 700,
		//height : 400,
		top : 150,
		buttons:[{
			text : '取消',
			click : function(d) {
				d.close();
			}
		},{
			text : '发送',
			click : function(d) {
				if(doSendMsg(path,smsUse,eventid)){
					d.close();
				}
			}
		},{
			text : '通讯录',
			click : function(d) {
				selectUser('tel',function(str){
					parent.$("#receiveSmsMobile").val(str);
				});
			}
		}]
	});
	
	parent.$("#smsTitle").val(eventTitle);
	parent.$("#smsContent").val(content);
}

/**
 * 发送短信
 * @param path访问url前缀
 * @param smsUse短信用途
 * @param eventid事件id
 */
function doSendMsg(path,smsUse,eventid){
	var t = parent.$("#smsTitle");
	var c = parent.$("#smsContent");
	var r = parent.$("#receiveSmsMobile");
	if(!checkLength(t,50)){
		return false;
	}
	if(!checkNotEmpty(c) || !checkLength(c,70)){
		return false;
	}
	if(!checkNotEmpty(r) || !checkLength(r,100) ||!checkTelephone(r)){
		return false;
	}
	
	$.post(path+"/eds/message/send.json",{"id":eventid,"obj.smsTitle":t.val(),"obj.smsContent":c.val(),"obj.receiveSmsMobile":r.val(),"obj.smsUse":smsUse},function(data){
		var r = parent.mac.eval(data);
		//if(r.flag){
		//	
		//}
		parent.mac.alert(r.msg);
	});
	return true;
}

/**
 * 发送传真
 * @param title弹出窗口标题
 * @param faxUse传真用途
 * @param eventid事件id
 */
function sendFax(title,faxUse,eventid,eventTitle,content){
	parent.mac.opendlg({
		title : title,
		message : $("#faxDiv").html(),
		width : 700,
		//height : 400,
		top : 150,
		buttons:[{
			text : '取消',
			click : function(d) {
				d.close();
			}
		},{
			text : '发送',
			click : function(d) {
				if(doSendFax(faxUse,eventid)){
					d.close();
				}
			}
		},{
			text : '通讯录',
			click : function(d) {
				selectUser('fax',function(str){
					parent.$("#faxReceiver").val(str);
				});
			}
		}]
	});
	
	parent.$("#faxTitle").val(eventTitle);
	parent.$("#faxDesc").val(content);
}

/**
 * 发送传真
 * @param faxUse传真用途
 * @param eventid事件id
 */
function doSendFax(faxUse,eventid){
	var c = parent.$("#faxFile");
	var r = parent.$("#faxReceiver");
	if(!checkLength(parent.$("#faxTitle"),50)){
		return false;
	}
	if(!checkNotEmpty(c)){
		return false;
	}
	if(!checkNotEmpty(r) || !checkLength(r,100) ||!checkTelephone(r)){
		return false;
	}
	if(!checkLength(parent.$("#faxDesc"),1000)){
		return false;
	}
	
	parent.$("#faxEventId").val(eventid);
	parent.$("#faxUse").val(faxUse);
	parent.$("#faxDivForm").attr("target",window.name);
	parent.$("#faxDivForm").submit();
	
	return true;
}

/**
 * 通讯录选择用户
 * @param type应用类别'tel'或者'fax'
 * @param func回调函数，用于回填选择的联系人号码
 */
function selectUser(type,func){
	$("#telOrfax").val(type);
	parent.mac.opendlg({
		title : '通讯录',
		message : $("#addressbookDiv").html(),
		width : 700,
		//height : 400,
		top : 150,
		buttons:[{
			text : '取消',
			click : function(d) {
				d.close();
			}
		},{
			text : '确定',
			click : function(d) {
				var str = "";	
				var selected = parent.document.getElementById("selected");
				for (var i = 0; i < selected.length; i++) {
					str = str + "," + selected.options[i].value;
				}	
				if(str != ""){
					str = str.substring(1);//去掉开头的,字符	
				}
				//parent.$("#receiveSmsMobile").val(str);
				func(str);
				
				d.close();
			}
		}]
	});
}

//根据事件类型，控制附加信息显示内容
function checkedInput(path,eventId){
	$.post(path + "/eds/info/getEventClassify.json",{"id":eventId},function(data){
		if(data == "11000"){
			$(".11000").removeClass("hide");
			$(".12000").addClass("hide");
			$(".13000").addClass("hide");
			$(".14000").addClass("hide");
		}else if(data == "12000"){
			$(".12000").removeClass("hide");
			$(".11000").addClass("hide");
			$(".13000").addClass("hide");
			$(".14000").addClass("hide");
		}else if(data == "13000"){
			$(".13000").removeClass("hide");
			$(".11000").addClass("hide");
			$(".12000").addClass("hide");
			$(".14000").addClass("hide");
		}else if(data == "14000"){
			$(".14000").removeClass("hide");
			$(".11000").addClass("hide");
			$(".12000").addClass("hide");
			$(".13000").addClass("hide");
		}
	});
}

//预览预案
function showPlan(path,id){
	parent.openDialog(path + "/eps/plaBaseDetail.json?plaBaseId="+id + "&isShowAlone=2",{
		title:"预览预案",
		height : 'auto'
	});
}

//选择预案
function selectPlan(path){
	var c = $("input[name='obj.classifyId']").val(); //事件类型
	var t = $("#eventlevelId").val(); //事件等级
	//加载预案列表数据
	$.post(path + "/eps/getPlanList.json",{"classifyId":c,"eventlevelId":t},function(data){
		//alert(data);
		var showCount = 8;//设置最多显示的事件条数
		var obj = parent.mac.eval(data);
		parent.$("#planTbody").html("");
		for(var i=0;i<obj.count && i<showCount;i++){
			var r = obj.result[i];
			var str = '<tr><td><input type="radio" name="planRadio" value="'+r.plaBaseId+'"></td>'+
				'<td id="td'+r.plaBaseId+'">'+r.plaName+'</td>'+
				'<td>'+r.plaType+'</td>'+
				'<td><a href="javascript:showPlan(\''+path +'\',\''+r.plaBaseId +'\');">预览</a></td></tr>';

			parent.$("#planTbody").append(str);
		}
		parent.$("input[name=planRadio]")[0].checked = true;
	});
	
}

