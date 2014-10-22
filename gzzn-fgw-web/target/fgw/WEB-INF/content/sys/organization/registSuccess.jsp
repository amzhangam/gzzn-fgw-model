<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">
<title>业主注册成功提示</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
	<script type="text/javascript">
//<!--
	   $(document).ready(function() {
	   		showMenu(6,64);
   			//返回
			$("#backBtn").click(function() {
				window.location = "${ctx}/index.jsp";
			});
			
		});
	
	
	//-->
</script>
</head>
<body>
	<form id="editForm" action="./saveOwner.ac"  method="post">
		<table style="width: 98%;margin: 0 auto;margin-top: 3px;">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle1" style="width: 99%;">
					<font color="green">您的业主信息注册成功，请耐心等待管理员审核！</font>
					以下重要信息只显示一次，建议你记录下来
				</td>
			</tr>
		</table>
		<div class="list" style="width: 97.7%;margin: 0 auto;text-align: center;padding: 15px 0;">
			<div style="color:red;height: 35px;">使用此用户名可登录广州市政府投资项目库申报信息系统</div>
			<div><input type="button" class="btn" value="返回登录界面"  id="backBtn"/></div>
		</div>
	</form>
</body>
</html>