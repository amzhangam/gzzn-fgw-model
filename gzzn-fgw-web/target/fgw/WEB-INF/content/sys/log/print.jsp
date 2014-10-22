<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>日志打印功能</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">

<link href="${ctx}/resources/css/office.css" rel="stylesheet" type="text/css" />
<script src="${ctx}/resources/js/jquery/jquery-1.8.3.js" type="text/javascript"></script>
<script type="text/javascript" src="${ctx}/resources/js/office/OfficeContorlFunctions.js"></script>
<script type="text/javascript">
	$(document).ready(function() {
		if("${not empty filepath}" == "true"){
			intializePage("${ctx}/officetemp/${filepath}");
		}else{
			alert("对不起，没有可以打印的相关数据！");
		}
	});

</script>
</head>
<body>
	<div id="editmain" class="editmain" style="width:100%">
		<div id="editmain_top" class="editmain_top">
			<input type="button" class="officeBtn" value="关闭窗口" onclick="window.close();"/>
		</div>
		
		<div id="officecontrol">
			<object id='NTKO_OCX' classid='clsid:C9BC4DFF-4248-4a3c-8A49-63A7D317F404'
				codebase='${ctx}/download/OfficeControl.cab#version=5,0,1,0' width='100%' height='100%'>
				<param name="BorderStyle" value="1">
				<param name="TitlebarColor" value="42768">
				<param name="TitlebarTextColor" value="0">
				<param name="Caption" value="欢迎使用！">
				<param name="MakerCaption" value="广州智能科技发展有限公司">
				<param name="MakerKey" value="7673DC3A5B31632906303598D99F600038935B1C">
				<param name="ProductCaption" value="管理综合业务系统">
				<param name="ProductKey" value="36300F5E2EDEE2CBF8EAF81835F6081832490903">
				<SPAN STYLE='color: red'>不能装载文档控件。请在检查浏览器的选项中检查浏览器的安全设置。</SPAN>
			</object>
			<script language="JScript" for=NTKO_OCX event="OnDocumentClosed()">
			</script>
			<script language="JScript" for=NTKO_OCX event="OnDocumentOpened(TANGER_OCX_str,TANGER_OCX_obj)">						
				//OFFICE_CONTROL_OBJ.activeDocument.saved=true;//saved属性用来判断文档是否被修改过,文档打开的时候设置成ture,当文档被修改,自动被设置为false,该属性由office提供.
				OFFICE_CONTROL_OBJ.FullScreenMode = true
				//OFFICE_CONTROL_OBJ.IsNoCopy = false;
				// 填充word模板签内容
				/* if("${empty filepath}" == "true"){
					initValue();
				} */
			</script>
		</div>
	</div>
</body>
</html>
