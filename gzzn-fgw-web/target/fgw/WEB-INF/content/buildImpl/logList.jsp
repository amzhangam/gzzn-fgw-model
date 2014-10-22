<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>填报日志</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="${ctx}/resources/js/sys/sys.js" type="text/javascript"></script>
</head>
<body>
<script type="text/javascript">
//<!--
 	
	$(document).ready(function() {
		//showMenu(2,210);
		showMenu(12,21);
		$("#operationType").val("${sysParams.operationType}");

		$("#queryBtn").click(function(){
			$("#currentPage").val("0");
			$("form")[0].submit();
		});
		
		//清空事件
		$("#clearBtn").click(function(){
			$(".topSearchTab input[type='text'],.topSearchTab select").each(function(index) {
				$(this).val("");
			});
		});
	});
	

//-->
</script>

	<form action="${ctx}/buildImpl/logList.ac" method="post">
		<!-- 加载相关的查询条件 -->
		<div class="topSearchDiv" style="width: 100%;">
			  <table class="topSearchTab">
				<tr>
					<th>用户名称：</th>
					<td>
					  <input type="text" class="text" name="sysParams.userName" value="${sysParams.userName}" />
					</td>
					<th>单位名称：</th>
					<td>
					  <input type="text" class="text" name="sysParams.organName" value="${sysParams.organName}" />
					</td>
					<th>部门名称：</th>
					<td>
					  <input type="text" class="text" name="sysParams.deptname" value="${sysParams.deptname}" />
					</td>
					<th>填报单位：</th>
					<td>
					  <input type="text" class="text" name="sysParams.tbdw" value="${sysParams.tbdw}" />
					</td>
					<th>
						<input type="button" class="btn" id="queryBtn" value="查询" />&nbsp;&nbsp;
						<input type="button" class="btn" id="clearBtn" value="清空" />  
					</th>
				</tr>
				<tr>
					<th>操作类型：</th>
					<td>
					  <select id="operationType" name="sysParams.operationType" style="border: 1px solid #be9a4d;width: 136px;">
					  	<option value=""></option>
					  	<option value="1">上传导入数据</option>
					  	<option value="2">自检上报数据</option>
					  	<option value="3">覆盖重复数据</option>
					  </select>
					</td>
					<th>操作内容：</th>
					<td>
					  <input type="text" class="text" name="sysParams.likeData" value="${sysParams.likeData}" />
					</td>
					<th>日期：</th>
					<td colspan="4">
				    	<input type="text" class="text Wdate" name="sysParams.startTime" id="startTime" value="${sysParams.startTime}" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,readOnly:true,maxDate:'#F{$dp.$D(\'endTime\')}'})"/>
	                                                             至
	                    <input type="text" class="text Wdate" name="sysParams.endTime" id="endTime" value="${sysParams.endTime}" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:true,readOnly:true,minDate:'#F{$dp.$D(\'startTime\')}'})"/>
					</td>
				</tr>
			  </table>
		</div>
		<table style="width:100%">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle">填报日志</td>
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
				<td width="8%">用户名称</td>
				<td width="12%">单位名称</td>
				<td width="10%">部门名称</td>
				<td width="12%">填报单位</td>
				<td width="10%">操作类型</td>
				<td width="32%">操作内容</td>
				<td width="12%">操作时间</td>
			</tr>
		</thead>
		<tbody>
	  	  <c:forEach items="${page.list}" var="obj" varStatus="status"> 
			<tr>
				<td>${page.size*page.current+status.index+1}</td>
				<td>${obj.sysUser.userName}</td>
				<td>${obj.sysOrganization.organizationName}</td>
				<td>${obj.sysDept.deptname}</td> 
				<td>${obj.tbdw}</td> 
				<td>
					<c:choose>
					    <c:when test="${obj.operationType==1}">上传导入数据</c:when>
						<c:when test="${obj.operationType==2}">自检上报数据</c:when>
						<c:when test="${obj.operationType==3}">覆盖重复数据</c:when>
				    </c:choose>
				</td> 
				<td>${obj.operationContent}</td>
				<td><fmt:formatDate value="${obj.operationDatetime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="8" class="line2" style="text-align: right;">
				<%@include file="../changePage.jsp" %>
			</td>
		</tr>
		</tbody>
	</table>
 </form>
</body>
</html>
