
/**
 * Ztree数据
 */
$(function(){
	var cxt=$("#cxt").val();//得到工程名
	var sysOrganizationByDeclareunitsidDatas=getDatas("./getOwnerOrganizationJson.json");
	$("#sysOrganizationByDeclareunitsidName").focus(function(){
		initZtree("sysOrganizationByDeclareunitsid",sysOrganizationByDeclareunitsidDatas,250,250);
	});
	var sysOrganizationByDirectorUnitsIdDatas=getDatas("./getManageOrganizationJson.json");
	$("#sysOrganizationByDirectorUnitsIdName").focus(function(){
		initZtree("sysOrganizationByDirectorUnitsId",sysOrganizationByDirectorUnitsIdDatas,250,250);
	});
	$("#sendOrganizationName").focus(function(){
		initZtree("sendOrganization",sysOrganizationByDeclareunitsidDatas,250,250);
	});
	$("#processOrganizationName").focus(function(){
		initZtree("processOrganization",sysOrganizationByDeclareunitsidDatas,250,250);
	});
	var xqDatas=getDatas("./getSzqyTreeJson.json");
	$("#xqName").focus(function(){
		initZtree("xq",xqDatas,150,200);
	});
	var zjxzDatas=getDatas("./getZjxzTreeJson.json");
	$("#zjxzName").focus(function(){
		initZtree("zjxz",zjxzDatas,200,200);
	});
	var xmlxDatas=getDatas("./getXmlxTreeJson.json");
	$("#xmlxName").focus(function(){
		initZtree("xmlx",xmlxDatas,200,200);
	});
	var hylbDatas=getDatas("./getHylbTreeJson.json");
	$("#hylbName").focus(function(){
		initZtree("hylb",hylbDatas,200,200);
	});
	var nextauditdeptDatas=getDatas("./getSysDeptJson.json");
	$("#nextauditdeptName").focus(function(){
		initZtree("nextauditdept",nextauditdeptDatas,150,200);
	});
	$("#sendDeptName").focus(function(){
		initZtree("sendDept",nextauditdeptDatas,150,200);
	});
	$("#processDeptName").focus(function(){
		initZtree("processDept",nextauditdeptDatas,150,200);
	});
});

/*删除*/
function del(id){
	delData(id,"./delete.ac");
}

