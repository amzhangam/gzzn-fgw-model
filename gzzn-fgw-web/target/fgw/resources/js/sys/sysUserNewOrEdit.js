/**
 * JS描述：时间类型操作
 */

$(function(){
	var status=$("#status").val();
	if(status=="7"){
		showMenu(7, 71);
	}else{
		showMenu(6, 61);
	}
	/**验证表单*/
	var validator = $("#form1").validate({
		event:"blur",
		onkeyup:false,
		rules: {
             "dto.userName":{   
                 required: true,
                 byteMaxLength:30
             },
             "dto.loginName":{   
                 required: true,
                 remote:{
                 	type:"POST",
                 	dataType:"JSON",
                 	url:"./checkLoginName.json",
                 	data:{
                 		userId:function(){
                 			return $("#userId").val();
                 		},
                 		time:new Date().getTime()
                 	}
                 },
                 byteMaxLength:30
             },
             "dto.sysOrganization.organizationName":{   
                 required: true
             },
             "dto.loginPwd":{   
            	 required: true,
            	 byteMaxLength:20
             },
             "confirmPwd":{   
                 required: true,
                 equalTo: "#loginPwd"
             }, 
             "dto.tel":{   
                 Tel:true,
                 byteMaxLength:20
             },
             "dto.fax":{   
                 Tel:true,
                 byteMaxLength:20
             },
             "dto.telmobile":{ 
            	 required: true,
                 Mobile:true,
                 byteMaxLength:20
             },
             "dto.email":{   
                 email:true,
                 byteMaxLength:50
             },
             "validFromDate":{   
                 required: true
             },
             "validToDate":{   
                 required: true
             }
	      },
	      messages:{
	    	  "dto.fax":{
	    		  Tel:"请输入一个合法的传真"
	    	  },
	    	  "dto.loginName":{
	    		  remote:"存在相似名称"
	    	  }
	      }
	});
	
	/**Ztree数据*/
	var cxt=$("#cxt").val();//得到工程名
	var width = 210,height=200;//弹出树形选择框的宽、高
	//单位信息：1-业主用户,2-主管单位用户,3-发改委用户 
	var orgDatas1 = getJsonDatas(cxt+"/com/getSysOrgJson.json","params.workunitstype=1");
	var orgDatas2 = getJsonDatas(cxt+"/com/getSysOrgJson.json","params.workunitstype=2");
	var orgDatas4 = getJsonDatas(cxt+"/com/getSysOrgJson.json","params.workunitstype=4");
	//var orgDatas3 = getJsonDatas(cxt+"/com/getSysOrgJson.json","params.workunitstype=3");
	chgUserTypeSel($('input[name="userType"]:checked').val(),orgDatas1, orgDatas2,orgDatas4);
	//所属部门信息
	//var deptDatas = getJsonDatas(cxt+"/com/getSysDeptJson.json","");
	var deptDatas = getJsonDatas(cxt+"/com/getOrgDeptTreeJson.json","params.workunitstype=3");
	$("#deptName").focus(function(){ initZtree("dept", deptDatas, width, height); });
	//角色类型
	var roleTypeDatas=getDatas(cxt+"/sys/getRoleTypeTreeJson.json");
	$("#roleTypeName").focus(function(){ initZtree("roleType", roleTypeDatas, width, height); });
	//所任职务
	var dutyDatas=getDatas(cxt+"/sys/duty/getSysDutyTreeJson.json");
	$("#dutyName").focus(function(){ initZtree("duty", dutyDatas, width, height); });
	
	$('input[name="userType"]').click(function(){
		chgUserTypeSel($('input[name="userType"]:checked').val(),orgDatas1, orgDatas2,orgDatas4);
	});
	
	//重置表单
	$("#resetBtn").click(function() {
		validator.resetForm();
		chgUserTypeSel($("#userType").val(),orgDatas1, orgDatas2,orgDatas4);
	});
	
});

function addOrEdit(){
	if($("#form1").valid()){
		var options = {
			url:'./sysUserAdd.json',
			type : "POST",
			dataType : "json",
			success:function(data){
				  if(data.success){
					 mac.alert(data.info);
					 backQuePage("./sysUserQuery.ac");
				  }else{
				  	mac.alert("保存失败!");
				  }
			}  
		};
		$("#form1").ajaxSubmit(options);
	}
	return false;
}

/**
 * 设置input的状态：disabled=true 不可编辑
 * @param putId 可以多个格式 "#id01,#id02"
 * @param disabled
 * @returns
 */
function setInputDisabled(putId, disabled){
	$(putId).attr("disabled",disabled);
	$(putId).css("background",disabled==true?"#EEEEEE":"#FFFFFF");
	if(disabled==true){//设置不可用输入框数据为空
		$(putId).attr("value","");
	}
}

/**
 * Ztree数据
 */
/**$(function(){
	var cxt=$("#cxt").val();//得到工程名
	var width = 210,height=200;//弹出树形选择框的宽、高
	
	//单位信息：1-业主用户,2-主管单位用户,3-发改委用户 
	var orgDatas1 = getJsonDatas(cxt+"/com/getSysOrgJson.json","params.workunitstype=1");
	var orgDatas2 = getJsonDatas(cxt+"/com/getSysOrgJson.json","params.workunitstype=2");
	//var orgDatas3 = getJsonDatas(cxt+"/com/getSysOrgJson.json","params.workunitstype=3");
	chgUserTypeSel($('input[name="userType"]:checked').val(),orgDatas1, orgDatas2);
	
	//所属部门信息
	//var deptDatas = getJsonDatas(cxt+"/com/getSysDeptJson.json","");
	var deptDatas = getJsonDatas(cxt+"/com/getOrgDeptTreeJson.json","params.workunitstype=3");
	$("#deptName").focus(function(){ initZtree("dept", deptDatas, width, height); });
	//角色类型
	var roleTypeDatas=getDatas(cxt+"/sys/getRoleTypeTreeJson.json");
	$("#roleTypeName").focus(function(){ initZtree("roleType", roleTypeDatas, width, height); });
	//所任职务
	var dutyDatas=getDatas(cxt+"/sys/duty/getSysDutyTreeJson.json");
	$("#dutyName").focus(function(){ initZtree("duty", dutyDatas, width, height); });
	
	$('input[name="userType"]').click(function(){
		chgUserTypeSel($('input[name="userType"]:checked').val(),orgDatas1, orgDatas2);
	});
	
	//重置表单
	$("#resetBtn").click(function() {
		validator.resetForm();
		chgUserTypeSel($("#userType").val(),orgDatas1, orgDatas2);
	});
	
});*/


/**function chgUserTypeSel(userType,orgDatas1, orgDatas2){
	if(userType==3){
		//所属单位——不可用
		setInputDisabled("#organizationName,#organizationId", true);
		//所属部门——可用，并且设置默认值
		setInputDisabled("#deptName,#deptId,#roleTypeName,#roleTypeId", false);
		$("#deptName").val("<s:property value='#attr.dto.sysDept.deptname'/>");
		$("#deptId").val("<s:property value='#attr.dto.sysDept.deptId'/>");
		$("#roleTypeName").val("<s:property value='jslxMap[dto.roleType]'/>");
		$("#roleTypeId").val("<s:property value='dto.roleType'/>");
	}else{
		//所属单位——可用
		setInputDisabled("#organizationName,#organizationId", false);
		$("#organizationName").val("<s:property value='#attr.dto.sysOrganization.organizationName'/>");
		$("#organizationId").val("<s:property value='#attr.dto.sysOrganization.organizationId'/>");
		//所属部门——不可用
		setInputDisabled("#deptName,#deptId,#roleTypeName,#roleTypeId", true);
		$("#organizationName").focus(function(){
			initZtree("organization", userType==1?orgDatas1:orgDatas2, 210, 200);
		});
	}
}*/



