<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/myFunctions" prefix="Custom" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<meta http-equiv="pragma" content="no-cache" />
<meta http-equiv="cache-control" content="no-cache" />
<meta http-equiv="expires" content="0" />
<title>导入上报项目数据</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="${ctx}/resources/js/sys/sys.js" type="text/javascript"></script>
<script src="${ctx}/resources/js/sys/fileUploadCheck.js" type="text/javascript"></script>
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
#message p{height: 26px;}
</style>
</head>
<body>
<script type="text/javascript">
$(document).ready(function() {
	showMenu(12,21);//,#auditData
	$("#message").niceScroll({touchbehavior:false,cursorcolor:"#808080",cursoropacitymax:0.6,cursorwidth:8});
	$("#mainContainer").width(document.body.clientWidth-$("#leftDiv").width()-10);//改变页面主体的宽度
	$(".printDivStyle").niceScroll({touchbehavior:false,cursorcolor:"#808080",cursoropacitymax:0.6,cursorwidth:8});
	
	
	/** if("${params.stuType eq impl}"=="true" && "${not empty message}" == "true"){
		//mac.alert(Url.decode("${message}"));
		$("#message").html("<font color='#000'>"+ Url.decode("${message}") +"</font>");
	 }*/
	
	
	//导入数据
	$("#uploadBtn").click(function(){
		$("#message").html("");
		/**if($("#impFile").val()===""){
			$("#message").html("<font color='red'>上传文件不允许为空，请选择要上传的文件</font>");
			return;
		}
		if($("#impFile").val().match(/\.xls(x)?$/)==null){
			$("#message").html("<font color='red'>上传文件后缀必须为.xls或.xlsx，请重新选择文件</font>");
			return;
		}*/
		var filesObj = document.getElementsByName("impFile");
		var checkBol = checkFileSize("文件(.xls或.xlsx)", filesObj, true, 10, [".xls", ".xlsx"]);
		if(checkBol==false){
			return;
		}
		//临时库中本单位已存在的未自检数据将会被清空，确认要上传吗
		mac.confirm('<p>临时库中您已上传的数据将会被清空，确认要上传吗?</p>', function(){
			$("#message").html("上传处理中...");
			$("#stuType").val("impl");
			$("#report").submit();
		}, null);
	});
	//自检上报数据
	$("#auditBtn").click(function(){
		saveCheckBoxInfo("自检上报","<p>确认要自检上报已选中的记录吗?</p>","${ctx}/buildImpl/dataAudit.ac");
		/**if("${!empty list}"=="true"){
			mac.confirm('<p>改单位数据已存在，是否覆盖?</p>', function(){
				window.location = "${ctx}/buildImpl/dataAudit.ac?id=" + ids;
			}, null);
		
		}else{
			mac.confirm('<p>确认要保存已选中的记录吗?</p>', function(){
				window.location = "${ctx}/buildImpl/dataAudit.ac?id=" + ids;
			}, null);
		}*/
	});
	
	//覆盖重复数据
	$("#reapetBtn").click(function(){
		saveCheckBoxInfo("覆盖","<p>确认要覆盖正式库中对应的记录吗?</p>","${ctx}/buildImpl/dataImpl.ac");
	});	
	
	//勾选复选框，进行删除操作
	checkAllBox();
	
});

function saveCheckBoxInfo(alertInfo,confirmInfo,url){
	var ids = "";
	$(".subcheck").each(function(){
		if($(this).attr("checked")){
			ids += "@" + $(this).val();
		}
	});
	if(ids.length == 0){
		mac.alert("请选择要"+ alertInfo +"的记录");
		return;
	}
	ids = ids.substring(1);
	//alert(ids);
	mac.confirm(confirmInfo, function(){
			$("#id").val(ids);
			var action = document.forms[0].action;
			document.forms[0].action = url;
			document.forms[0].submit();
			document.forms[0].action = action;
	}, null);
}


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
	fileName = "2013年项目建设情况明细表模板.xls";
	fileUrl = "/uploads/bulidImpl/bulidImplTemp.xls";
	download_file("${ctx}/project/pjplanyear/downloadFile.ac?fileName="+ encodeURIComponent(fileName) +"&fileUrl="+ fileUrl);
}



</script>
	 <form id="report" action="${ctx}/buildImpl/dataImpl.ac" method="post"  enctype="multipart/form-data">
	 	<input type="hidden" name="params.stuType" id="stuType" value="${params.stuType}">
	 	<input type="hidden" name="id" id="id" value="">
		<div class="topSearchDiv" style="background: #FFFFFF;">
			<!-- 导入上报项目数据 -->
			  <p class="titleP">导入上报项目数据</p>
			  <table class="topSearchTab">
			  	<tr>
			  		<th>&nbsp;&nbsp;文件(.xls或.xlsx)<font color="red">*</font></th>
			  		<td><input type="file" title="文件应小于10M" name="impFile" id="impFile" style="width: 400px;"/></td>
			  		<td>
			  			<input type="button" class="btn" id="uploadBtn" value="上&nbsp;&nbsp;传" />&nbsp;&nbsp;
						<!-- <input type="button" class="btn" id="downloadBtn" value="下载导入模板"  onclick="downloadModel()"/>   -->
			  		</td>
			  	</tr>
			  	</table>
			<!-- 显示上传消息 -->
			 <p class="titleP">导入消息提示</p>
			 <div id="message" style="height: 120px;">
				<s:fielderror></s:fielderror>
				<s:actionmessage></s:actionmessage>
				 <table class="topSearchTab">
					<c:forEach items="${msg}" var ="b">
						<tr><td>${b}</td></tr>
					</c:forEach>
				</table>
			</div>
		</div>
		<!-- 相关数据展示 -->	
		<table style="width:98%;margin: 0 auto;">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle" style="width:138px;">
					<c:if test="${not empty params.stuType && params.stuType eq 'saveRepeart'}" var="result">
						上报项目自检重复数据
					</c:if>
					<c:if test="${!result}">
						上报项目未自检数据
					</c:if>
				</td>
				<td class="tbHeadBg" style="width: 5%;"><div class="tbHeadMid"></div></td>
				<td class="tbHeadBg">
					 <div>
						<c:if test="${not empty params.stuType && params.stuType eq 'saveRepeart'}" var="result">
							<font color="green">以下为重复数据，如需覆盖请选择相应的数据</font>
							<span style="float: right;"> 
								<input type="button" class="btn" id="reapetBtn" value="覆盖重复数据" />  
							</span>      
						</c:if>
						<c:if test="${!result}">
							 <font color="red">注：省重点项目请于项目名称前标注★ 市重点项目请于项目名称前标注■</font>
							<span style="float: right;"> 
							  <input type="button" class="btn" id="auditBtn" value="自检上报" />  
							</span>  
						</c:if>
		             </div>
				</td>
				<td class="tbHeadBg" style="width: 3.5%;">
					<div class="tbHeadRight"></div>
				</td>
			</tr>
		</table>
		<div class="printDivStyle" id="exp">
			<table class="printTab" id="tabList" style="width:3380px">
			<!-- <table class="list" style="width: 98%;margin: 0 auto;"> -->
				<thead> 
					<tr>
						<th width="40px"><input type="checkbox" id="checkAll" /></th>
						<th width="50px">序号</th>
						<th width="190px">项目名称</th>
						<th width="250px">建设内容及规模</th>	
						<th width="150px">建设起止年限 </th>
						<th width="150px">总投资（万元）</th>	
						<th width="150px">到上年底累计完成投资（万元）<!-- 到2013年底累计完成投资（万元） --></th>	
						<th width="150px">本年度投资计划（万元）<!-- 2014年投资计划（万元） --></th>	
						<th width="150px">本年度投资计划完成情况（万元）<!-- 2014年以来完成投资（万元） --></th>	
						<th width="250px">立项批复文号</th>	
						<th width="250px">批复单位</th>	
						<th width="100px">批复时间</th>	
						<th width="250px">项目类型</th>	
						<th width="250px">项目类别</th>	
						<th width="150px">产业类别</th>
						<th width="150px">投资类型</th>	
						<th width="250px">所属行业</th>
						<th width="250px">项目(法人)单位</th>
						<th width="100px">项目联系人</th>	
						<th width="100px">联系电话</th>	
						<th width="100px">项目属地</th>
						<th width="250px">填报单位</th>	
					</tr>				
				</thead>
				<tbody>
					<c:forEach items="${listTemp}" var="obj" varStatus="status">
						<tr>
							<td>
								<input type="checkbox" value="${obj.bulidId}" class="subcheck"/>
							</td>
							<td>${page.size*page.current+status.index+1}</td> 
							<td>
								<c:if test="${obj.zdxmbz eq 1 }"><font color="red">★</font></c:if>
								<c:if test="${obj.zdxmbz eq 2 }"><font color="red">■</font></c:if>
								${obj.projectname}&nbsp;
							</td>
							<td>${obj.projectcontent}&nbsp;</td> 	
							<td>${obj.bulidQznx}&nbsp;</td> 	
							<td>${obj.pjinvestsum}&nbsp;</td> 	
							<td>${obj.expectfinishinvest}&nbsp;</td> 	
							<td>${obj.planinvestsum}&nbsp;</td> 	
							<td>${obj.currentfinishinvest}&nbsp;</td> 	
							<td>${obj.lxpfwh}&nbsp;</td> 
							<td>${obj.pfdw}&nbsp;</td> 
							<td>${obj.pfsj}&nbsp;</td> 
							<td>${obj.xmlx}&nbsp;</td> 
							<td>${obj.remark}&nbsp;</td> 
							<td>${obj.cylb}&nbsp;</td> 	
							<td>${obj.tzlx}&nbsp;</td> 	
							<td>${obj.sshy}&nbsp;</td> 
							<td>${obj.xmdw}&nbsp;</td> 
							<td>${obj.projectcontact}&nbsp;</td> 	
							<td>${obj.phone}&nbsp;</td> 	
							<%-- <td>${obj.sysOrganization.sysXq.xqmc}&nbsp;</td> 
							<td>${obj.sysOrganization.organizationName}&nbsp;</td>  --%>
							<td>${obj.xmsd}&nbsp;</td> 
							<td>${obj.tbdw}&nbsp;</td> 
						</tr>
					</c:forEach>
				</tbody>
			</table>	
		</div>
	</form>
</body>
</html>
