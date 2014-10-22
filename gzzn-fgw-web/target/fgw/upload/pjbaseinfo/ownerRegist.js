/**
 * JS描述：时间类型操作
 */

$(function(){
	 var validator = $("#editForm").validate({
			event:"blur",
			onkeyup:false,
			rules: {
	             "obj.organizationName":{   
	                 required: true,
	                 byteMaxLength: 150
	             },
	             "obj.workunitsregistercode":{   
	                 byteMaxLength:40,
	                 remote:{
		                 	type:"POST",
		                 	dataType:"JSON",
		                 	url:"./checkRegistCode.json",
		                 	data:{
		                 		"obj.organizationId":function(){
		                 			return $("#organizationId").val();
		                 		},
		                 		time:new Date().getTime()
		                 	}
		             }
	             },
	             "obj.workunitslinkman":{   
	                 byteMaxLength:50
	             },
	             "obj.workunitslinkmantel":{   
	            	 Mobile:true,
	                 byteMaxLength:50
	             }, 
	             "obj.workunitsperson":{   
	                 byteMaxLength:50
	             },
	             "obj.workunitsaddress":{   
	                 byteMaxLength:300
	             },
	             "ownerUser.loginName":{   
	                 required: true,
	                 byteMaxLength:30,
	                 remote:{
	                 	type:"POST",
	                 	dataType:"JSON",
	                 	url:"./checkRegistName.json",
	                 	data:{
	                 		"ownerUser.userId":function(){
	                 			return $("#userId").val();
	                 		},
	                 		time:new Date().getTime()
	                 	}
	                 }
	             }, 
	             "ownerUser.loginPwd":{   
	            	 required:true,
	                 byteMaxLength:20
	             }, 
	             "confirmPwd":{   
	            	 required:true,
	            	 equalTo: "#loginPwd"
	             }, 
	             "ownerUser.userName":{   
	            	 required:true,
	                 byteMaxLength:30
	             }, 
	             "ownerUser.sex":{   
	            	 required:true
	             }, 
	             "ownerUser.telmobile":{   
	            	 required:true,
	            	 Mobile:true,
	                 byteMaxLength:90
	             } 
		      },
		      messages:{
		    	  "obj.workunitsregistercode":{
		    		  remote:"该企业工商注册号已注册，请不要重复注册"
		    	  },
		    	  "ownerUser.loginName":{
		    		  remote:"该登录帐号已注册，请选择其他登录帐号"
		    	  }
		      },
			  submitHandler: function(form){
			      form.submit();
			  }		 	    
		});

	//重置表单
	$("#resetBtn").click(function() {
		validator.resetForm();
	});
});

/**
 * Ztree数据
 */
$(function(){
	 
	if($("#userId").val()!=""){//用户id不为空
		$("#loginName").attr("disabled",true);
	}
	 
	var cxt=$("#cxt").val();//得到工程名
	//区域
	//var xqDatas=getDatas("./getAreaJson.json");
	var xqDatas = getJsonDatas("../../com/getSysXqJson.json");
	$("#xqName").focus(function(){
		initZtree("xq",xqDatas,210,250);
	});
	//单位性质
	//var dwxzDatas=getDatas("./getUnitNatureJson.json");
	var dwxzDatas = getJsonDatas("../../com/getDictItemsJson.json","params.dictName=单位性质");
	$("#dwxzName").focus(function(){
		initZtree("dwxz",dwxzDatas,210,250);
	});
});

