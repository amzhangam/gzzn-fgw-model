<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<html>
<head>
<title>下载模板</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="${ctx}/resources/js/sys/sys.js" type="text/javascript"></script>
<style type="text/css">
.titleP{
	background: #D3E4FF;
	border-bottom: 1px solid #85B0E3;
	height: 28px;
	line-height: 28px;
	margin-bottom: 5px;
	padding-left: 10px;
	font-weight: bold;
	color: #002D5F;
	text-align: center;
}
.helpAbout {
	width: 100%;
	color: #CC0033;
}
.helpAbout ul li{
	list-style: none;
/* 	height: 25px;
	line-height: 25px; */
	border-bottom: 1px dotted #d6d6d6;
	padding: 5px 0 5px 20px;
}
</style>
</head>
<body>
<script type="text/javascript">
$(document).ready(function() {
	showMenu(12,21);//,#auditData
	//$("#message").niceScroll({touchbehavior:false,cursorcolor:"#808080",cursoropacitymax:0.6,cursorwidth:8});
});

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

function downloadModel(){
	//var fileName = "2013年以来立项的项目建设情况明细表模板.xls";
	var fileName = "2013.xls";
	var fileUrl = "/upload/bulidImpl/bulidImplTempNew.xls";
	download_file("${ctx}/project/pjplanyear/downloadTempFile.ac?fileName="+ encodeURIComponent(fileName) +"&fileUrl="+ fileUrl);
}

function downloadModel2(){
	var fileName = "IndusGBCode.xls";//行业分类（国标行业）编码规范
	var fileUrl = "/upload/bulidImpl/IndusGBCode.xls";
	download_file("${ctx}/project/pjplanyear/downloadTempFile.ac?fileName="+ encodeURIComponent(fileName) +"&fileUrl="+ fileUrl);
}



</script>
		<div class="topSearchDiv" style="background: #FFFFFF;">
			<!-- 下载上报项目数据模板 -->
			  <p class="titleP">下载上报项目数据模板</p>
			  <table class="topSearchTab">
			  	<tr>
			  		<th>&nbsp;&nbsp;下载数据模板：</th>
			  		<td>
						<input type="button" class="btn" id="downloadBtn" value="上报项目数据模板"  onclick="downloadModel()"/> &nbsp;&nbsp;
						<input type="button" class="btn" id="downloadBtn" value="行业分类（国标行业）编码规范"  onclick="downloadModel2()"/>   
			  		</td>
			  	</tr>
			  	</table>
			<!-- 显示上传消息 -->
			 <p class="titleP">数据填写相关说明</p>
			 <div class="helpAbout">
			 	<ul>
			 		<li style="padding-left: 5px;font-weight: bold;">请务必按照指定模板填写相关数据：</li>
			 		<li>No1、Excel表中第二行：填报单位（盖章）不允许为空</li>
			 		<li>No2、Excel表中非空字段：B-项目名称、C-建设内容及规模、D-建设起止年限、E-总投资（万元）、F-到上年底累计完成投资（万元）、G-本年度投资计划（万元）、H-本年度投资计划完成情况（万元）、L-项目类型、M-项目类别、N-产业类别、O-投资类型、P-所属行业、Q-项目(法人)单位、Q-项目联系人、Q-联系电话、T-项目属地</li>
			 		<li>No3、建设起止年限：到年【年份在1900-2099间】，中间用“-”隔开，且“拟开工时间”不能大于“拟建成时间”，格式如：2013-2015</li>
			 		<li>No4、立项批复文号：项建、可研、备案、核准文号</li>
			 		<li>No5、批复时间：到月【其中年份在1900-2099间，月份在01-12或1-12间】，格式如：2014-03 或 2014-3</li>
			 		<li>No6、项目类型：审批【A00001】、核准【A00002】、备案【A00003】、项目建议书【A00004】、可行性研究报告【A00005】、
			 			初步设计及概算【A00006】、其他【A00007】；对于审批项目，请细化到详细批复内容（A00004/A00005/A00006/A00007）</li>
			 		<li>No7、项目类别：完工项目、在建项目、未开工项目 <!-- 2013年以来立项（审批、核准、备案）项目 --></li>
			 		<li>No8、产业类别：现代产业类、基础设施类、社会事业类、绿色发展类、宜居城乡类</li>
			 		<li>No9、投资类型：国有投资、民间投资、港澳台投资、外商投资</li>
			 		<li>No10、所属行业：按统计局行业分类标准文字或对应编码填写，具体信息请下载“行业分类（国标行业）编码规范”</li>	
			 		<li>No11、联系电话：如果存在多个联系电话请使用,或空格隔开，格式如：29196160,18925142590,13500003804 或 29196160 18925142590 13500003804</li>	
			 		<li>No12、项目属地：按市本级，各区、县级市填写</li>	
			 	</ul>
			</div>
		</div>
</body>
</html>
