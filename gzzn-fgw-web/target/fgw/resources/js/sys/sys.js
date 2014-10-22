/**
 * 点击checkAll控制全部checkBox的选中与不选中
 */
function checkAllBox(){
	$("#checkAll").click(function() {
		var tag = $(this).attr("checked");
		if (tag) {
			$(".subcheck").attr("checked", "true");
		} else {
			$(".subcheck").removeAttr("checked");
		}
	});
}

/**
 * 当用户点击删除按钮时，将用户选中（checkBox）的记录全部删除
 * @param url
 */
function delBtnClick(url){
	$("#delBtn").click(function(){
		var ids = "";
		$(".subcheck").each(function(){
			if($(this).attr("checked")){
				ids += "@" + $(this).val();
			}
		});
		if(ids.length == 0){
			mac.alert("请选择要删除的记录");
			return;
		}
		ids = ids.substring(1);
		//alert(ids);
		mac.confirm('<p>确认要删除已选中的记录吗?</p>', function(){
			window.location = url + ids;
		}, null);
	});
}


/**
 * 导出数据到excel
 * @param url
 * @param tableid
 * @param rowNum 该参数不传是，默认=2
 */
function exportexcel(url,tableid,rowNum){
	//alert("===哈哈哈，导出数据啦==="+rowNum);
	var rowNum = (rowNum==undefined||rowNum==null)?2:rowNum;
	var table = document.getElementById(tableid);
	if(table.rows.length == rowNum){
		alert("没有可以导出的数据.");
		return;
	}
	var oldurl = document.forms[0].action;
	document.forms[0].action = url;
	document.forms[0].submit();
	document.forms[0].action = oldurl;
}


/**
 * 点击文本框时，控制树形下拉框的显示与隐藏
 * @param clickId
 * @param treeSelDivId
 */
function showHideTreeSel(clickId,treeSelDivId){
	var offset,h;
	var treeDiv = $("#"+treeSelDivId);
	var onBodyDown = function(event){//定义绑定事件event.target.id == "menuBtn" || 
		if (!(event.target.id == clickId || event.target.id == treeSelDivId || $(event.target).parents("#"+treeSelDivId).length>0)) {
			treeDiv.fadeOut("fast");
			$("body").unbind("mousedown", onBodyDown);
		}
	};
	$("#"+clickId).bind("click",function(event){
		offset = $(this).offset();
		h = $(this).outerHeight();
		treeDiv.css({"left":offset.left + "px" ,"top":offset.top + h + "px" }).slideDown("fast");
		$("body").bind("mousedown", onBodyDown);
	});
}

/**
 * click树节点时的回调函数
 * @param e
 * @param treeId
 * @param treeNode
 * @returns {Boolean}
 */
function onClick(e, treeId, treeNode) {
	var zTree = $.fn.zTree.getZTreeObj(treeId);
	zTree.checkNode(treeNode, !treeNode.checked, null, true);
	return false;
}

/**
 * 单选树节点时的回调函数
 * 在此规定选中节点的名称跟ID存放的文本框分别为：
 * treeId+"SelName"  和 treeId+"SelID"
 * @param e
 * @param treeId
 * @param treeNode
 */
function onRadioCheck(e, treeId, treeNode) {
	//初始化定义变量：cId选中节点id、cName选中节点名称
	var cId = "" , cName = "" ,
		zTree = $.fn.zTree.getZTreeObj(treeId),
		nodes = zTree.getCheckedNodes(true);
	if(nodes.length>0){
		cId = nodes[0].id;
		cName = nodes[0].name;
	}
	//$("#"+treeId+"SelID").val( cId );
	//$("#"+treeId+"SelName").val( cName );
	$("#"+treeId+"SelID").attr("value", cId );
	$("#"+treeId+"SelName").attr("value",cName );
}

/**
 * 多选树节点时的回调函数
 * 在此规定选中节点的名称跟ID存放的文本框分别为：
 * treeId+"SelName"  和 treeId+"SelID"
 * @param e
 * @param treeId
 * @param treeNode
 */
function onCheckBoxCheck(e, treeId, treeNode) {
	//初始化定义变量：cId选中节点id、cName选中节点名称
	var  cId = "" , cName = "" ,
		zTree = $.fn.zTree.getZTreeObj(treeId),
		nodes = zTree.getCheckedNodes(true);
	for (var i=0; i<nodes.length; i++) {
		cName += nodes[i].name + ",";
		cId += nodes[i].id + ",";
	}
	if (cId.length > 0) cId = cId.substring(0, cId.length-1);
	if (cName.length > 0) cName = cName.substring(0, cName.length-1);
	$("#"+treeId+"SelID").val( cId );
	$("#"+treeId+"SelName").val( cName );
}

/**
 * 初始化只允许单选的树形下拉选择框
 * 在此规定选中节点的名称跟ID存放的文本框分别为：
 * treeId+"SelName"  和 treeId+"SelID"
 * 存放树的Div层为：treeId+"Div"
 * @param treeId 
 * @param setting
 * @param zNodes
 * @param nowId 当前操作信息的ID
 * @param nowUpId 上级信息ID
 * @returns
 */
function initRadioTreeCheck(treeId, setting, zNodes, nowId, nowUpId){
	var zTree = $.fn.zTree.init($("#"+treeId), setting, zNodes);//初始化树1
	showHideTreeSel(treeId+"SelName",treeId+"Div");//控制树形下拉框的展示与隐藏
	chkDisabledNode(zTree,nowId);//设置不可选的节点
	checkDefaultNode(zTree,nowUpId,true);//设置指定节点为选中状态
	return zTree;
}

/**
 * 初始化允许多选的树形下拉选择框
 * 在此规定选中节点的名称跟ID存放的文本框分别为：
 * treeId+"SelName"  和 treeId+"SelID"
 * 存放树的Div层为：treeId+"Div"
 * @param treeId 
 * @param setting
 * @param zNodes
 * @param selNodesId 选中的节点id,多个间使用,隔开
 * @returns
 */
function initCheckBoxTree(treeId, setting, zNodes, selNodesId){
	var zTree = $.fn.zTree.init($("#"+treeId), setting, zNodes);//初始化树1
	showHideTreeSel(treeId+"SelName",treeId+"Div");//控制树形下拉框的展示与隐藏
	checkDefaultNode(zTree,selNodesId,true);//在树中选中用户选择的节点
	return zTree;
}

/**
 * 设置树的指定节点【以及该节点的全部子节点】为不可选状态
 * @param zTree 指定树
 * @param nowId 指定节点
 */
function chkDisabledNode(zTree,nowId){
	if(/[^(^\s*)|(\s*$)]/.test(nowId)){///[^(^\s*)|(\s*$)|0]/
		var nowNode = zTree.getNodeByParam("id", nowId);
		if(nowNode!=null){
			zTree.setChkDisabled(nowNode, true ,false, true);
		}
	}
}

/**
 * 设置树的指定节点为选中状态，并且展开所选节点的全部父节点
 * @param zTree
 * @param nowUpId
 * @param checked Boolean 
 * checked = true 表示勾选节点  checked = false 表示节点取消勾选 省略此参数，则根据对此节点的勾选状态进行 toggle 切换
 * 不影响 treeNode.nochecked = true 的节点。
 */
function checkDefaultNode(zTree,nowUpId,checked){
	if(/[^(^\s*)|(\s*$)]/.test(nowUpId)){///[^(^\s*)|(\s*$)|0]/
		var arr = nowUpId.split(",");
		for(var i=0;i<arr.length;i++){
			var nowUpNode = zTree.getNodeByParam("id", arr[i]);
			if(nowUpNode!=null){
				zTree.checkNode(nowUpNode, checked, null, true);
				if(nowUpNode.getParentNode()!=null){
					zTree.expandNode(nowUpNode.getParentNode(),true);
				}
			}
		}
	}
}


/**
 * 清空树中被选中的全部节点
 * @param treeObj
 */
function clearCheckNodesObj(treeObj){
	 var nodes = treeObj.getCheckedNodes(true);//当前被选中节点的信息
	 if(nodes!=null){
		 for(var i=0;i<nodes.length;i++){
		 	 treeObj.checkNode(nodes[i], false, null, true);//设置该节点的状态为不选中
		 }	  
	 }
}
/**
 * 清空树中被选中的全部节点
 * @param treeObj
 * @param treeId
 */
function clearCheckNodes(treeObj, treeId){
	 if(treeObj==null){
		treeObj = $.fn.zTree.getZTreeObj(treeId); 
	 }
	 clearCheckNodesObj(treeObj);
}

/**
 * 根据给出的树的id值选中树中的相应节点，
 * 并且将选中节点的名称填写入treeId+"SelID" 和 treeId+"SelName"输入框中
 * @param treeId 树ID
 * @param defaultVal 默认选择值
 */
function showNodesNameById(treeObj, treeId, defaultVal){
	 var  cId = "" , cName = "";
	 if(treeObj==null){
		treeObj = $.fn.zTree.getZTreeObj(treeId); 
	 }
	 if(/[^(^\s*)|(\s*$)|0]/.test(defaultVal)){
		var arr = defaultVal.split(",");
		for(var i=0;i<arr.length;i++){
			var nowUpNode = treeObj.getNodeByParam("id", arr[i]);
			if(nowUpNode!=null){
				cName += nowUpNode.name + ",";
				cId += nowUpNode.id + ",";
				treeObj.checkNode(nowUpNode, true, null, true);//选中该节点
				if(nowUpNode.getParentNode()!=null){
					treeObj.expandNode(nowUpNode.getParentNode(),true);
				}
			}
		}
	}
	if (cId.length > 0) cId = cId.substring(0, cId.length-1);
	if (cName.length > 0) cName = cName.substring(0, cName.length-1);
	
	//alert(treeId+"===showNodesNameById===="+defaultVal +"===="+cId+"==="+cName);
	
	$("#"+ treeId +"SelID").attr("value", cId );
	$("#"+ treeId +"SelName").attr("value",cName );
	/*$("#"+ treeId +"SelID").val( cId );
	$("#"+ treeId +"SelName").val( cName );*/
}



















