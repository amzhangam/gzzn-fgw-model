<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>
<c:if test="${sysParams.read==0}">
	待办工作信息
</c:if>
<c:if test="${sysParams.read==1}">
	已办工作信息
</c:if>
</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="${ctx}/resources/js/sys/sys.js" type="text/javascript"></script>
</head>
<body>
<script type="text/javascript">
//<!--
 	
	$(document).ready(function() {
		showMenu(2,66);
		
		$("#queryBtn").click(function(){
			$("#currentPage").val("0");
			$("form")[0].submit();
		});
	});
	

//-->
</script>

	<form action="${ctx}/index/list.ac" method="post">
		<!-- 加载相关的查询条件 -->
		<div class="topSearchDiv" style="width: 100%;">
			  <table class="topSearchTab">
				<tr>
					<%-- <th>项目名称：</th>
					<td>
					  <input type="text" class="text" name="sysParams.projectName" value="${sysParams.projectName}" />
					</td>
					<th>操作内容：</th>
					<td>
					  <input type="text" class="text" name="sysParams.likeData" value="${sysParams.likeData}" />
					</td> --%>
					<th>发送时间：</th>
					<td>
				    	<input type="text" class="text Wdate" name="sysParams.startTime" id="startTime" value="${sysParams.startTime}" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:true,readOnly:true,maxDate:'#F{$dp.$D(\'endTime\')}'})"/>
	                                                             至
	                    <input type="text" class="text Wdate" name="sysParams.endTime" id="endTime" value="${sysParams.endTime}" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:true,readOnly:true,minDate:'#F{$dp.$D(\'startTime\')}'})"/>
					</td>
					<c:if test="${sysParams.read==1}">
						<th>读取时间：</th>
						<td>
					    	<input type="text" class="text Wdate" name="sysParams.readStartTime" id="readStartTime" value="${sysParams.readStartTime}" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:true,readOnly:true,maxDate:'#F{$dp.$D(\'readEndTime\')}'})"/>
		                                                             至
		                    <input type="text" class="text Wdate" name="sysParams.readEndTime" id="readEndTime" value="${sysParams.readEndTime}" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:true,readOnly:true,minDate:'#F{$dp.$D(\'readStartTime\')}'})"/>
						</td>
				    </c:if>
					<th>
						<input type="hidden" class="text" name="sysParams.read" id="read" value="${sysParams.read}" />
						<input type="button" class="btn" id="queryBtn" value="查询" />
					</th>
				</tr>
			  </table>
		</div>
		<table style="width:100%">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle">
					<c:if test="${sysParams.read==0}">
						待办工作信息
					</c:if>
					<c:if test="${sysParams.read==1}">
						已办工作信息
					</c:if>
				</td>
				<td class="tbHeadBg" style="width: 5%;"><div class="tbHeadMid"></div></td>
				<td class="tbHeadBg" style="width: 79%;">
					 <div>
					    <span style="float: right;">     
		               	</span>     
		             </div>
				</td>
				<td class="tbHeadBg" style="width: 3.5%;">
					<div class="tbHeadRight"></div>
				</td>
			</tr>
		</table>
		
	<table id="tabList" width="100%" border="0" cellpadding="1" cellspacing="0" bordercolor="AEC2D5" class="list">
		<thead>
			<tr>
				<td width="5%">序号</td>
				<td width="35%">标题</td>
				<td width="20%">发送单位</td>
				<td width="15%">发送时间</td>
				<c:if test="${sysParams.read==1}">
					<td width="10%">读取用户</td>
					<td width="15%">读取时间</td>
				</c:if>
			</tr>
		</thead>
		<tbody>
	  	  <c:forEach items="${page.list}" var="obj" varStatus="status"> 
			<tr>
				<td>${page.size*page.current+status.index+1}</td>
				<td>
					<c:if test="${sysParams.read==0}">
						<a href="${ctx}/index/toDoWork.ac?id=${obj.operationlogRecId}&sysParams.read=0">
					</c:if>
					${obj.operationContent}
					<c:if test="${sysParams.read==0}"></a></c:if>
				</td>
				<td>${obj.sysOrganization.organizationName}</td>
				<td><fmt:formatDate value="${obj.operationDatetime}" pattern="yyyy-MM-dd HH:mm"/></td>
				<c:if test="${sysParams.read==1}">
					<td>${obj.readUserObj.userName}</td>
					<td><fmt:formatDate value="${obj.readtime}" pattern="yyyy-MM-dd HH:mm"/></td>
				</c:if>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="${sysParams.read==1?6:4}" class="line2" style="text-align: right;">
				<%@include file="../changePage.jsp" %>
			</td>
		</tr>
		</tbody>
	</table>
 </form>
</body>
</html>
