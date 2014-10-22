<%@page import="java.util.Map"%>
<%@page import="com.gzzn.fgw.webUtil.PropertiesUtil"%>
<%@page import="com.gzzn.fgw.webUtil.CommonFiled"%>
<%@page import="com.gzzn.fgw.model.*"%>
<%@page import="com.gzzn.fgw.util.IConstants"%>
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
填写用户意见
</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/2_event.css">
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/edittable.css">
<script type="text/javascript" src="${ctx}/resources/js/tc.all.js"></script>
<script src="${ctx}/resources/js/jquery-validation/jquery.validate.expand.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/jquery/jquery.form.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/eps/ztreePublic.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/sys/public.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/jquery-validation/jquery.validate.expand.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/project/dclxm/mind.js" type="text/javascript"></script>
<script type="text/javascript">
//<!--
$(document).ready(function() {
	
	$("#yjmb").change(function(){
		if($("#yjmb").val()!=""){
			$.post("${ctx}/project/dclxm/getMind.json",{"yjmbId":$("#yjmb").val()},function(data){
				var json = $.parseJSON(data);
				$("#mind").val(json.msg);
			});
		}
	});
});


//-->
function toxmps(projectid){
		var mind = $("#mind").val();
		if(mind==null||mind==''){
			alert("请输入用户意见");
			return;
		}
		$.post("${ctx}/project/dclxm/toxmps.json",{"id":projectid,"mind":mind},function(data){
			var json = $.parseJSON(data);
			if(json.flag){
				alert(json.msg);
				window.location = "${ctx}/project/dclxm/list.ac";
			}else{
				alert(json.msg);
			}
		});
}
</script>
</head>
<body>
	<form action="${ctx}/project/dclxm/toxmps.ac" method="post"  enctype="multipart/form-data">
		<table style="width:100%" bgcolor="#EDF6FF" align="center"  border="0" cellpadding="0" cellspacing="10">
			<tr>
				<td>
				      <table style="width:500" bgcolor="#EDF6FF" align="center"  border="0" cellpadding="0" cellspacing="0">
				      	<tr><td>&nbsp;</td></tr>
						<tr>
							<td align="right">
							       用户意见：
							</td>
							<td>
								请选择意见模板：
									<select name="yjmb" id="yjmb">
										<option value="">==请选择==</option>
										<c:forEach items="${list}" var="obj" varStatus="status">
											<option value="${obj.yjmbId}">${obj.mbmc}</option>
										</c:forEach>
									</select>
									<br/>
									<br/>
								   <textarea id="mind" name="mind" rows="5" cols="80"></textarea>
							</td>
						</tr>
						<tr><td>&nbsp;</td></tr>
						<tr>
							<td align="center" colspan="2">
								<input type="button" class="btn" id="queryBtn" value="确定" onclick="toxmps(${id})"/>&nbsp;
								<input type="button" class="btn" id="backBtn" value="返回">
							</td>
						</tr>
					</table>	
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
