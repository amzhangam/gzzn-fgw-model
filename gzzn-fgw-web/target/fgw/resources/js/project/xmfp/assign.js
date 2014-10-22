
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
	var nextUserDatas=getDatas("./getNextUserJson.json");
	$("#nextUserName").focus(function(){
		initZtree("nextUser",nextUserDatas,150,200);
	});
});

