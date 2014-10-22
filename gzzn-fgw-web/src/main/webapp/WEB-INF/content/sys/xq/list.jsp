<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>辖区信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="${ctx}/resources/js/sys/sys.js" type="text/javascript"></script>
</head>
<body>
<script type="text/javascript">
//<!--
	$(document).ready(function() {
		showMenu(6,65);
		//$("#dataViewId").val("${sysParams.dataViewId}");
		
	    if("${not empty message}" == "true"){
			mac.alert(Url.decode("${message}"));
			//alert(Url.decode("${message}"))
		}

		$("#queryBtn").click(function(){
			$("#currentPage").val("0");
			$("form")[0].submit();
		});
		
		$("#addBtn").click(function(){
			window.location = "${ctx}/sys/xq/edit.ac";
		});
		
		//勾选复选框，进行删除操作
		checkAllBox();
		delBtnClick("${ctx}/sys/xq/delete.ac?id=");
	});
//-->
</script>

	<form action="${ctx}/sys/xq/list.ac" method="post">
		<table style="width:100%">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle">辖区信息</td>
				<td class="tbHeadBg" style="width: 5%;"><div class="tbHeadMid"></div></td>
				<td class="tbHeadBg" style="width: 79%;">
					 <div>
					        辖区名称：
					   <input type="text" class="text" name="xqmc" value="${xqmc}" />&nbsp;&nbsp;  
					         行政区域代码：
					   <input type="text" class="text" name="xzqydm" value="${xzqydm}" />&nbsp;&nbsp;  
					   <c:if test="${objectMap['XMSB_XT_XQGL_VIEW']}">
					   <input type="button" class="btn" id="queryBtn" value="查询" />
					   </c:if>
					        
					    <span style="float: right;">     
					    	<c:if test="${objectMap['XMSB_XT_XQGL_ADD']}">
			            	 <input type="button" class="btn" id="addBtn" value="新增"/>
			            	 </c:if>
			            	 <c:if test="${objectMap['XMSB_XT_XQGL_DEL']}">
			            	 <input type="button" class="btn" id="delBtn" value="批量删除"/>     
			            	 </c:if>
		               	</span>     
		             </div>
				</td>
				<td class="tbHeadBg" style="width: 3.5%;">
					<div class="tbHeadRight"></div>
				</td>
			</tr>
		</table>
		
	<table width="100%" border="0" cellpadding="1" cellspacing="0" bordercolor="AEC2D5" class="list">
		<thead>
			<tr>
				<td width="5%"><input type="checkbox" id="checkAll" /></td>
				<td width="5%">序号</td>
				<td width="20%">辖区名称</td>
				<td width="15%">行政区域代码</td>
				<td width="20%">辖区详细名称</td>
				<td width="20%">上级辖区</td>
				<td width="15%">操作</td>
			</tr>
		</thead>
		<tbody>
	  	  <c:forEach items="${page.list}" var="obj" varStatus="status"> 
			<tr>
				<td>
					<input type="checkbox" value="${obj.xqId}" class="subcheck"/>
				</td>
				<td>${page.size*page.current+status.index+1}</td>
				<td>${obj.xqmc}</td>
				<td>${obj.xzqydm}</td>
				<td>${obj.xqxxmc}</td>
				<td>${obj.sjxq.xqmc}</td>
				<td>
					<c:if test="${objectMap['XMSB_XT_XQGL_MOD']}">
					<a href="${ctx}/sys/xq/edit.ac?id=${obj.xqId}">编辑</a>
					</c:if>
					&nbsp;&nbsp;|&nbsp;&nbsp;
					<c:if test="${objectMap['XMSB_XT_XQGL_DEL']}">
					<a href="javascript:deleteAction('${ctx}/sys/xq/delete.ac?id=${obj.xqId}')">删除</a>
					</c:if>
				</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="7" class="line2" style="text-align: right;">
				<%@include file="../../changePage.jsp" %>
			</td>
		</tr>
		</tbody>
	</table>
 </form>
</body>
</html>
