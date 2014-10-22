<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>项目日志</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/2_event.css">
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/edittable.css">
<link href="${ctx}/resources/css/style.css" type="text/css" rel="stylesheet" />

<script src="${ctx}/resources/js/common.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/event.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/jquery/jquery-1.8.3.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/My97DatePicker/WdatePicker.js" type="text/javascript" ></script>
<script src="${ctx}/resources/js/jquery-ztree/jquery.ztree.all-3.5.min.js" type="text/javascript" ></script>
<script src="${ctx}/resources/js/jquery-dialog_2.4/cn.js" type="text/javascript" ></script>
<script src="${ctx}/resources/js/jquery-dialog_2.4/core.js" type="text/javascript" ></script>
<script src="${ctx}/resources/js/jquery-dialog_2.4/global.js" type="text/javascript" ></script>
<script src="${ctx}/resources/js/jquery-dialog_2.4/dialog.js" type="text/javascript" ></script>
<script src="${ctx}/resources/js/jquery-dialog_2.4/mousewheel.js" type="text/javascript" ></script>
<script src="${ctx}/resources/js/jquery-grid_3.0b/pager.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/jquery-grid_3.0b/grid.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/jquery-ui/js/jquery-ui.js" type="text/javascript" ></script>
<script src="${ctx}/resources/js/jquery-autocomplete/jquery.autocomplete.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/jquery-validation/1.10.0/jquery.validate.min.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/jquery-validation/1.10.0/messages_cn.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/jquery-validation/jquery.validate.expand.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/RBAlertMSG.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/eps/ztreePublic.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/jquery.nicescroll.js" type="text/javascript"></script>
</head>
<body>
<script type="text/javascript">
<!--
	$(document).ready(function() {
		//showMenu(2,17);
	});
//-->

</script>

<form action="" method="post"  enctype="multipart/form-data">
	<br/>
	<table style="width:95%" align="center" border="0" cellpadding="1" cellspacing="0"
		bordercolor="AEC2D5" class="list">
			<thead>
			<tr>
				<td width="5%">序号</td>
				<td width="15%">项目名称</td>
				<td width="10%">用户名称</td>
				<td width="15%">单位名称</td>
				<td width="10%">部门名称</td>
				<td width="30%">操作内容</td>
				<td width="15%">操作时间</td>
			</tr>
		</thead>
		<tbody>
	  	  <c:forEach items="${sysProjectlogs}" var="obj" varStatus="status"> 
			<tr>
				<td>${status.index+1}</td>
				<td>${obj.pjbaseinfo!=null?obj.pjbaseinfo.projectname:''}</td>
				<td>${obj.sysUser!=null?obj.sysUser.userName:''}</td>
				<td>${obj.sysOrganization!=null?obj.sysOrganization.organizationName:''}</td>
				<td>${obj.sysDept!=null?obj.sysDept.deptname:''}</td>
				<td>${obj.operationContent}&nbsp;</td>
				<td><fmt:formatDate value="${obj.operationDatetime}" pattern="yyyy-MM-dd HH:mm"/></td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	<table  id="submitTab" style="width: 100%;">
		<tr><td>&nbsp;</td></tr>
		<tr>
			<td style="width: 100%;">
				<div align="center">
					 <input type="button" class="btn" id="backBtn" value="关闭" onclick="window.close()">
				</div>
			</td>
		</tr>
	</table>
</form>
</body>
</html>
