<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>用户审核操作</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<script type="text/javascript">
//<!--
	   $(document).ready(function() {
	   		showMenu(6,64);
   			//返回
			$("#backBtn").click(function() {
				window.location.href = "${ctx}/sys/userVerifyQuery.ac";
			});
		});
	
	   function ownerVerify(verityStatus){
			$("#verityStatus").val(verityStatus);
			$("#form1").submit();
		}
	
	//-->
</script>

	<script src="../resources/js/jquery-validation/jquery.validate.expand.js" type="text/javascript"></script>
	<script src="../resources/js/jquery/jquery.form.js" type="text/javascript"></script>
	<script src="../resources/js/eps/ztreePublic.js" type="text/javascript"></script>
	<script src="../resources/js/sys/sysUserNewOrEdit.js"  type="text/javascript"></script>
	<script src="../resources/js/sys/public.js" type="text/javascript"></script>
	<script src="../resources/js/jquery-validation/jquery.validate.expand.js" type="text/javascript"></script>
	<script src="../resources/js/sys/sys.js" type="text/javascript"></script>
	<!-- 工程名 -->
	<input type="hidden" value="<%=request.getContextPath() %>" id="cxt"/>
	<form id="form1"  action="verifyProcess.ac"  method="post">
	<input type="hidden" name="verityStatus" id="verityStatus" value=""/>
		<table style="width:100%">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle">
					用户审核操作
				</td>
				<td class="tbHeadBg" style="width: 5%;"><div class="tbHeadMid"></div></td>
				<td class="tbHeadBg" style="width: 79%;">
					<div>
						<span style="float: right;">
					    <input type="hidden"  name="dto.userId"  id="userId"  value="<s:property value="#attr.dto.userId"/>"/>
					    <input type="hidden" id="userType"  value="<s:property value="#attr.dto.userType"/>"/>
					    <input type="hidden" id="status" name="status" disabled="disabled" value="<s:property value="#attr.status"/>">
						</span>
					</div>
				</td>
				<td class="tbHeadBg" style="width: 3.5%;">
					<div class="tbHeadRight"></div>
				</td>
			</tr>
		</table>
		<div class="editDiv">
		<table id="editTab"  class="editTab" >
		<tr>
					<th>用户类型<font class="msg">*</font></th>
			     	<td>
			     		<s:if test="#attr.dto.userType==1">
							<input type="radio" name="userType" value="1" class="radio" checked="checked" disabled="true" style="background:#EEE">业主用户
							<input type="radio" name="userType" value="2" class="radio" disabled="true" style="background:#EEE">主管单位用户
							<input type="radio" name="userType" value="3" class="radio" disabled="true" style="background:#EEE">市发改委用户
							<input type="radio" name="userType" value="4" class="radio" disabled="true" style="background:#EEE">区县发展和改革局用户
						</s:if>
						<s:elseif test="#attr.dto.userType==2">
							<input type="radio" name="userType" value="1" class="radio" disabled="true" style="background:#EEE">业主用户
							<input type="radio" name="userType" value="2" class="radio" checked="checked" disabled="true" style="background:#EEE">主管单位用户
							<input type="radio" name="userType" value="3" class="radio" disabled="true" style="background:#EEE">市发改委用户
							<input type="radio" name="userType" value="4" class="radio" disabled="true" style="background:#EEE">区县发展和改革局用户
						</s:elseif>
						<s:elseif test="#attr.dto.userType==3">
							<input type="radio" name="userType" value="1" class="radio" disabled="true" style="background:#EEE">业主用户
							<input type="radio" name="userType" value="2" class="radio" disabled="true" style="background:#EEE">主管单位用户
							<input type="radio" name="userType" value="3" class="radio" checked="checked" disabled="true" style="background:#EEE">市发改委用户
							<input type="radio" name="userType" value="4" class="radio" disabled="true" style="background:#EEE">区县发展和改革局用户
						</s:elseif>
						<s:elseif test="#attr.dto.userType==4">
							<input type="radio" name="userType" value="1" class="radio" disabled="true" style="background:#EEE">业主用户
							<input type="radio" name="userType" value="2" class="radio" disabled="true" style="background:#EEE">主管单位用户
							<input type="radio" name="userType" value="3" class="radio" disabled="true" style="background:#EEE">市发改委用户
							<input type="radio" name="userType" value="4" class="radio" checked="checked" disabled="true" style="background:#EEE">区县发展和改革局用户
						</s:elseif>
						<s:else>
							<input type="radio" name="userType" value="1" class="radio" disabled="true" style="background:#EEE">业主用户
							<input type="radio" name="userType" value="2" class="radio" disabled="true" style="background:#EEE">主管单位用户
							<input type="radio" name="userType" value="3" class="radio" checked="checked" disabled="true" style="background:#EEE">市发改委用户
							<input type="radio" name="userType" value="4" class="radio" disabled="true" style="background:#EEE">区县发展和改革局用户
						</s:else>
			     	</td>
				</tr>
		<tr>
			<th>所属单位<font class="msg">*</font></th>
			<td>
				<input type="text" name="sysOrganization.organizationName" id="organizationName" readonly="readonly" style="background:#EEE"
		          value="<s:property value="#attr.dto.sysOrganization.organizationName"/>" /> <!-- readonly="readonly" -->
		        <input type="hidden" name="sysOrganization.organizationId" id="organizationId"  
		        value="<s:property value="#attr.dto.sysOrganization.organizationId"/>"/>
			</td>
		</tr>
		<tr>
			<th>角色类型</th>	
	         <td >
	        	<input type="text" name="roleTypeName" id="roleTypeName"
		          value="<s:property value="jslxMap[dto.roleType]"/>"  readonly="readonly" style="background:#EEE"/>
		        <input type="hidden" name="dto.roleType" id="roleTypeId"  
		        value="<s:property value="dto.roleType"/>"/>
	      	</td>
		</tr>
		<tr>
			<th>所属部门</th>	
	         <td >
	        	<input type="text" name="sysDept.deptname" id="deptName"
		          value="<s:property value="#attr.dto.sysDept.deptname"/>"  readonly="readonly" style="background:#EEE"/>
		        <input type="hidden" name="sysDept.deptId" id="deptId"  
		        value="<s:property value="#attr.dto.sysDept.deptId"/>"/>
	      	</td>
		</tr>
		<tr>
				<th >所属职务</th>	
			        <td >
				        <input type="text" name="sysDuty.dutyName" id="dutyName"
				          value="<s:property value="#attr.dto.sysDuty.dutyName"/>"  readonly="readonly" style="background:#EEE"/>
				        <input type="hidden" name="sysDuty.dutyId" id="dutyId"  
				        value="<s:property value="#attr.dto.sysDuty.dutyId"/>"/>
			      	</td>
				</tr>
				<tr>
					<th >真实姓名<font class="msg">*</font></th>
					<td>
						<input type="text" name="userName" id="userName"   readonly="readonly" style="background:#EEE"
						value="<s:property value="#attr.dto.userName"/>"   class="text">
					</td>
				</tr>
				<tr>
					<th >性别<font class="msg">*</font></th>
			     	<td>
			     		<s:if test="#attr.dto.sex==1">
							<input type="radio" name="dto.sex" value="1" class="radio" checked="checked" disabled="true" style="background:#EEE">男
							<input type="radio" name="dto.sex" value="0" class="radio"  disabled="true" style="background:#EEE">女
						</s:if>
						<s:else>
							<input type="radio" name="dto.sex" value="1"  class="radio"  disabled="true" style="background:#EEE">男
							<input type="radio" name="dto.sex" value="0"  class="radio" checked="checked" disabled="true" style="background:#EEE">女
						</s:else>
			     	</td>
				</tr>
				<tr>
					<th >登录帐号<font class="msg">*</font></th>
					<td>
						<input type="text" name="dto.loginName" id="loginName"   readonly="readonly" style="background:#EEE"
							value="<s:property value="#attr.dto.loginName"/>" >
					</td>
				</tr>
				<tr>
					<th >电话</th>
					<td>
						<input type="text" name="dto.tel" id="tel"  value="<s:property value="#attr.dto.tel"/>"  readonly="readonly" style="background:#EEE"  class="text">
					</td>
				</tr>
				<tr>
					<th >手机号<font class="msg">*</font></th>
					<td>
						<input type="text" name="dto.telmobile" id="telmobile"   value="<s:property value="#attr.dto.telmobile"/>"  readonly="readonly" style="background:#EEE" class="text">
					</td>
				</tr>
				<tr>
					<th >传真</th>
					<td><input type="text" name="dto.fax" id="fax"  value="<s:property value="#attr.dto.fax"/>" readonly="readonly" style="background:#EEE"   class="text"></td>
				</tr>
				<tr>
					<th >电子邮件地址</th>
					<td><input type="text" name="dto.email" id="email"  value="<s:property value="#attr.dto.email"/>" readonly="readonly" style="background:#EEE"   class="text"></td>
				</tr>
			</table>
		</div>
		<br/>
		<table style="width:100%">
			<tr>
				<td align="center" style="width: 100%;">
						<c:if test="${objectMap['XMSB_XT_ZCYZSHGL_REVIEW']}">
						 <input type="button" class="btn" value="审核通过"  onclick="ownerVerify(2)" />
						 <input type="button" class="btn" value="审核不通过" onclick="ownerVerify(3)"  />
						</c:if>
						 <input type="button" class="btn" id="backBtn" value="返回" />
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
