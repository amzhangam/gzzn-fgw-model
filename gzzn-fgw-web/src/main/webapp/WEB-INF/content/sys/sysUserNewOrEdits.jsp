<%@page import="com.gzzn.fgw.util.IConstants"%>
<%@page import="com.gzzn.fgw.webUtil.CommonFiled"%>
<%@page import="com.gzzn.fgw.model.SysUser"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
SysUser user = (SysUser)request.getSession().getAttribute(CommonFiled.SESSION_LOGIN_USER);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>用户信息操作</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
</head>
<body>
<script type="text/javascript">
//<!--
$(document).ready(function() {
	$("#gly").hide();
	$("#other").hide();
	
});


//-->
function chgUserTypeSel(userType,orgDatas1, orgDatas2,orgDatas4){
	if(userType==3){
		//所属单位——不可用
		setInputDisabled("#organizationName,#organizationId", true);
		//所属部门——可用，并且设置默认值
		setInputDisabled("#deptName,#deptId,#roleTypeName,#roleTypeId", false);
		if("${dto.userType}"=="3"){
			$("#organizationName").attr("value", "${dto.sysOrganization.organizationName}" );
			$("#organizationId").attr("value","${dto.sysOrganization.organizationId}" );
		
			$("#deptName").attr("value","${dto.sysDept.deptname}");
			$("#deptId").attr("value","${dto.sysDept.deptId}");
			$("#roleTypeName").attr("value","${jslxMap[dto.roleType]}");
			$("#roleTypeId").attr("value","${dto.roleType}");
		}
		$("#organizationId").val(455);
		$("#organizationName").val('广州市发展和改革委员会');
	}else{
		//所属单位——可用
		setInputDisabled("#organizationName,#organizationId", false);
		$("#organizationName,#organizationId").attr("value","");
		//所属部门——不可用
		setInputDisabled("#deptName,#deptId,#roleTypeName,#roleTypeId", true);
		if("${dto.sysOrganization}"!=null && "${dto.sysOrganization.workunitstype}"==userType){
			$("#organizationName").attr("value", "${dto.sysOrganization.organizationName}" );
			$("#organizationId").attr("value","${dto.sysOrganization.organizationId}" );
		}
		var datas=orgDatas2;
		if(userType==1){
			datas = orgDatas1;
		}
		else if(userType==2){
			datas = orgDatas2;
		}
		else if(userType==4){
			datas = orgDatas4;
		}
		inputAutoComplete(datas, "organizationName", "organizationId", true);
    	/**$("#organizationName").focus(function(){
			initZtree("organization", userType==1?orgDatas1:orgDatas2, 210, 200);
		});*/
    
	}
	<%
		if(user.getUserType()==null||user.getUserType()!=IConstants.USER_TYPE_5){
	 %>
	 		$("#organizationName").attr("disabled",true);
	 <%
		}
	 %>
}	

	
	</script>
	<script src="../resources/js/jquery-validation/jquery.validate.expand.js" type="text/javascript"></script>
	<script src="../resources/js/jquery/jquery.form.js" type="text/javascript"></script>
	<script src="../resources/js/eps/ztreePublic.js" type="text/javascript"></script>
	<script src="../resources/js/sys/sysUserNewOrEdit.js"  type="text/javascript"></script>
	<script src="../resources/js/sys/public.js" type="text/javascript"></script>
	<script src="../resources/js/jquery-validation/jquery.validate.expand.js" type="text/javascript"></script>
	<script src="../resources/js/sys/sys.js" type="text/javascript"></script>
	<!-- 工程名 -->
	<form id="form1" method="post" action="${ctx}/sys/sysUserAdd.ac">
		<s:token/>
	<input type="hidden" value="<%=request.getContextPath() %>" id="cxt"/>
		<table style="width:100%">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle">
					用户信息操作
				</td>
				<td class="tbHeadBg" style="width: 5%;"><div class="tbHeadMid"></div></td>
				<td class="tbHeadBg" style="width: 79%;">
					<div>
						<span style="float: right;">
						<input type="submit" class="btn" value="保存"  id="saveBtn"/>
						<input type="reset" class="btn" id="resetBtn" value="重置" />  
					    <!--input type="button" class="btn" value="返回"  onclick="backQuePage('./sysUserQuery.ac')"-->
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
			     		<%
			     			if(user.getUserType()!=null&&user.getUserType()==IConstants.USER_TYPE_5){
			     		%>
					     		<s:if test="#attr.dto.userType==1">
									<input type="radio" name="userType" value="1" class="radio" checked="checked">业主用户
									<input type="radio" name="userType" value="2" class="radio">主管单位用户
									<input type="radio" name="userType" value="3" class="radio">市发改委用户
									<input type="radio" name="userType" value="4" class="radio">区县发展和改革局用户
									<span id="gly">
										<input type="radio" name="userType" value="5" class="radio">管理员用户
									</span>
								</s:if>
								<s:elseif test="#attr.dto.userType==2">
									<input type="radio" name="userType" value="1" class="radio">业主用户
									<input type="radio" name="userType" value="2" class="radio" checked="checked">主管单位用户
									<input type="radio" name="userType" value="3" class="radio">市发改委用户
									<input type="radio" name="userType" value="4" class="radio">区县发展和改革局用户
									<span id="gly">
										<input type="radio" name="userType" value="5" class="radio">管理员用户
									</span>
								</s:elseif>
								<s:elseif test="#attr.dto.userType==3">
									<input type="radio" name="userType" value="1" class="radio">业主用户
									<input type="radio" name="userType" value="2" class="radio">主管单位用户
									<input type="radio" name="userType" value="3" class="radio" checked="checked">市发改委用户
									<input type="radio" name="userType" value="4" class="radio">区县发展和改革局用户
									<span id="gly">
										<input type="radio" name="userType" value="5" class="radio">管理员用户
									</span>
								</s:elseif>
								<s:elseif test="#attr.dto.userType==4">
									<input type="radio" name="userType" value="1" class="radio">业主用户
									<input type="radio" name="userType" value="2" class="radio">主管单位用户
									<input type="radio" name="userType" value="3" class="radio">市发改委用户
									<input type="radio" name="userType" value="4" class="radio" checked="checked">区县发展和改革局用户
									<span id="gly">
										<input type="radio" name="userType" value="5" class="radio">管理员用户
									</span>
								</s:elseif>
								<s:elseif test="#attr.dto.userType==5">
									<span id="other">
										<input type="radio" name="userType" value="1" class="radio">业主用户
										<input type="radio" name="userType" value="2" class="radio">主管单位用户
										<input type="radio" name="userType" value="3" class="radio">市发改委用户
										<input type="radio" name="userType" value="4" class="radio">区县发展和改革局用户
									</span>
									<input type="radio" name="userType" value="5" class="radio" checked="checked">管理员用户
								</s:elseif>
								<s:else>
									<input type="radio" name="userType" value="1" class="radio">业主用户
									<input type="radio" name="userType" value="2" class="radio">主管单位用户
									<input type="radio" name="userType" value="3" class="radio" checked="checked">市发改委用户
									<input type="radio" name="userType" value="4" class="radio">区县发展和改革局用户
									<span id="gly">
										<input type="radio" name="userType" value="5" class="radio">管理员用户
									</span>
								</s:else>
					    <%
			     			}
			     			else{
			     		%>
			     				<span onmousemove="this.setCapture();" onmouseout="this.releaseCapture();" onfocus="this.blur();">
				     			<s:if test="#attr.dto.userType==1">
									<input type="radio" name="userType" value="1" class="radio" checked="checked">业主用户
									<input type="radio" name="userType" value="2" class="radio">主管单位用户
									<input type="radio" name="userType" value="3" class="radio">市发改委用户
									<input type="radio" name="userType" value="4" class="radio">区县发展和改革局用户
								</s:if>
								<s:elseif test="#attr.dto.userType==2">
									<input type="radio" name="userType" value="1" class="radio">业主用户
									<input type="radio" name="userType" value="2" class="radio" checked="checked">主管单位用户
									<input type="radio" name="userType" value="3" class="radio">市发改委用户
									<input type="radio" name="userType" value="4" class="radio">区县发展和改革局用户
								</s:elseif>
								<s:elseif test="#attr.dto.userType==3">
									<input type="radio" name="userType" value="1" class="radio">业主用户
									<input type="radio" name="userType" value="2" class="radio">主管单位用户
									<input type="radio" name="userType" value="3" class="radio" checked="checked">市发改委用户
									<input type="radio" name="userType" value="4" class="radio">区县发展和改革局用户
								</s:elseif>
								<s:elseif test="#attr.dto.userType==4">
									<input type="radio" name="userType" value="1" class="radio">业主用户
									<input type="radio" name="userType" value="2" class="radio">主管单位用户
									<input type="radio" name="userType" value="3" class="radio">市发改委用户
									<input type="radio" name="userType" value="4" class="radio" checked="checked">区县发展和改革局用户
								</s:elseif>
								<s:else>
									<input type="radio" name="userType" value="1" class="radio">业主用户
									<input type="radio" name="userType" value="2" class="radio">主管单位用户
									<input type="radio" name="userType" value="3" class="radio" checked="checked">市发改委用户
									<input type="radio" name="userType" value="4" class="radio">区县发展和改革局用户
								</s:else>
								</span>
			     		<%
			     			}
					    %>
			     	</td>
				</tr>
		<tr>
			<th>所属单位<font class="msg">*</font></th>
			<td>
		          <%
	     			if(user.getUserType()==null||user.getUserType()!=IConstants.USER_TYPE_5){
	     		 %>
	     		 		<span onmousemove="this.setCapture();" onmouseout="this.releaseCapture();" onfocus="this.blur();">
						<input type="text" name="dto.sysOrganization.organizationName" id="organizationName" value="<s:property value="#attr.dto.sysOrganization.organizationName"/>"/> 
	     		 		</span>
	     		 <%
	     			}
	     			else{
	     		%>
	     				<input type="text" name="dto.sysOrganization.organizationName" id="organizationName" value="<s:property value="#attr.dto.sysOrganization.organizationName"/>"/>
	     		<%	
	     			}
	     		 %>
		        <input type="hidden" name="dto.sysOrganization.organizationId" id="organizationId"  
		        value="<s:property value="#attr.dto.sysOrganization.organizationId"/>"/>
			</td>
		</tr>
		<tr>
			<th>角色类型</th>	
	         <td >
		          <%
	     			if(user.getUserType()==null||user.getUserType()!=IConstants.USER_TYPE_5){
	     		 %>
	     		 		<span onmousemove="this.setCapture();" onmouseout="this.releaseCapture();" onfocus="this.blur();">
						<input type="text" name="roleTypeName" id="roleTypeName" value="<s:property value="jslxMap[dto.roleType]"/>"/> 
	     		 		</span>
	     		 <%
	     			}
	     			else{
	     		%>
	     				<input type="text" name="roleTypeName" id="roleTypeName" value="<s:property value="jslxMap[dto.roleType]"/>"/>
	     		<%	
	     			}
	     		 %>
		        <input type="hidden" name="dto.roleType" id="roleTypeId"  
		        value="<s:property value="dto.roleType"/>"/>
	      	</td>
		</tr>
		<tr>
			<th>所属部门</th>	
	         <td >
		          <%
	     			if(user.getUserType()==null||user.getUserType()!=IConstants.USER_TYPE_5){
	     		 %>
	     		 		<span onmousemove="this.setCapture();" onmouseout="this.releaseCapture();" onfocus="this.blur();">
						<input type="text" name="dto.sysDept.deptname" id="deptName" value="<s:property value="#attr.dto.sysDept.deptname"/>"/> 
	     		 		</span>
	     		 <%
	     			}
	     			else{
	     		%>
	     				<input type="text" name="dto.sysDept.deptname" id="deptName" value="<s:property value="#attr.dto.sysDept.deptname"/>"/>
	     		<%	
	     			}
	     		 %>
		        <input type="hidden" name="dto.sysDept.deptId" id="deptId"  
		        value="<s:property value="#attr.dto.sysDept.deptId"/>"/>
	      	</td>
		</tr>
		<tr>
				<th >所属职务</th>	
			        <td >
				          <%
			     			if(user.getUserType()==null||user.getUserType()!=IConstants.USER_TYPE_5){
			     		 %>
			     		 		<span onmousemove="this.setCapture();" onmouseout="this.releaseCapture();" onfocus="this.blur();">
								<input type="text" name="dto.sysDuty.dutyName" id="dutyName" value="<s:property value="#attr.dto.sysDuty.dutyName"/>"/> 
			     		 		</span>
			     		 <%
			     			}
			     			else{
			     		%>
			     				<input type="text" name="dto.sysDuty.dutyName" id="dutyName" value="<s:property value="#attr.dto.sysDuty.dutyName"/>"/>
			     		<%	
			     			}
			     		 %>
				        <input type="hidden" name="dto.sysDuty.dutyId" id="dutyId"  
				        value="<s:property value="#attr.dto.sysDuty.dutyId"/>"/>
			      	</td>
				</tr>
				<tr>
					<th >真实姓名<font class="msg">*</font></th>
					<td>
						<input type="text" name="dto.userName" id="userName"  
						value="<s:property value="#attr.dto.userName"/>"   class="text">
					</td>
				</tr>
				<tr>
					<th >性别<font class="msg">*</font></th>
			     	<td>
			     		<s:if test="#attr.dto.sex==1">
							<input type="radio" name="dto.sex" value="1" class="radio" checked="checked">男
							<input type="radio" name="dto.sex" value="0" class="radio" >女
						</s:if>
						<s:else>
							<input type="radio" name="dto.sex" value="1"  class="radio" >男
							<input type="radio" name="dto.sex" value="0"  class="radio" checked="checked">女
						</s:else>
			     	</td>
				</tr>
				<tr>
					<th >登录帐号<font class="msg">*</font></th>
					<td>
						<input type="text" name="dto.loginName" id="loginName"  
							value="<s:property value="#attr.dto.loginName"/>" >
					</td>
				</tr>
				<s:if test="#attr.dto.userId==null">
					<tr>
					<th >登录密码<font class="msg">*</font></th>
					<td>
						<input type="password" name="dto.loginPwd" id="loginPwd"  
							value="<s:property value="#attr.dto.loginPwd"/>"   class="text">
					</td>
				</tr>
				<tr>
					<th >确认密码<font class="msg">*</font></th>
					<td>
						<input type="password" name="confirmPwd"  id="confirmPwd"  class="text">
					</td>
				</tr>
				</s:if>
				
				<%-- tr>
					<th >用户状态<font class="msg">*</font></th>
					<td>
						<s:if test="#attr.dto.useStatus==2">
							<input type="radio" name="useStatus" value="0" class="radio" > 未审核
							<input type="radio" name="useStatus" value="1" class="radio"> 审核未通过
							<input type="radio" name="useStatus" value="2" class="radio" checked="checked"> 正常
						</s:if>
						<s:elseif test="#attr.dto.useStatus==1">
							<input type="radio" name="useStatus" value="0" class="radio" > 未审核
							<input type="radio" name="useStatus" value="1" class="radio" checked="checked"> 审核未通过
							<input type="radio" name="useStatus" value="2" class="radio"> 正常
						</s:elseif>
						<s:elseif test="#attr.dto.useStatus==0">
							<input type="radio" name="useStatus" value="0" class="radio" checked="checked"> 未审核
							<input type="radio" name="useStatus" value="1" class="radio"> 审核未通过
							<input type="radio" name="useStatus" value="2" class="radio"> 正常
						</s:elseif>
						<s:else>
							<input type="radio" name="useStatus" value="0" class="radio" > 未审核
							<input type="radio" name="useStatus" value="1" class="radio"> 审核未通过
							<input type="radio" name="useStatus" value="2" class="radio" checked="checked"> 正常
						</s:else>
					</td>
				</tr>
				<<tr>
					<th >起始有效日期<font class="msg">*</font></th>
					<td><input type="text" name="validFromDate" id="validFromDate"  value="<s:date name="#attr.dto.validFromDate" format="yyyy-MM-dd" />"  onfocus="WdatePicker({maxDate:'#F{$dp.$D(\'validToDate\')}'})"class="Wdate"  readonly="readonly" ></td>
				</tr>
				<tr>
					<th >截止有效日期<font class="msg">*</font></th>
					<td><input type="text" name="validToDate" id="validToDate"  value="<s:date name="#attr.dto.validToDate" format="yyyy-MM-dd" />"  onfocus="WdatePicker({minDate:'#F{$dp.$D(\'validFromDate\')}'})" class="Wdate"  readonly="readonly" ></td>
				</tr>
				<tr>
					<th >所属职位</th>
					<td><input type="text" name="dto.userduty" id="userduty"  value="<s:property value="#attr.dto.userduty"/>"   class="text"></td>
				</tr> --%>
				<tr>
					<th >电话</th>
					<td>
						<input type="text" name="dto.tel" id="tel"  value="<s:property value="#attr.dto.tel"/>"   class="text">
					</td>
				</tr>
				<tr>
					<th >手机号<font class="msg">*</font></th>
					<td>
						<input type="text" name="dto.telmobile" id="telmobile"   value="<s:property value="#attr.dto.telmobile"/>"  class="text">
					</td>
				</tr>
				<tr>
					<th >传真</th>
					<td><input type="text" name="dto.fax" id="fax"  value="<s:property value="#attr.dto.fax"/>"   class="text"></td>
				</tr>
				<tr>
					<th >电子邮件地址</th>
					<td><input type="text" name="dto.email" id="email"  value="<s:property value="#attr.dto.email"/>"   class="text"></td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>
