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
批量分派
</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">

<link rel="stylesheet" type="text/css" href="../../resources/css/1_event.css">
<link rel="stylesheet" type="text/css" href="../../resources/css/edittable.css">
<link href="../../resources/css/style.css" type="text/css" rel="stylesheet" />

<link href="../../resources/css/2_event.css" type="text/css" rel="stylesheet">
<link href="../../resources/js/jquery-ztree/zTreeStyle/zTreeStyle.css" type="text/css" rel="stylesheet" />
<link href="../../resources/js/jquery-validation/1.10.0/validate.css" type="text/css" rel="stylesheet" />

<script src="../../resources/js/jquery-1.7.2.js" type="text/javascript"></script>
<script src="../../resources/js/jquery.form.js" type="text/javascript"></script>
<script src="../../resources/js/jquery.validate.js" type="text/javascript"></script>
<script src="../../resources/js/common.js" type="text/javascript"></script>
<script src="../../resources/js/event.js" type="text/javascript"></script>
<script src="../../resources/js/jquery-ztree/jquery.ztree.all-3.5.min.js" type="text/javascript" ></script>
<script src="../../resources/js/jquery-dialog_2.4/cn.js" type="text/javascript" ></script>
<script src="../../resources/js/jquery-dialog_2.4/core.js" type="text/javascript" ></script>
<script src="../../resources/js/jquery-dialog_2.4/global.js" type="text/javascript" ></script>
<script src="../../resources/js/jquery-dialog_2.4/dialog.js" type="text/javascript" ></script>
<script src="../../resources/js/jquery-dialog_2.4/mousewheel.js" type="text/javascript" ></script>
<script src="../../resources/js/jquery-grid_3.0b/pager.js" type="text/javascript"></script>
<script src="../../resources/js/jquery-grid_3.0b/grid.js" type="text/javascript"></script>
<script src="../../resources/js/jquery-ui/js/jquery-ui.js" type="text/javascript" ></script>
<script src="../../resources/js/RBAlertMSG.js" type="text/javascript"></script>
<script src="../../resources/js/eps/ztreePublic.js" type="text/javascript"></script>
<script src="../../resources/js/jquery.nicescroll.js" type="text/javascript"></script>

<script src="../../resources/js/project/xmfp/assign.js" type="text/javascript"></script>
<script type="text/javascript">
//<!--
$(document).ready(function() {
		<%
			SysUser user = (SysUser)request.getSession().getAttribute(CommonFiled.SESSION_LOGIN_USER);
			String deptCode = user.getSysDept()!=null&&user.getSysDept().getFullcode()!=null?user.getSysDept().getFullcode():null;
			String fgwtzc = PropertiesUtil.getProperties("fgwproject.properties").getProperty("fgwtzc.fullcode");
		%>
});
//-->

</script>
</head>
<body>

	<script type="text/javascript">
	
	function saveProject(){
		var validator2 = $("#editForm").validate({
			 event:"submit",
			 rules: {
	             "pjauditlog.pjauditmind":{   
	            	 byteMaxLength: 200
	             }
		      },
		      messages:{
		    	  "pjauditlog.pjauditmind":{
		    		  byteMaxLength:"不能全为空格且字数最多是200"
		    	  }
		      },
		      submitHandler: function(form){
			    	form.submit();
			    }	
		});
		if($("#nextUserId").val()==null||$("#nextUserId").val()==""){
			alert("跟进人不能为空!");
			return;
		}
		$("#saveBtn2").attr("disabled","disabled");
		
		$("#editForm").ajaxSubmit(
				{
					url : '${ctx }/project/xmfp/batchsave.json',
					type : "post",
					dataType : "json",
					success : function(data) {
						window.opener.location.reload();
						alert(data.msg);
						self.close();
					},
					error : function() {
						parent.mac
								.alert("系统响应失败");
					}
				});
	}
	</script>
	<form action="${ctx}/project/xmfp/save.ac" method="post"  enctype="multipart/form-data" id="editForm" >
		<input type="hidden" id="id" name="id" value="${id}"/>
			
		<fieldset style="width:80%;height:100%">
				<legend>批量分派信息</legend>
		<table class="editTab" id="gcxxjdTab">
				<tr>
					<th width="200" align="right">
						跟进人：
					</th>
					<td>
						<input type="text" name="nextUser.userName" id="nextUserName" value="${nextUser.userName}" readonly="readonly"/>
						<input type="hidden" name="nextUser.UserId" id="nextUserId" value="${nextUser.userId }" />
					 </td>	
				</tr>
				<tr>
					<th width="200" align="right">
						分派意见(100字以内)：
					</th>
					<td>
				        <textarea  id="pjauditmind" name="pjauditlog.pjauditmind"></textarea>
					 </td>	
				</tr>
		</table>
		<table  id="submitTab" style="width: 80%;">
			<tr><td>&nbsp;</td></tr>
			<tr>
				<td style="width: 100%;">
					<div>
						<span style="float: right;">
						 <input type="button" class="btn" id="saveBtn2" value="保存" onclick="saveProject()"> &nbsp;&nbsp;
						 <input type="reset" class="btn" id="resetBtn" value="重置"> &nbsp;&nbsp;
						 <input type="button" class="btn" id="closeBtn" value="关闭" onclick="window.close()">
						</span>
					</div>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>