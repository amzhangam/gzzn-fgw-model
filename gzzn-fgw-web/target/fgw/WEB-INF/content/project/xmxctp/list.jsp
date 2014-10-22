<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>项目图片长廊 </title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="${ctx}/resources/js/sys/sys.js" type="text/javascript"></script>
</head>
<body>
<script type="text/javascript">
$(document).ready(function() {
	 showMenu(2);
	
	if("${not empty message}" == "true"){
		mac.alert(Url.decode("${message}"));
		//alert(Url.decode("${message}"))
	}

	$("#queryBtn").click(function(){
		$("#currentPage").val("0");
		$("form")[0].submit();
	});
	
	//清空事件
	$("#clearBtn").click(function(){
		$(".topSearchTab input[type='text']").each(function(index) {
			$(this).val("");
		});
	});
	
	$("#addBtn").click(function(){
		window.location = "${ctx}/project/pjplanyear/edit.ac";
	});
	
	//勾选复选框，进行删除操作
	checkAllBox();
	delBtnClick("${ctx}/project/pjplanyear/delete.ac?id=");
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

function downloadFile(fileName,fileUrl){
	download_file("${ctx}/project/pjplanyear/downloadFile.ac?fileName="+ encodeURIComponent(fileName) +"&fileUrl="+ fileUrl);
}


</script>

	<form id="report" action="${ctx}/project/pjplanyear/list.ac" method="post">
		<!-- 加载相关的查询条件 -->
		<div class="topSearchDiv">
		  <table class="topSearchTab">
			<tr>
				<th>投资计划终稿名称：</th>
				<td><input type="text" class="text" name="params.investPlanName" id="investPlanName" value="${params.investPlanName }"/>	</td>
				<th>投资计划终稿年度：</th>
				<td>
					<input type="text" class="text Wdate" style="width:55px" name="params.startTime" id="startTime" value="${params.startTime}" onFocus="WdatePicker({dateFmt:'yyyy',isShowClear:true,readOnly:true,maxDate:'#F{$dp.$D(\'endTime\')}'})"/>
					至&nbsp;
					<input type="text" class="text Wdate" style="width:55px" name="params.endTime" id="endTime" value="${params.endTime}" onFocus="WdatePicker({dateFmt:'yyyy',isShowClear:true,readOnly:true,minDate:'#F{$dp.$D(\'startTime\')}'})"/>
				</td>
				<th>
					<input type="button" class="btn" id="queryBtn" value="查询" />&nbsp;&nbsp;
					<input type="button" class="btn" id="clearBtn" value="清空" />  
				</th>
			</tr>
		  </table>
		</div>
		<!-- 相关数据展示 -->	
		<table style="width: 98%;margin: 0 auto;">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle" style="width:128px;">项目图片长廊</td>
				<td class="tbHeadBg" style="width: 5%;"><div class="tbHeadMid"></div></td>
				<td class="tbHeadBg">
					 <div>
					    <span style="float: right;">     
			            	 <input type="button" class="btn" id="addBtn" value="新增"/>
			            	 <input type="button" class="btn" id="delBtn" value="批量删除"/>     
		               	</span>      
		             </div>
				</td>
				<td class="tbHeadBg" style="width: 3.5%;">
					<div class="tbHeadRight"></div>
				</td>
			</tr>
		</table>
		<table class="list" style="width: 98%;margin: 0 auto;">
			<thead> 
				<tr>
					<td width="5%"><input type="checkbox" id="checkAll" /></td>
					<td width="5%">序号</td>
					<td width="13%">投资计划终稿年度</td>
					<td width="20%">投资计划终稿名称</td>
					<td width="20%">投资计划终稿附件</td>	
					<td width="12%">上传人名称  </td>
					<td width="15%">上传时间</td>		
					<td width="10%">操作</td>					
				</tr>				
			</thead>
			<tbody>
				<c:forEach items="${page.list}" var="obj" varStatus="status">
					<tr>
						<td>
							<input type="checkbox" value="${obj.investPlanid}" class="subcheck"/>
						</td>
						<td>${page.size*page.current+status.index+1}</td> 
						<td>${obj.investPlanYear}</td>
						<td>${obj.investPlanName}</td> 						
						<td><a href="javaScript:void(0);" onclick="downloadFile('${obj.fileName}','${obj.fileUrl}');">${obj.fileName}</a></td> 
						<td>${obj.recorderName}</td> 						
						<td><fmt:formatDate value="${obj.recorderTime}" pattern="yyyy-MM-dd HH:mm"/></td>					
						<td>
							<a href="javascript:deleteAction('${ctx}/project/pjplanyear/delete.ac?id=${obj.investPlanid}')">删除</a>
						</td>						
					</tr>
				</c:forEach>
				<tr>
					<td colspan="8" class="line2"><%@include file="../../changePage.jsp" %></td>
				</tr>
			</tbody>
		</table>	
	</form>
</body>
</html>
