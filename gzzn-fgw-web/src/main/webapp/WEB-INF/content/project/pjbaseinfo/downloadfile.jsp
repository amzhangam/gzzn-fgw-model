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
资料下载
</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/2_event.css">
<link rel="stylesheet" type="text/css" href="${ctx}/resources/css/edittable.css">
<script type="text/javascript" src="${ctx}/resources/js/tc.all.js"></script>
<script src="${ctx}/resources/js/jquery-validation/jquery.validate.expand.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/jquery/jquery.form.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/eps/ztreePublic.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/sys/public.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/ajaxfileupload.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/project/pjbaseinfo/addPjbaseinfo.js" type="text/javascript"></script>
<script type="text/javascript">
var validator1;
var validator2;
//<!--
$(document).ready(function() {
	showMenu(2,17);
		//返回
		$("#backBtn").click(function() {
			window.location = "${ctx}/project/pjbaseinfo/list.ac";
		});
		
});
//-->

/* ajax下载文件 
	@url: 文件url路径
	*/
	function download_file(url) {
		if (typeof (download_file.iframe) == "undefined") {
			var iframe = document.createElement("iframe");
			download_file.iframe = iframe;
			
			document.body.appendChild(download_file.iframe);
		}
		download_file.iframe.src = url;
		download_file.iframe.style.display = "none";
	}

	function downloadModel(no){
		//var fileName = "2013年以来立项的项目建设情况明细表模板.xls";
		var fileName = "IE8-WindowsXP-x86-CHS.rar";
		var fileUrl = "/upload/pjbaseinfo/fj11.xls";
		if(no==6){
			fileName = "广州市政府投资管理条例(2011).doc";
			fileUrl = "/upload/pjbaseinfo/fj6.doc";
		}
		else if(no==7){
			fileName = "广州市发展改革委 广州市财政局关于2015年广州市本级政府投资项目库申报工作的通知.tif";
			fileUrl = "/upload/pjbaseinfo/fj7.tif";
		}
		else if(no==8){
			fileName = "广州市政府投资管理条例实施细则(试行)(2014).doc";
			fileUrl = "/upload/pjbaseinfo/fj8.doc";
		}
		else if(no==9){
			fileName = "广州市政府投资项目基本情况（更新改造类）.doc";
			fileUrl = "/upload/pjbaseinfo/fj9.doc";
		}
		else if(no==10){
			fileName = "广州市政府投资项目基本情况（基本建设类）（范本）.doc";
			fileUrl = "/upload/pjbaseinfo/fj10.doc";
		}
		else if(no==11){
			fileName = "IE8-WindowsXP-x86-CHS.rar";
			fileUrl = "/upload/pjbaseinfo/fj11.rar";
		}
		else if(no==12){
			fileName = "2014年政府投资项目申报填写说明.doc";
			fileUrl = "/upload/pjbaseinfo/fj12.doc";
		}
		else if(no==13){
			fileName = "广州市发改委政府投资项目库申报信息系统培训材料.ppt";
			fileUrl = "/upload/pjbaseinfo/fj13.ppt";
		}
		else if(no==14){
			fileName = "广州市政府投资项目库申报信息系统培训时间通知--2014年7月8日.pdf";
			fileUrl = "/upload/pjbaseinfo/fj14.pdf";
		}
		else if(no==15){
			fileName = "项目实施计划.doc";
			fileUrl = "/upload/pjbaseinfo/fj15.doc";
		}
		else if(no==16){
			fileName = "较规范的投资评审资料.zip";
			fileUrl = "/upload/pjbaseinfo/fj16.zip";
		}
		download_file("${ctx}/project/pjplanyear/downloadTempFile.ac?fileName="+ encodeURIComponent(fileName) +"&fileUrl="+ fileUrl);
	}
</script>
</head>
<body>
	
		<div class="editDiv">
			
			<fieldset style="width:80%;height:100%;">
				<legend>资料下载</legend>
			<table class="editTab" id="jbqkTab">
				<tr>
					<td><a href="#"  onclick="downloadModel(6)">广州市政府投资管理条例(2011).doc</a></td>	
				</tr>
				<tr>
					<td><a href="#"  onclick="downloadModel(7)">广州市发展改革委 广州市财政局关于2015年广州市本级政府投资项目库申报工作的通知.tif</a></td>	
				</tr>
				<tr>
					<td><a href="#"  onclick="downloadModel(8)">广州市政府投资管理条例实施细则(试行)(2014).doc</a></td>	
				</tr>
				<tr>
					<td><a href="#"  onclick="downloadModel(9)">广州市政府投资项目基本情况（更新改造类）.doc</a></td>	
				</tr>
				<tr>
					<td><a href="#"  onclick="downloadModel(10)">广州市政府投资项目基本情况（基本建设类）（范本）.doc</a></td>	
				</tr>
				<tr>
					<td><a href="#"  onclick="downloadModel(11)">IE8-WindowsXP-x86-CHS.rar</a></td>	
				</tr>
				<tr>
					<td><a href="#"  onclick="downloadModel(12)">2014年政府投资项目申报填写说明.doc</a></td>	
				</tr>
				<tr>
					<td><a href="#"  onclick="downloadModel(13)">广州市发改委政府投资项目库申报信息系统培训材料.ppt</a></td>	
				</tr>
				<tr>
					<td><a href="#"  onclick="downloadModel(14)">广州市政府投资项目库申报信息系统培训时间通知--2014年7月8日.pdf</a></td>	
				</tr>
				<tr>
					<td><a href="#"  onclick="downloadModel(15)">项目实施计划.doc</a></td>	
				</tr>
				<tr>
					<td><a href="#"  onclick="downloadModel(16)">较规范的投资评审资料.zip</a></td>	
				</tr>
				
			</table>
			</fieldset>
		</div>
	</form>
</body>
</html>