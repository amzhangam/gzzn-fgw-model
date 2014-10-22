<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
<title>
业主信息审核
</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<script src="../../resources/js/jquery-validation/jquery.validate.expand.js" type="text/javascript"></script>
	<script src="../../resources/js/jquery/jquery.form.js" type="text/javascript"></script>
	<script src="../../resources/js/eps/ztreePublic.js" type="text/javascript"></script>
	<script src="../../resources/js/sys/public.js" type="text/javascript"></script>
	<script src="../../resources/js/jquery-validation/jquery.validate.expand.js" type="text/javascript"></script>
	<script src="../../resources/js/sys/sys.js" type="text/javascript"></script>
	<script type="text/javascript">
//<!--
	   $(document).ready(function() {
	   		showMenu(6,64);
		
   			//返回
			$("#backBtn").click(function() {
				window.location = "${ctx}/sys/organNew/listOwnerVerify.ac";
			});
			
			//重置表单
			$("#resetBtn").click(function() {
       			 validator.resetForm();
   			});
   			
		});
	
	
	//-->
	function submitPassVerify(){
		$("#editForm").attr("action", "./verifyProcess.ac?verityStatus=2");
		$("#editForm").submit();
	}
	function submitNotPassVerify(){
		$("#editForm").attr("action", "./verifyProcess.ac?verityStatus=1");
		$("#editForm").submit();
	}
</script>
</head>
<body>
	<form id="editForm" action="./verifyProcess.ac"  method="post">
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
						<input type="text" name="obj.organizationName" id="organizationName" value="${obj.organizationName}"  readonly="readonly"/>
					</td>
				</tr>
				<tr>
					<th>企业工商注册号(机构代码)：<font class="msg">*</font></th>
					<td>
						<input type="text" name="obj.workunitsregistercode" id="workunitsregistercode" value="${obj.workunitsregistercode}" readonly="readonly" />
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
						<input type="text" name="ownerUser.loginName" id="loginName" value="${ownerUser.loginName}" readonly="readonly" />
					</td>
				</tr>
				<tr>
					<th>真实姓名：<font class="msg">*</font></th>
					<td>
						<input type="text" name="ownerUser.userName" id="userName" value="${ownerUser.userName}" readonly="readonly" />
					</td>
				</tr>
				<tr>
					<th>手机号码：<font class="msg">*</font></th>
					<td>
						<input type="text" name="ownerUser.telmobile" id="telmobile" value="${ownerUser.telmobile}" readonly="readonly" />
					</td>
				</tr>
			</table>
		</div>
		<br/>
		<table style="width:100%">
			<tr>
				<td align="center" style="width: 100%;">
						<c:if test="${objectMap['XMSB_XT_ZCYZSHGL_REVIEW']}">
						 <input type="button" class="btn" value="审核通过"  name="verifyPass" id="verifyPass" onclick="submitPassVerify()" />
						 <input type="button" class="btn" value="审核不通过"  name="verifyNotPass"  id="verifyNotPass" onclick="submitNotPassVerify()"  />
						</c:if>
						 <input type="button" id="backBtn" class="btn" value="返回">
				</td>
			</tr>
		</table>
	</form>
</body>
</html>