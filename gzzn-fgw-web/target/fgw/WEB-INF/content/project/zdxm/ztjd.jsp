<%@page import="com.gzzn.fgw.util.IConstants"%>
<%@page import="com.gzzn.fgw.webUtil.CommonFiled"%>
<%@page import="com.gzzn.fgw.model.SysUser"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>
总体进度查看
</title>
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
<script type="text/javascript">
//<!--
$(document).ready(function() {
		<%
			SysUser user = (SysUser)request.getSession().getAttribute(CommonFiled.SESSION_LOGIN_USER);
		%>
});
//-->
</script>
</head>
<body>

	<script type="text/javascript">
	
	
	</script>
	<form action="${ctx}/project/pjbaseinfo/save.ac" method="post"  enctype="multipart/form-data" id="editForm" >
		
			<fieldset style="width:80%;height:100%;">
				<legend>项目总体进度</legend>
			<table class="editTab2" id="tzTab">
				<tr bgcolor="#C7E7FF">
					<td align="center">序号</td>
					<td align="center">项目名称</td>	
			        <td align="center">申报日期</td>
			        <td align="center" colspan="6">项目进度 </td>
				</tr>
				<c:forEach items="${pjbaseinfos}" var="obj" varStatus="status">
					<tr>
						<td>${status.index+1}</td>
						<td>
							${obj.projectname}
						</td>
						<td>
							<fmt:formatDate value='${obj.declartime}' pattern='yyyy-MM-dd'/>
						</td>
						<c:if test="${obj.xmjd==null}">
							<td>
								<font color="#D0D4E1">项目建议书</font>
							</td>
							<td>
								<font color="#D0D4E1">可行性研究报告</font>
							</td>
							<td>
								<font color="#D0D4E1">初步设计与概算评审</font>
							</td>
							<td>
								<font color="#D0D4E1">核发施工许可</font>
							</td>
							<td>
								<font color="#D0D4E1">施工</font>
							</td>
							<td>
								<font color="#D0D4E1">竣工验收</font>
							</td>
						</c:if>
						<c:if test="${obj.xmjd!=null}">
							<td>
								<font color="${obj.xmjd>=1?'green':'#696969'}">项目建议书</font>
							</td>
							<td>
								<font color="${obj.xmjd>=2?'green':'#696969'}">可行性研究报告</font>
							</td>
							<td>
								<font color="${obj.xmjd>=3?'green':'#696969'}">初步设计与概算评审</font>
							</td>
							<td>
								<font color="${obj.xmjd>=4?'green':'#696969'}">核发施工许可</font>
							</td>
							<td>
								<font color="${obj.xmjd>=5?'green':'#696969'}">施工</font>
							</td>
							<td>
								<font color="${obj.xmjd>=6?'green':'#696969'}">竣工验收</font>
							</td>
						</c:if>
						
					</tr>
				</c:forEach>
			</table>
			</fieldset>
			
		<table  id="submitTab" style="width: 80%;">
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