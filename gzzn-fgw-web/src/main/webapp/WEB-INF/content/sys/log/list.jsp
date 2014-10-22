<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>用户操作日志</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="${ctx}/resources/js/sys/sys.js" type="text/javascript"></script>
</head>
<body>
<script type="text/javascript">
//<!--
 	
	$(document).ready(function() {
		showMenu(6,66);
		
	    if("${not empty message}" == "true"){
			mac.alert(Url.decode("${message}"));
			//alert(Url.decode("${message}"))
		}

		$("#queryBtn").click(function(){
			$("#currentPage").val("0");
			$("form")[0].submit();
		});
		
		$("#printBtn").click(function(){
			var table = document.getElementById("tabList");
			if(table.rows.length == 2){
				alert("没有可以打印的数据.");
				return;
			}
			//打印日志信息
			//window.open("${ctx}/sys/log/print.json");
			var oldurl = document.forms[0].action;
			document.forms[0].action = "${ctx}/sys/log/print.json";
			document.forms[0].target="_blank";
			document.forms[0].submit();
			document.forms[0].action = oldurl;
			document.forms[0].target="";
		});
		
	});
	

//-->
</script>

	<form action="${ctx}/sys/log/list.ac" method="post">
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
					<th>
						<c:if test="${objectMap['XMSB_XT_RZGL_CZRZ_VIEW']}">
						<input type="button" class="btn" id="queryBtn" value="查询" />
						</c:if>
					</th>
				</tr>
				<tr>
					<th>操作内容：</th>
					<td>
					  <input type="text" class="text" name="sysParams.likeData" value="${sysParams.likeData}" />
					</td>
					<th>日期：</th>
					<td colspan="4">
				    	<input type="text" class="text Wdate" name="sysParams.startTime" id="startTime" value="${sysParams.startTime}" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm',isShowClear:false,readOnly:true,maxDate:'#F{$dp.$D(\'endTime\')}'})"/>
	                                                             至
	                    <input type="text" class="text Wdate" name="sysParams.endTime" id="endTime" value="${sysParams.endTime}" onFocus="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',isShowClear:false,readOnly:true,minDate:'#F{$dp.$D(\'startTime\')}'})"/>
					</td>
				</tr>
			  </table>
		</div>
		<table style="width:100%">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle">用户操作日志</td>
				<td class="tbHeadBg" style="width: 5%;"><div class="tbHeadMid"></div></td>
				<td class="tbHeadBg" style="width: 79%;">
					 <div>
					    <span style="float: right;">     
			            	<!--  <input type="button" class="btn" id="printBtn" value="打印"/> -->
			            	 <input type="button" class="btn" id="expBtn" value="导出" onclick="exportexcel('${ctx}/sys/log/exportExcel.ac','tabList');"/>     
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
				<td width="10%">用户名称</td>
				<td width="16%">单位名称</td>
				<td width="12%">部门名称</td>
				<td width="44%">操作内容</td>
				<td width="13%">操作时间</td>
			</tr>
		</thead>
		<tbody>
	  	  <c:forEach items="${page.list}" var="obj" varStatus="status"> 
			<tr>
				<td>${page.size*page.current+status.index+1}</td>
				<td>${obj.sysUser.userName}</td>
				<td>${obj.sysOrganization.organizationName}</td>
				<td>${obj.sysDept.deptname}</td> 
				<td>${obj.operationContent}</td>
				<td><fmt:formatDate value="${obj.operationDatetime}" pattern="yyyy-MM-dd HH:mm"/></td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="6" class="line2" style="text-align: right;">
				<%@include file="../../changePage.jsp" %>
			</td>
		</tr>
		</tbody>
	</table>
 </form>
</body>
</html>
