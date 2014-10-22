<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<s:set var="ctx">${pageContext.request.contextPath}</s:set>
<%
    String userId = request.getParameter("userId");
    if (userId != null) {
        session.setAttribute("user_id", userId);
    }
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>1</title>
	<script src="${ctx}/resources/js/jquery/jquery-1.8.3.js" type="text/javascript"></script>
<script type="text/javascript">
	$(function(){
			$.post("${ctx}/login/slogin.json",function(data){
				var json = $.parseJSON(data);
				if(json.flag){
					//window.location.href="${ctx}/eds/info/main.ac";
					window.location.href="${ctx}/index/main.ac";
				}else{
					$("#ActionMessage").removeClass("hide").html(json.msg);
					resetTxtInput();
				}
			});
  	});
</script>

</head>

<body>
	<input type="hidden" id="surl" value="${param.surl }"/>
</body>
</html>
