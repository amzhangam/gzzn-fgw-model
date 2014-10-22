/**
 * JS描述：时间类型操作
 */

//$(function(){
//	var validator = $("#editForm").validate({
//		event:"blur",
//		onkeyup:false,
//		 rules: {
//             "obj.projectType":{   
//             },
//             "obj.projectName":{   
//                 required: true,
//                 byteMaxLength:100
//             }
//	      },
//	      messages:{
//	    	  "mobile":{
//	    		  Mobile:"请输入一个合法的传真"
//	    	  }
//	      }
//	});
//});

//	$(function(){
//		var validator = $("#editForm").validate({
//			event:"blur",
//			onkeyup:false,
//			 rules: {
//	             "obj.projectName":{   
//	                 required: true,
//	                 byteMaxLength:10
//	             }
//		      },
//		      messages:{
//		    	  "mobile":{
//		    		  Mobile:"请输入一个合法的传真"
//		    	  }
//		      }
//		});
//	});

//function addOrEdit(){
//	if($("#form1").valid()){
//		var options = {
//			url:'./saveOwner.json',
//			type : "POST",
//			dataType : "json",
//			success:function(data){
//				  if(data.success){
//					 mac.alert(data.info);
//					 backQuePage("./listOwner.ac");
//				  }else{
//				  	mac.alert("保存失败!");
//				  }
//			}  
//		};
//		$("#form1").ajaxSubmit(options);
////		$("#form1").submit();
//	}
//	return false;
//}

/**
 * Ztree数据
 */
$(function(){
	var cxt=$("#cxt").val();//得到工程名
	/**var sysOrganizationByDeclareunitsidDatas=getDatas("./getOwnerOrganizationJson.json");
	$("#sysOrganizationByDeclareunitsidName").focus(function(){
		initZtree("sysOrganizationByDeclareunitsid",sysOrganizationByDeclareunitsidDatas,250,250);
	});*/
	var sysOrganizationByDeclareunitsidDatas = getJsonDatas("../../com/getSysOrgJson.json","params.workunitstype=1,2");
	inputAutoComplete(sysOrganizationByDeclareunitsidDatas, "sysOrganizationByDeclareunitsidName", "sysOrganizationByDeclareunitsidId", true);
	/**var sysOrganizationByDirectorUnitsidDatas=getDatas("./getManageOrganizationJson.json");
	$("#sysOrganizationByDirectorunitsidName").focus(function(){
		initZtree("sysOrganizationByDirectorunitsid",sysOrganizationByDirectorUnitsidDatas,250,250);
	});*/
	var sysOrganizationByDirectorUnitsidDatas = getJsonDatas("../../com/getSysOrgJson.json","params.workunitstype=2");
	inputAutoComplete(sysOrganizationByDirectorUnitsidDatas, "sysOrganizationByDirectorunitsidName", "sysOrganizationByDirectorunitsidId", true);
	
//	var projectTypeDatas=getDatas("./getProjectTypeJson.json");
//	$("#projectTypeName").focus(function(){
//		initZtree("projectType",projectTypeDatas,150,200);
//	});
//	var hyflDatas=getDatas("./getSysHyflJson.json");
//	$("#hyflName").focus(function(){
//		initZtree("hyfl",hyflDatas,300,300);
//	});
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
//	var yearDatas=getDatas("./getYearJson.json");
//	$("#finishContentYearName").focus(function(){
//		initZtree("finishContentYear",yearDatas,150,200);
//	});
//	$("#expectFinishInvestYearName").focus(function(){
//		initZtree("expectFinishInvestYear",yearDatas,150,200);
//	});
//	$("#startyearName").focus(function(){
//		initZtree("startyear",yearDatas,150,200);
//	});
//	$("#endyearName").focus(function(){
//		initZtree("endyear",yearDatas,150,200);
//	});
//	$("#planInvestYearName").focus(function(){
//		initZtree("planInvestYear",yearDatas,150,200);
//	});
	
	var nextauditdeptDatas=getDatas("./getSysDeptJson.json");
	$("#nextauditdeptName").focus(function(){
		initZtree("nextauditdept",nextauditdeptDatas,150,200);
	});
});

