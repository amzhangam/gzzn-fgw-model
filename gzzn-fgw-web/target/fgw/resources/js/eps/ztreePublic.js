/**
 * 生成Ztree，位置<input id="tg+'Id'" />处生成。
 * 并且datas数据的key放在<input id="tg+'Id'" />,显示值value放在<input id="tg+'Name'" />
 * 
 * @param tg 目标名(规则名),共同前缀，
 * @param datas 数据源
 * @param wid 显示框的宽度
 * @param hei 显示框的高度
 */
function initZtree(tg,datas,wid,hei){
		initDIV(tg,wid,hei);
	var setting = {
		check : {
			enable : true,
			chkStyle : "radio",
			radioType : "all"
		},
		view : {
			dblClickExpand : false
		},
		data : {
			simpleData : {
				enable : true
			}
		},
		callback : {
			onClick : onClick,
			onCheck:function(e, treeId, treeNode) {
				var zTree = $.fn.zTree.getZTreeObj(treeId);
				nodes = zTree.getCheckedNodes(true);
				if (nodes.length != 0) {
					$("#"+ tg +"Id").val(nodes[0].id);
					$("#"+ tg +"Name").val(nodes[0].name);
				} else {
					$("#"+ tg +"Id").val("");
					$("#"+ tg +"Name").val("");
				}
			}
		}
	};
	$.fn.zTree.init($("#"+ tg +"Tree"), setting,datas);
	showMenuTree(tg +"Name", tg +"Content", function(event) {
		checkMenuTree(tg +"Name", tg +"Content", event);
	});
	defaultSelect(tg +"Tree", tg +"Id");
}

/**
 * 获取数据源
 * @param url 访问地址
 * @returns {String} 数据源
 */
function getDatas(url){
	var data="";
	$.ajax({
		type:"POST",
		url:url,
		async:false,
		data:{
			time:new Date().getTime()
		},
		success:function(result){
			data=result;
		},
		error:function(){
			alert("系统响应异常！");
		},
		dataType:'json'
	});
	return data;
}

/**
 * 获取数据源
 * @param url 访问地址
 * @returns {String} 数据源
 */
function getDatas(url,type1){
	var data="";
	$.ajax({
		type:"POST",
		url:url,
		async:false,
		data:{
			time:new Date().getTime(),
			ut:type1
		},
		success:function(result){
			data=result;
		},
		error:function(){
			alert("系统响应异常！");
		},
		dataType:'json'
	});
	return data;
}

/**
 * 获取数据源
 * @param url 访问地址
 * @param queryParams 查询参数串，
 * 	格式如params.workunitstype=1&params.workunitsstatus=1
 * @returns {String} 数据源
 */
function getJsonDatas(url,queryParams){
	var data="";
	$.ajax({
		type:"POST",
		url:url,
		async:false,
		data: queryParams,
		success:function(result){
			data = result;
		},
		error:function(){
			alert("系统响应异常！");
		},
		dataType:'json'
	});
	return data;
}

/**
 * 在body中追加Ztree要用的DIV
 * @param tg 目标名(规则名)
 * @param wid 宽度
 * @param hei 高度
 */
function initDIV(tg,wid,hei){
	//如果不存在显示框DIV，则创建
	if($(tg+"Content").length==0){
		$("body").append("<div id=\""+ tg +"Content\" class=\"menuContent\""+
			"style=\"display: none; position: absolute;width: " + wid + "px; height: "+ hei +"px;\">"+
			"<ul id=\""+ tg +"Tree\" class=\"ztree\"></ul>"+
		"</div>");
	}
}

function onClick(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj(treeId);
	zTree.checkNode(treeNode, !treeNode.checked, null, true);
	return false;
}

function showMenuTree(targetId, contentId, onBodyDown) {
	var obj = $("#" + targetId);
	var offset = obj.offset();
	$("#" + contentId).css({
		left : offset.left + "px",
		top : offset.top + obj.outerHeight() + "px"
	}).slideDown("fast");

	$("body").bind("mousedown", onBodyDown);
}
function hideMenuTree(targetId, contentId) {
	$("#" + contentId).fadeOut("fast");
}
function checkMenuTree(targetId, contentId, event) {
	if (!(event.target.id == "menuBtn" || event.target.id == targetId
			|| event.target.id == contentId || $(event.target).parents(
			"#" + contentId).length > 0)) {
		hideMenuTree(targetId, contentId);
	}
}
function defaultSelect(treeId, valueId) {
	var zTree = $.fn.zTree.getZTreeObj(treeId);
	if (zTree!=null) {
		var node = zTree.getNodeByParam("id", $("#" + valueId).val(),
				null);
		if (node != null) {
			zTree.expandNode(node.getParentNode());
			zTree.checkNode(node, true, null, true);
		}
	}
}