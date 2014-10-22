/**
 * JS描述：时间类型操作
 */

$(function(){
	/**Ztree数据*/
	var ctx=$("#ctx").val();//得到工程名
	var width = 210,height=200;//弹出树形选择框的宽、高
	var orgDatas1 = getJsonDatas(ctx+"/com/getSysOrgJson.json","");
	var deptDatas = getJsonDatas(ctx+"/com/getOrgDeptTreeJson.json","params.workunitstype=3");
	$("#sendDeptName").focus(function(){ initZtree("sendDept", deptDatas, width, height); });
	$("#receiveDeptName").focus(function(){ initZtree("receiveDept", deptDatas, width, height); });
	
	inputAutoComplete(orgDatas1, "receiveOrganizationName", "receiveOrganizationId", true);
	
});




