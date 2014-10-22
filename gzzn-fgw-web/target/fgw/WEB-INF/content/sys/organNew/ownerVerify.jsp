<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>注册业主信息审核</title>
</head>
<body>
<script type="text/javascript">
//<!--
	   $(document).ready(function() {
	   		showMenu(6,64);
   			//返回
			$("#backBtn").click(function() {
				window.location = "${ctx}/sys/organNew/listOwnerVerify.ac";
			});
		});
	
	function ownerVerify(verityStatus){
		$("#verityStatus").val(verityStatus);
		$("#editForm").submit();
	}
	
	//-->
</script>

	<form id="editForm" action="${ctx}/sys/organNew/verifyProcess.ac"  method="post">
		<input type="hidden" name="verityStatus" id="verityStatus" value=""/>
		<input type="hidden" name="obj.organizationId" id="organizationId" value="${obj.organizationId}"/>
		<table style="width:100%">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle">审核业主单位信息</td>
				<td class="tbHeadBg" style="width: 5%;"><div class="tbHeadMid"></div></td>
				<td class="tbHeadBg" style="width: 79%;"></td>
				<td class="tbHeadBg" style="width: 3.5%;">
					<div class="tbHeadRight"></div>
				</td>
			</tr>
		</table>
		<div class="editDiv">
			<table class="editTab" id="editTab">
				<tr>
					<th>业主单位名称<font class="msg">*</font></th>
					<td>${obj.organizationName}</td>
				</tr>
				<tr>
					<th>单位性质</th>
					<td>${dwxzMap[obj.workunitsquality]}</td>
				</tr>
				<tr>
					<th>所在区域</th>
					<td>${obj.sysXq.xqmc}</td>
				</tr>
				<tr>
					<th>企业工商注册号(机构代码)<font class="msg">*</font></th>
					<td>${obj.workunitsregistercode}</td>
				</tr>
				<tr>
					<th>单位法人<font class="msg">*</font></th>
					<td>${obj.workunitsperson}</td>
				</tr>
				<tr>
					<th>单位联系人<font class="msg">*</font></th>
					<td>${obj.workunitslinkman}</td>
				</tr>
				<tr>
					<th>联系人电话<font class="msg">*</font></th>
					<td>${obj.workunitslinkmantel}</td>
				</tr>
				<tr>
					<th>单位地址<font class="msg">*</font></th>
					<td>${obj.workunitsaddress}</td>
				</tr>
			</table>
		</div>
		<hr/>
		<table style="width:100%">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle">审核业主个人信息</td>
				<td class="tbHeadBg" style="width: 5%;"><div class="tbHeadMid"></div></td>
				<td class="tbHeadBg" style="width: 79%;"></td>
				<td class="tbHeadBg" style="width: 3.5%;">
					<div class="tbHeadRight"></div>
				</td>
			</tr>
		</table>
		<input type="hidden" name="ownerUser.userId" id="userId" value="${ownerUser.userId}"/>
		<div class="editDiv">
			<table class="editTab" id="editTab2">
				<tr>
					<th>用户名：<font class="msg">*</font></th>
					<td>${ownerUser.loginName}</td>
				</tr>
				<tr>
					<th>真实姓名：<font class="msg">*</font></th>
					<td>${ownerUser.userName}</td>
				</tr>
				<tr>
					<th >性别<font class="msg">*</font></th>
			     	<td>
			     		<c:if test="${ownerUser.sex eq 1}" var="result">男</c:if>
			     		<c:if test="${!result}">女</c:if>
			     	</td>
			    </tr>
				<tr>
					<th>手机号码：<font class="msg">*</font></th>
					<td>${ownerUser.telmobile}</td>
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