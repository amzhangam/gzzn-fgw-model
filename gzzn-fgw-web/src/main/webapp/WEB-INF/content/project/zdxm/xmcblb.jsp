<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="s" uri="/struts-tags"  %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>设置项目储备类别</title>
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
<body bgcolor="#EDF6FF">
<script type="text/javascript">
<!--
	$(document).ready(function() {
		showMenu(2,17);
	});
//-->

function ok(){
	$.post(
			"${ctx}/project/zdxm/setXmcblb.json",
			{"id":$("#id").val(),"xmcblb":$("#xmcblb").val(),"pageFlag":${pageFlag}},
			function(data){
		var json = $.parseJSON(data);
		if(json.flag){
			if(json.msg=='1'){
				window.opener.location = "${ctx}/project/zdxm/list.ac";
			}
			else if(json.msg=='2'){
				window.opener.location = "${ctx}/project/csshtgxm/list.ac";
			}
			else if(json.msg=='3'){
				window.opener.location = "${ctx}/report/zsxmk.ac";
			}
			else if(json.msg=='4'){
				window.opener.location = "${ctx}/report/zbxmk.ac";
			}
			self.close();
		}else{
			alert(json.msg);
		}
	});
}
</script>

	<form action="${ctx}/project/zdxm/setXmcblb.ac" method="post"  enctype="multipart/form-data">
	<input type="hidden" id="id" name="id" value="${id}"/>
	<br/>
	<br/>
		<table width="500" border="0" cellpadding="1" cellspacing="0" bgcolor="#EDF6FF"
		align="center" >
			<thead>
				<tr>
				
					<th width="200" align="right">
						项目储备类别：
					</th>
					<td width="200">
						<select id="xmcblb" name="xmcblb" style="width:100px"/>
							<option value="">(无)</option>
							<option value="储备项目库">预备项目库</option>
							<option value="正式项目库">正式项目库</option>
						</select>
					 </td>	
				</tr>
			<tr><td>&nbsp;</td></tr>
			<tr><td>&nbsp;</td></tr>
			<tr>
				<td style="width: 100%;" colspan="2">
					<div align="center">
						 <input type="button" class="btn" id="okBtn" value="确定" onclick="ok()">
						 <input type="button" class="btn" id="backBtn" value="关闭" onclick="window.close()">
					</div>
				</td>
			</tr>
			</thead>
		</table>
	</form>
</body>
</html>
