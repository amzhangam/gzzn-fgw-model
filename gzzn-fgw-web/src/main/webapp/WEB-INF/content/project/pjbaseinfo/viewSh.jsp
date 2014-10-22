<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>项目管理</title>
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
					<td width="2%">序号</td>
					<td width="8%">审核单位与部门</td>
					<td width="8%">处理环节</td>
					<td width="8%">处理结果</td>
					<td width="8%">处理意见</td>
					<td width="8%">处理人</td>
					<td width="6%">处理时间</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${pjauditlogs}" var="obj" varStatus="status">
				<tr>
					<td>${status.index+1}&nbsp;</td>
					<td>${obj.sysDeptBySenddeptid!=null?obj.sysDeptBySenddeptid.deptname:(obj.sysOrganizationByPjauditunits!=null?obj.sysOrganizationByPjauditunits.organizationName:"")}&nbsp;</td>
					<td><s:property value="pjaudittypeMap[#attr.obj.pjaudittype]"/>&nbsp;</td>
					<td>
						<c:choose>
							<c:when test="${obj.pjauditresult==1}">
								通过
							</c:when>
							<c:when test="${obj.pjauditresult==2}">
								审核不通过
							</c:when>
							<c:when test="${obj.pjauditresult==3}">
								转交其他处室
							</c:when>
							<c:otherwise>
								
							</c:otherwise>
						</c:choose>
					</td>
					<td>${obj.pjauditmind}&nbsp;</td>
					<td>${obj.recordername}&nbsp;</td>
					<td><fmt:formatDate value='${obj.recordertime}' pattern='yyyy-MM-dd HH:mm'/></td>
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
