<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
<title>
<c:if test="${not empty obj.organizationId}" var="result">
	编辑业主单位信息
</c:if>
<c:if test="${!result}">
	新增业主单位信息
</c:if>
</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="${ctx}/resources/js/jquery-validation/jquery.validate.expand.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/sys/sys.js" type="text/javascript"></script>
</head>
<body>
	<script type="text/javascript">
//<!--
		var setting = {
				check: {
					enable: true,
					chkStyle: "radio",
					radioType: "all"
				},
				view: {
					dblClickExpand: false
				},
				data: {
					simpleData: {
						enable: true
					}
				},
				callback: {
					onClick: onClick,
					onCheck: onRadioCheck
				}
			};
			
	   $(document).ready(function() {
	   		showMenu(6,64);
	   		
			//单位性质
			var dwxzDatas = getJsonDatas("${ctx}/com/getDictItemsJson.json","params.dictName=单位性质");
			var dwxzTree = initRadioTreeCheck("dwxz", setting, dwxzDatas, "", "${obj.workunitsquality}");
			//区域
			var xqIdDatas = getJsonDatas("${ctx}/com/getSysXqJson.json");
			var xqIdTree = initRadioTreeCheck("xqId", setting, xqIdDatas, "", "${obj.sysXq.xqId}");
			
			//重置表单
			$("#resetBtn").click(function() {
       			 validator.resetForm();
       			  //重置树形下拉框的相关选项：先清空【clearCheckNodes】，再设置对应的值【showNodesNameById】
       			 clearCheckNodes(dwxzTree);
       			 showNodesNameById(dwxzTree, "dwxz", "${obj.workunitsquality}");
       			 clearCheckNodes(xqIdTree);
       			 showNodesNameById(xqIdTree, "xqId", "${obj.sysXq.xqId}");
   			});
		
		
			//验证表单
		    var validator = $("#editForm").validate({
								event:"blur",
								onkeyup:false,
								rules: {
						             "obj.organizationName":{   
						                 required: true,
						                 byteMaxLength: 150
						             },
						              "dwxzName":{   
						                 required: true
						             },
						              "xqIdName":{   
						                 required: true
						             },
						             "obj.workunitsregistercode":{  
						              	 required: true, 
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
							             },
						                 byteMaxLength:40
						             },
						             "obj.workunitsperson":{ 
						              	 required: true,  
						                 byteMaxLength:50
						             },
						             "obj.workunitslinkman":{ 
						              	 required: true,  
						                 byteMaxLength:50
						             },
						             "obj.workunitslinkmantel":{ 
						              	 required: true,  
						                 byteMaxLength:50
						             }, 
						             "obj.workunitsaddress":{ 
						              	 required: true,  
						                 byteMaxLength:300
						             },
						             
						             "ownerUser.loginName":{   
						                 required: true,
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
						                 },
						                 byteMaxLength:30
						             }, 
						              "ownerUser.loginPwd":{   
						                 required: true,
						                 byteMaxLength:20
						             },
						             "confirmPwd":{   
						                 required: true,
						                 equalTo: "#loginPwd"
						             }, 
						              "ownerUser.userName":{   
						            	 required:true,
						                 byteMaxLength:30
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
			
   			//返回
			$("#backBtn").click(function() {
				window.location = "${ctx}/sys/organNew/listOwnerVerify.ac";
			});
			
   			
		});
	
	
	//-->
</script>

	<form id="editForm" action="${ctx}/sys/organNew/saveOwnerVerify.ac"  method="post">
		<input type="hidden" name="obj.organizationId" id="organizationId" value="${obj.organizationId}"/>
		<input type="hidden" name="obj.workunitsstatus" id="workunitsstatus" value="${obj.workunitsstatus}"/>
		<table style="width:100%">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle">
					<c:if test="${not empty obj.organizationId}" var="result">
						编辑业主单位信息
					</c:if>
					<c:if test="${!result}">
						新增业主单位信息
					</c:if>
				</td>
				<td class="tbHeadBg" style="width: 5%;"><div class="tbHeadMid"></div></td>
				<td class="tbHeadBg" style="width: 79%;">
				</td>
				<td class="tbHeadBg" style="width: 3.5%;">
					<div class="tbHeadRight"></div>
				</td>
			</tr>
		</table>
		<div class="editDiv">
			<table class="editTab" id="editTab">
				<tr>
					<th>业主单位名称<font class="msg">*</font></th>
					<td>
						<input type="text" name="obj.organizationName" id="organizationName" value="${obj.organizationName}" />
					</td>
				</tr>
				<tr>
					<th>单位性质<font class="msg">*</font></th>
					<td>
						<input type="text" name="dwxzName" id="dwxzSelName" class="text" value="" readonly="readonly" />
						<input type="hidden" name="obj.workunitsquality" id="dwxzSelID" value="${obj.workunitsquality}"/>
						<div id="dwxzDiv" class="menuContent" style="display:none; position: absolute;width:210px;height: 250px;">
							<ul id="dwxz" class="ztree"  style="margin-top:0;"></ul>
						</div>
					</td>
				</tr>
				<tr>
					<th>所在区域<font class="msg">*</font></th>
					<td>
						<input type="text" name="xqIdName" id="xqIdSelName" class="text" value="" readonly="readonly" />
						<input type="hidden" name="obj.sysXq.xqId" id="xqIdSelID" value="${obj.sysXq.xqId}"/>
						<div id="xqIdDiv" class="menuContent" style="display:none; position: absolute;width:210px;height: 250px;">
							<ul id="xqId" class="ztree"  style="margin-top:0;"></ul>
						</div>
					</td>
				</tr>
				<tr>
					<th>企业工商注册号(机构代码)<font class="msg">*</font></th>
					<td>
						<input type="text" name="obj.workunitsregistercode" id="workunitsregistercode" value="${obj.workunitsregistercode}" />
					</td>
				</tr>
				<tr>
					<th>单位法人<font class="msg">*</font></th>
					<td>
						<input type="text" name="obj.workunitsperson" id="workunitsperson" value="${obj.workunitsperson}" />
					</td>
				</tr>
				<tr>
					<th>单位联系人<font class="msg">*</font></th>
					<td>
						<input type="text" name="obj.workunitslinkman" id="workunitslinkman" value="${obj.workunitslinkman}" />
					</td>
				</tr>
				<tr>
					<th>联系人电话<font class="msg">*</font></th>
					<td>
						<input type="text" name="obj.workunitslinkmantel" id="workunitslinkmantel" value="${obj.workunitslinkmantel}" />
					</td>
				</tr>
				<tr>
					<th>单位地址<font class="msg">*</font></th>
					<td>
						<input type="text" name="obj.workunitsaddress" id="workunitsaddress" value="${obj.workunitsaddress}" />
					</td>
				</tr>
			</table>
		</div>
		<hr/>
		<table style="width:100%">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle">
					个人信息
				</td>
				<td class="tbHeadBg" style="width: 5%;"><div class="tbHeadMid"></div></td>
				<td class="tbHeadBg" style="width: 79%;">
					
				</td>
				<td class="tbHeadBg" style="width: 3.5%;">
					<div class="tbHeadRight"></div>
				</td>
			</tr>
		</table>
		<input type="hidden" name="ownerUser.userId" id="userId" value="${ownerUser.userId}"/>
		<input type="hidden" name="ownerUser.useStatus" id="useStatus" value="${ownerUser.useStatus}"/>
		<div class="editDiv">
			<table class="editTab" id="editTab2">
				<tr>
					<th>登录帐号<font class="msg">*</font></th>
					<td>
						<input type="text" name="ownerUser.loginName" id="loginName" value="${ownerUser.loginName}" />
					</td>
				</tr>
				<c:if test="${empty ownerUser.userId}" var="result">
					<tr>
						<th>登录密码<font class="msg">*</font></th>
						<td>
							<input type="password" name="ownerUser.loginPwd" id=loginPwd />
						</td>
					</tr>
					<tr>
						<th>确认密码<font class="msg">*</font></th>
						<td>
							<input type="password" name="confirmPwd" id="confirmPwd" />
						</td>
					</tr>
				</c:if>
				<tr>
					<th>真实姓名<font class="msg">*</font></th>
					<td>
						<input type="text" name="ownerUser.userName" id="userName" value="${ownerUser.userName}" />
					</td>
				</tr>
				<tr>
					<th >性别<font class="msg">*</font></th>
			     	<td>
			     		<c:if test="${ownerUser.sex eq 1}" var="result">
			     			<input type="radio" name="ownerUser.sex" value="1" class="radio" checked="checked">男
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="ownerUser.sex" value="0" class="radio" >&nbsp;女
			     		</c:if>
			     		<c:if test="${!result}">
							<input type="radio" name="ownerUser.sex" value="1" class="radio">男
							&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<input type="radio" name="ownerUser.sex" value="0" class="radio" checked="checked">&nbsp;女	
						</c:if>
			     	</td>
				</tr>
				<tr>
					<th>手机号码<font class="msg">*</font></th>
					<td>
						<input type="text" name="ownerUser.telmobile" id="telmobile" value="${ownerUser.telmobile}" />
					</td>
				</tr>
			</table>
		</div>
		<br/>
		<table style="width:100%">
			<tr>
				<td align="center" style="width: 100%;">
					<input type="submit" class="btn" id="saveBtn" value="保存" />
					<input type="reset" class="btn" id="resetBtn" value="重置" /> 
					<input type="button" class="btn" id="backBtn" value="返回" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>