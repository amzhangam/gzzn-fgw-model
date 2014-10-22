<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="sitemesh" uri="http://www.opensymphony.com/sitemesh/decorator" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>${configObj['system.name.main']}${configObj["system.name.sub"]}<sitemesh:title/></title>
<meta http-equiv="Content-Type" content="text/html;charset=utf-8" />
<meta http-equiv="Cache-Control" content="no-store" />
<meta http-equiv="Pragma" content="no-cache" />
<meta http-equiv="Expires" content="0" />

<link href="${ctx}/resources/css/2_event.css" type="text/css" rel="stylesheet">
<link href="${ctx}/resources/css/style.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/resources/js/jquery-ztree/zTreeStyle/zTreeStyle.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/resources/js/jquery-ui/css/smoothness/jquery-ui.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/resources/js/jquery-validation/1.10.0/validate.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/resources/js/jquery-dialog_2.4/css/default.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/resources/js/jquery-grid_3.0b/css/default.css" type="text/css" rel="stylesheet" />
<link href="${ctx}/resources/js/jquery-autocomplete/css/jquery.autocomplete.css" type="text/css" rel="stylesheet" />

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
<!-- 
<script src="${ctx}/resources/js/jquery/jquery.form.js" type="text/javascript"></script>
-->
<script src="${ctx}/resources/js/jquery-validation/1.10.0/jquery.validate.min.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/jquery-validation/1.10.0/messages_cn.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/jquery-validation/jquery.validate.expand.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/RBAlertMSG.js" type="text/javascript"></script>

<script src="${ctx}/resources/js/eps/ztreePublic.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/jquery.nicescroll.js" type="text/javascript"></script>



<script type="text/javascript">
	$(function(){
		 //$( document ).tooltip();
		 //alert("===="+$("#mainContainer").width());
	});
</script>
<sitemesh:head />

<style type="text/css">
	body{
		margin: 0;
		padding: 0;
	}
	#mainContainer{
		min-height:700px;
		float:left;
		padding-left: 4px;
		padding-bottom: 5px;
		/* border: 1px solid red; */
	}
	.warning { color:blue; }
	.exceeded { color:red; }
</style>

</head>

<body>
	<div>
		<%@ include file="/WEB-INF/layouts/header.jsp"%>
		<div class="clear"></div>
		<div>
			<div id="leftDiv" style="float:left;">
			<%-- <%@ include file="/WEB-INF/layouts/left.jsp"%> --%>
				<%@ include file="/WEB-INF/layouts/leftNew.jsp"%>
			</div>
			<div id="mainContainer">
				<sitemesh:body />
			</div>
			<div class="clear"></div>
		</div>
		<div class="clear"></div>
		<%@ include file="/WEB-INF/layouts/footer.jsp"%>
	</div>
	<%@ include file="msgInfo.jsp"%>
</body>
</html>