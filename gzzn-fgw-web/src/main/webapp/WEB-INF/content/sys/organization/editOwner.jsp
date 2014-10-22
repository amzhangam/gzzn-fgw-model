<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags"%>
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
	<script src="../../resources/js/jquery-validation/jquery.validate.expand.js" type="text/javascript"></script>
	<script src="../../resources/js/jquery/jquery.form.js" type="text/javascript"></script>
	<script src="../../resources/js/eps/ztreePublic.js" type="text/javascript"></script>
	<script src="../../resources/js/sys/editOwner.js"  type="text/javascript"></script>
	<script src="../../resources/js/sys/public.js" type="text/javascript"></script>
	<script src="../../resources/js/jquery-validation/jquery.validate.expand.js" type="text/javascript"></script>
	<script src="../../resources/js/sys/sys.js" type="text/javascript"></script>
	<script type="text/javascript">
//<!--
	   $(document).ready(function() {
	   		showMenu(6,64);
		
			//验证表单
		    var validator = $("#editForm").validate({
								event:"blur",
								onkeyup:false,
								rules: {
						             "obj.organizationName":{   
						                 required: true,
						                 byteMaxLength:50
						             },
						             "obj.workunitsregistercode":{   
						                 required: true,
						                 byteMaxLength:100
						             },
						             "obj.workunitslinkman":{   
						                 required: true,
						                 byteMaxLength:100
						             },
						             "obj.workunitslinkmantel":{   
						            	 required:true,
						                 byteMaxLength:11
						             }, 
						             "obj.workunitsperson":{   
						            	 required:true,
						                 byteMaxLength:100
						             },
						             "obj.workunitsaddress":{   
						            	 required:true,
						                 byteMaxLength:200
						             },
						             "sysXq.xqmc":{   
						            	 required:true
						             },
						             "workunitsquality":{   
						            	 required:true
						             }
							      },
							      
							    submitHandler: function(form){
							    	form.submit();
							    }		 	    
							});
			
   			//返回
			$("#backBtn").click(function() {
				window.location = "${ctx}/sys/organization/list.ac";
			});
			
			//重置表单
			$("#resetBtn").click(function() {
       			 validator.resetForm();
   			});
   			
		});
	
	
	//-->
</script>
</head>
<body>
	<form id="editForm" action="./saveOwner.ac"  method="post">
		<input type="hidden" name="obj.organizationId" value="${obj.organizationId}"/>
		<table style="width:100%">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle">
					单位信息
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
					<th>业主单位名称：<font class="msg">*</font></th>
					<td>
						<input type="text" name="obj.organizationName" id="organizationName" value="${obj.organizationName}" />
					</td>
				</tr>
				<tr>
					<th>企业工商注册号(机构代码)：<font class="msg">*</font></th>
					<td>
						<input type="text" name="obj.workunitsregistercode" id="workunitsregistercode" value="${obj.workunitsregistercode}" />
					</td>
				</tr>
				<tr>
					<th>单位性质：<font class="msg">*</font></th></th>	
			         <td >
			        	<input type="text" name="workunitsqualityName" id="dwxzName"
							          value="<s:property value="xzMap[#attr.workunitsquality]"/>" readonly="readonly" class="text"/>
							        <input type="hidden" name="workunitsquality" id="dwxzId"  
							        value="<s:property value="#attr.workunitsquality"/>"/>
			      	</td>
				</tr>
				<tr>
					<th>所在区域：<font class="msg">*</font></th></th>	
			         <td >
			        	<input type="text" name="sysXq.xqmc" id="xqName"
							          value="<s:property value="#attr.sysXq.xqmc"/>" readonly="readonly" class="text"/>
							        <input type="hidden" name="sysXq.xqId" id="xqId"  
							        value="<s:property value="#attr.sysXq.xqId"/>"/>
			      	</td>
				</tr>
				<tr>
					<th>单位法人：<font class="msg">*</font></th>
					<td>
						<input type="text" name="obj.workunitsperson" id="workunitsperson" value="${obj.workunitsperson}" />
					</td>
				</tr>
				<tr>
					<th>项目联系人：<font class="msg">*</font></th>
					<td>
						<input type="text" name="obj.workunitslinkman" id="workunitslinkman" value="${obj.workunitslinkman}" />
					</td>
				</tr>
				<tr>
					<th>项目联系人手机号码：<font class="msg">*</font></th>
					<td>
						<input type="text" name="obj.workunitslinkmantel" id="workunitslinkmantel" value="${obj.workunitslinkmantel}" />
					</td>
				</tr>
				<tr>
					<th>业主单位地址：<font class="msg">*</font></th>
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
		<input type="hidden" name="ownerUser.userId" value="${ownerUser.userId}"/>
		<div class="editDiv">
			<table class="editTab" id="editTab2">
				<tr>
					<th>用户名：<font class="msg">*</font></th>
					<td>
						<input type="text" name="ownerUser.loginName" id="loginName" value="${ownerUser.loginName}" />
					</td>
				</tr>
				<tr>
					<th>真实姓名：<font class="msg">*</font></th>
					<td>
						<input type="text" name="ownerUser.userName" id="userName" value="${ownerUser.userName}" />
					</td>
				</tr>
				<tr>
					<th>手机号码：<font class="msg">*</font></th>
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
						 <input type="submit" class="btn" value="确定"  id="saveBtn"  />
						 <input type="reset" class="btn" id="resetBtn" value="重置"> 
						 <input type="button" class="btn" value="返回"  onclick="backQuePage('./list.ac')">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>