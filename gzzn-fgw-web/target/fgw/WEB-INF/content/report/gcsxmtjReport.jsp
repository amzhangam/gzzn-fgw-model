<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/myFunctions" prefix="Custom" %>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>各处室项目情况统计报表 </title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<style type="text/css">
#ss{height:300px;width: 80%;}
</style>
</head>
<body>
<script src="${ctx}/resources/js/sys/sys.js" type="text/javascript"></script>
<script type="text/javascript">
$(document).ready(function() {
	 showMenu(3);
	 //alert( document.body.clientWidth+"===="+$("#mainContainer").width());
	 $("#mainContainer").width(document.body.clientWidth-$("#leftDiv").width()-10);//改变页面主体的宽度
	 $(".printDivStyle").niceScroll({touchbehavior:false,cursorcolor:"#808080",cursoropacitymax:0.6,cursorwidth:8});
	  
});

</script>

	<form id="report" action="${ctx}/report/gcsxmtjReport.ac" method="post">
		<!-- 相关数据展示 -->	
		<table style="width:98%;margin: 0 auto;">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle" style="width:128px;">各处室项目情况统计报表</td>
				<td class="tbHeadBg" style="width: 5%;"><div class="tbHeadMid"></div></td>
				<td class="tbHeadBg" style="width: 75%;">
					 <div>
					   <span style="float: right;">     
			            	<input type="button" class="btn" id="exportBtn" value="导出" onclick="exportexcel('${ctx}/report/expDeptProjectExcel.ac','tabList',3);"/>    
		               	</span>     
		             </div>
				</td>
				<td class="tbHeadBg" style="width: 3.5%;">
					<div class="tbHeadRight"></div>
				</td>
			</tr>
		</table>
 		<div class="printDivStyle" id="exp">
			<table class="printTab" id="tabList" align="center">
				<thead> 
					<tr>
						<th colspan="11" style="font-weight:bolder;align:center">统计时间：<font color="red">2014年7月1日之后</font></th>
					</tr>
					<tr>
						<th width="250px" rowspan="3" style="font-weight:bolder;color:black">发改委处室名称</th>
						<th colspan="5" style="font-weight:bolder;color:black">待处理项目数</th>	
						<th colspan="5" style="font-weight:bolder;color:black">审核通过项目数</th>
					</tr>
					<tr>
						<th style="font-weight:bolder;color:black">总数</th>
						<th colspan="2" style="font-weight:bolder;color:black">1000万元以下</th>	
						<th colspan="2" style="font-weight:bolder;color:black">1000万元（含）以上</th>
						<th style="font-weight:bolder;color:black">总数</th>
						<th colspan="2" style="font-weight:bolder;color:black">1000万元以下</th>
						<th colspan="2" style="font-weight:bolder;color:black">1000万元（含）以上</th>
					</tr>
					<tr>
						<th width="125px" style="font-weight:bolder;color:black"></th>
						<th width="125px" style="font-weight:bolder;color:black">基本建设类</th>
						<th width="125px" style="font-weight:bolder;color:black">更新改造类</th>	
						<th width="125px" style="font-weight:bolder;color:black">基本建设类</th>
						<th width="125px" style="font-weight:bolder;color:black">更新改造类</th>
						<th width="125px" style="font-weight:bolder;color:black"></th>
						<th width="125px" style="font-weight:bolder;color:black">基本建设类</th>
						<th width="125px" style="font-weight:bolder;color:black">更新改造类</th>
						<th width="125px" style="font-weight:bolder;color:black">基本建设类</th>
						<th width="125px" style="font-weight:bolder;color:black">更新改造类</th>
					</tr>
				</thead>
				<thead>
					<c:forEach items="${csxmPojos}" var="obj" varStatus="status">
						<tr ${obj.deptName=='合计'?'style="font-weight:bolder"':''}>
							<c:choose>
								<c:when test="${obj.deptName=='合计'}">
									<td align="center" style="text-align:right;font-size:14px;height:50px;font-weight:bolder">${obj.deptName}：</td> 	
								</c:when>
								<c:otherwise>
									<td align="center" style="text-align:center">${obj.deptName}</td>
								</c:otherwise>
							</c:choose>
							<td align="center" ${obj.deptName=='合计'?'style="font-size:14px;text-align:center;color:blue"':'style="text-align:center"'}>${obj.unPassCount>0?obj.unPassCount:''}</td> 						
							<td align="center" ${obj.deptName=='合计'?'style="font-size:14px;text-align:center;color:blue"':'style="text-align:center"'}>${obj.unPassUnderJbjsCount>0?obj.unPassUnderJbjsCount:''}</td> 						
							<td align="center" ${obj.deptName=='合计'?'style="font-size:14px;text-align:center;color:blue"':'style="text-align:center"'}>${obj.unPassUnderGxgzCount>0?obj.unPassUnderGxgzCount:''}</td> 						
							<td align="center" ${obj.deptName=='合计'?'style="font-size:14px;text-align:center;color:blue"':'style="text-align:center"'}>${obj.unPassUpJbjsCount>0?obj.unPassUpJbjsCount:''}</td> 						
							<td align="center" ${obj.deptName=='合计'?'style="font-size:14px;text-align:center;color:blue"':'style="text-align:center"'}>${obj.unPassUpGxgzCount>0?obj.unPassUpGxgzCount:''}</td> 	
												
							<td align="center" ${obj.deptName=='合计'?'style="font-size:14px;text-align:center;color:blue"':'style="text-align:center"'}>${obj.passCount>0?obj.passCount:''}</td> 						
							<td align="center" ${obj.deptName=='合计'?'style="font-size:14px;text-align:center;color:blue"':'style="text-align:center"'}>${obj.passUnderJbjsCount>0?obj.passUnderJbjsCount:''}</td> 	 						
							<td align="center" ${obj.deptName=='合计'?'style="font-size:14px;text-align:center;color:blue"':'style="text-align:center"'}>${obj.passUnderGxgzCount>0?obj.passUnderGxgzCount:''}</td> 					
							<td align="center" ${obj.deptName=='合计'?'style="font-size:14px;text-align:center;color:blue"':'style="text-align:center"'}>${obj.passUpJbjsCount>0?obj.passUpJbjsCount:''}</td> 						
							<td align="center" ${obj.deptName=='合计'?'style="font-size:14px;text-align:center;color:blue"':'style="text-align:center"'}>${obj.passUpGxgzCount>0?obj.passUpGxgzCount:''}</td> 			
						</tr>
					</c:forEach>
				</thead>
			</table>	
		</div>
	</form>
</body>
</html>
