<%@page import="com.gzzn.fgw.webUtil.CommonFiled"%>
<%@page import="com.gzzn.fgw.model.SysUser"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<html>
<head>
<title>用户信息管理</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style type="text/css">
	[action]{
		cursor:default; color:black;
	}
	[action]:hover{
		color:black;
	}
</style>
<script src="../resources/js/eps/ztreePublic.js" type="text/javascript"></script>
<script src="../resources/js/sys/public.js" type="text/javascript"></script>
<script src="../resources/js/sys/sysUserQuery.js" type="text/javascript"></script>
</head>
<%
	SysUser user = (SysUser)request.getSession().getAttribute(CommonFiled.SESSION_LOGIN_USER);
%>
<body>
		<!-- 加载相关的查询条件 -->
		<div class="topSearchDiv" style="width: 100%;">
		  <input type="hidden" value="<%=request.getContextPath() %>" id="cxt"/>
		  <form action="./sysUserQuery.ac" method="post">
			  <input type="hidden" id="currentPage" name="page.current" value="<s:property value="#attr.page.current"/>" />
			  <input type="hidden" id="pageSize" name="page.size" value="<s:property value="#attr.page.size"/>" />
			  <table class="topSearchTab">
				<tr>
					<th>真实姓名：</th>
					<td><input type="text" name="userName" id="userName" value="<s:property value="#attr.dto.userName"/>" class="text"> </td>
					<th>登录帐号：</th>
					<td><input type="text" name="loginName" id="loginName" value="<s:property value="#attr.dto.loginName"/>" class="text"> </td>
					<th>手机：</th>
					<td><input type="text" name="telmobile" id="telmobile" value="<s:property value="#attr.dto.telmobile"/>" class="text"> </td>
					<th>用户状态：</th>
					<td>
						<input type="text" name="useStatusName" id="yhztName"
								          value="<s:property value="yhztMap[#attr.useStatus]"/>" readonly="readonly" class="text"/>
						<input type="hidden" name="useStatus" id="yhztId" value="<s:property value="#attr.useStatus"/>"/>
					</td>
				</tr>
				<tr>
					<th>用户类型：</th>
					<td>
						<input type="text" name="userTypeName" id="yhlxName"
								          value="<s:property value="yhlxMap[#attr.userType]"/>" readonly="readonly" class="text"/>
						<input type="hidden" name="userType" id="yhlxId" value="<s:property value="#attr.userType"/>"/>
					</td>
					<th>所属单位：</th>
					<td>
						<input type="text" name="sysOrganization.organizationName" id="organizationName"
								          value="<s:property value="#attr.dto.sysOrganization.organizationName"/>" class="text"/><!--  readonly="readonly" -->
						<input type="hidden" name="sysOrganization.organizationId" id="organizationId" value="<s:property value="#attr.dto.sysOrganization.organizationId"/>"/>
					</td>
					<th>所属部门：</th>
					<td>
						<input type="text" name="sysDept.deptname" id="deptName"
								          value="<s:property value="#attr.dto.sysDept.deptname"/>" readonly="readonly" class="text"/>
						<input type="hidden" name="sysDept.deptId" id="deptId" value="<s:property value="#attr.dto.sysDept.deptId"/>"/>
					</td>
					<th colspan="2" style="text-align: center;">
						<c:if test="${objectMap['XMSB_XT_YHGL_VIEW']}">
						<input type="button" class="btn" value="查询"  id="queryBtn" />&nbsp;&nbsp;
						<input type="button" class="btn" value="清空"  id="clearCon" />
						</c:if>
					</th>
					<%-- <th>所属职务：</th>
					<td>
						<input type="text" name="sysDuty.dutyName" id="dutyName"
								          value="<s:property value="#attr.dto.sysDuty.dutyName"/>" readonly="readonly" class="text"/>
						<input type="hidden" name="sysDuty.dutyId" id="dutyId" value="<s:property value="#attr.dto.sysDuty.dutyId"/>"/>
					</td>
					
					<th>角色类型：</th>
					<td>
						<input type="text" name="roleTypeName" id="jslxName"
								          value="<s:property value="jslxMap[#attr.roleType]"/>" readonly="readonly" class="text"/>
						<input type="hidden" name="roleType" id="jslxId"  value="<s:property value="#attr.roleType"/>"/>
					</td> --%>
				</tr>
			  </table>
		  </form>
		</div>
		<!-- 相关数据展示 -->	
		<table style="width: 100%;margin: 0 auto;">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle" style="width:128px;">用户信息管理</td>
				<td class="tbHeadBg" style="width: 5%;"><div class="tbHeadMid"></div></td>
				<td class="tbHeadBg">
					 <div>
					    <span style="float: right;">     
					    	<c:if test="${objectMap['XMSB_XT_YHGL_ADD']}">
			            	<input type="button" class="btn add1"  onclick="editOrAddUrl('')" value="新增"> 
			            	</c:if>
			            	<c:if test="${objectMap['XMSB_XT_YHGL_DEL']}">
							<input type="button" class="btn" onClick="del('')" value="批量删除">
							</c:if>
							<input type="button" class="btn back1" onClick="back()" value="返回" style="display:none;"> 
		               	</span>      
		             </div>
				</td>
				<td class="tbHeadBg" style="width: 3.5%;">
					<div class="tbHeadRight"></div>
				</td>
			</tr>
		</table>
		<table class="list" style="width: 100%;margin: 0 auto;">
			<thead>
				<tr>
					<td width="3%"><input type="checkbox" id="checkAll"></td>
					<td width="5%">序号</td>
					<td width="10%">真实姓名</td>
					<!-- <td>性别</td> -->
					<td width="10%">登录帐号</td>
					<td width="16%">所属单位</td>
					<td width="16%">所属部门</td>
					<td width="8%">手机</td>
					<td width="8%">用户类型</td>
					<td width="8%">用户状态</td>
					<!-- 
					<td>角色类型</td>
					<td>创建时间</td>
					<td>所属职务</td> -->
					<td width="16%">操作</td>
				</tr>
			</thead>
			<tbody>
				<s:iterator value="#attr.page.list" status="st">
				<tr>
					<td>
						<input type="checkbox"  name="id"  value="<s:property value="userId"/>" class="subcheck"/>
					</td>
					<td><s:property value="#attr.page.size*#attr.page.current+#st.index+1"/>&nbsp;</td>
					<td><s:property value="userName"/>&nbsp;</td>
				<%-- 	<td>
						<s:if test="sex==1">男</s:if>
						<s:else>女</s:else>&nbsp;
					</td> --%>
					<td><s:property value="loginName"/>&nbsp;</td>
					<td><s:property value="sysOrganization.organizationName"/>&nbsp;</td>
					<td><s:property value="sysDept.deptname"/>&nbsp;</td>
					<td><s:property value="telmobile"/>&nbsp;</td>
					<td>
						<s:if test="userType==1">业主用户</s:if>
						<s:elseif test="userType==2">主管单位用户</s:elseif>
						<s:elseif test="userType==3">发改委用户</s:elseif>
						<s:elseif test="userType==4">区县发展和改革局用户</s:elseif>
						<s:elseif test="userType==5">系统管理员</s:elseif>
					</td>
					<td>					
						<s:if test="useStatus==1">待审批</s:if>
						<s:elseif test="useStatus==2">审批通过</s:elseif>
						<s:elseif test="useStatus==3">审批不通过</s:elseif>
						<s:elseif test="useStatus==4">销户</s:elseif>
					</td>
					
					<%-- <td>
						<s:if test="RoleType==1">处长</s:if>
						<s:elseif test="RoleType==2">副处长</s:elseif>
						<s:elseif test="RoleType==3">经办</s:elseif>
					</td>
					<td><s:date name="createTime" format="yyyy-MM-dd HH:mm"/>&nbsp;</td>
					<td><s:property value="sysDuty.dutyName"/>&nbsp;</td> --%>
					<td>
						<c:if test="${objectMap['XMSB_XT_YHGL_ROLE']}">
						<a href="javascript:void(0)"  onclick="grentRole(<s:property value="userId"/>)">${useStatus==2?'角色分配|':''}</a> 
						</c:if>
						<c:if test="${objectMap['XMSB_XT_YHGL_PWD']}">
						<a href="javascript:void(0)"  onclick="modifyPass(<s:property value="userId"/>,'<%=user.getUserType()%>')">修改密码</a> |
						</c:if>
						<c:if test="${objectMap['XMSB_XT_YHGL_MOD']}">
						<a href="javascript:void(0)"  onclick="editOrAddUrl(<s:property value="userId"/>)">编辑</a> |
						</c:if>
						<c:if test="${objectMap['XMSB_XT_YHGL_DEL']}">
							<!-- 如果等于当前账户，那么自己不能删除自己 -->
							<s:if test="#attr.conUserId==userId">
								删除
							</s:if>
							<s:else>
								<a href="javascript:void(0)"  onclick="del(<s:property value="userId"/>)">删除</a>
							</s:else>
						</c:if>
					</td>
					</tr>
				</s:iterator>
			<tr>
				<td colspan="10" class="line2" style="text-align: right;">
					<%@include file="../changePage.jsp" %>
				</td>
			</tr>
			</tbody>
	 </table>
</body>
</html>