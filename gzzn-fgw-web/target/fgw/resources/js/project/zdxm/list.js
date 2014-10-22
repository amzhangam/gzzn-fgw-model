/**
 * JS描述：预案等级查询
 */

$(function() {
		showMenu(6, 61);
		$("#checkAll").click(function() {
			var tag = $(this).attr("checked");
			if (tag) {
				$(".subcheck").attr("checked", "true");
			} else {
				$(".subcheck").removeAttr("checked");
			}
		});
		$("#clearCon").click(function(){
			$("#projectTypeName").val("");
			$("#pjstatusName").val("");
			$("#auditdeptName").val("");
			$("#projectTypeId").val("");
			$("#pjstatusId").val("");
			$("#auditdeptId").val("");
			$("#sfzdxmId").val("");
			$("#sfzdxmName").val("");
			$("#zjxzId").val("");
			$("#zjxzName").val("");
		});
		
		//勾选复选框，进行删除操作
		checkAllBox();
		delBtnClick("./delete.ac?id=");
	});
	
	/*添加业主*/
	function editOrAddUrl(val){
	   var url="./edit.ac?";
	    if(val!=""){
	    	url+="status=edit&projectId="+val;
	    }else{
	    	url+="status=add";
	    }
		location.href=url;
	}
	
	/*删除*/
	function del(id){
		delData(id,"./delete.ac");
	}
	
	/**
	 * Ztree数据
	 */
	$(function(){
		var xmlxDatas=getDatas("./getXmlxTreeJson.json");
		$("#xmlxName").focus(function(){
			initZtree("xmlx",xmlxDatas,150,200);
		});
		var hylbDatas=getDatas("./getHylbTreeJson.json");
		$("#hylbName").focus(function(){
			initZtree("hylb",hylbDatas,150,200);
		});
		var pjstatusDatas=getDatas("./getProjectStatusJson.json");
		$("#pjstatusName").focus(function(){
			initZtree("pjstatus",pjstatusDatas,150,200);
		});
		var auditdeptDatas=getDatas("./getSysDeptJson.json");
		$("#auditdeptName").focus(function(){
			initZtree("auditdept",auditdeptDatas,150,200);
		});
		var sfzdxmDatas=getDatas("./getSfzdxmJson.json");
		$("#sfzdxmName").focus(function(){
			initZtree("sfzdxm",sfzdxmDatas,150,200);
		});
		var zjxzDatas=getDatas("./getZjxzTreeJson.json");
		$("#zjxzName").focus(function(){
			initZtree("zjxz",zjxzDatas,150,200);
		});
	});
	
