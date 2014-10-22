<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>短信模板信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="${ctx}/resources/js/sys/sys.js" type="text/javascript"></script>
</head>
<body>
<script type="text/javascript">
//<!--
	$(document).ready(function() {
		showMenu(1,13);
		
	    if("${not empty message}" == "true"){
			mac.alert(Url.decode("${message}"));
			//alert(Url.decode("${message}"))
		}

		$("#queryBtn").click(function(){
			$("#currentPage").val("0");
			$("form")[0].submit();
		});
		
		$("#addBtn").click(function(){
			window.location = "${ctx}/sys/dxmb/edit.ac";
		});
		
		//勾选复选框，进行删除操作
		checkAllBox();
		delBtnClick("${ctx}/sys/dxmb/delete.ac?id=");
	});
//-->
</script>

	<form action="${ctx}/sys/dxmb/list.ac" method="post">
		<table style="width:100%">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle">短信模板信息</td>
				<td class="tbHeadBg" style="width: 5%;"><div class="tbHeadMid"></div></td>
				<td class="tbHeadBg" style="width: 79%;">
					 <div>
					 
					        短信模板名称：
					   <input type="text" class="text" name="sysParams.mbmc" value="${sysParams.mbmc}" />  
					   <c:if test="${objectMap['XMSB_DX_DXMB_EDIT']}">
					   	<input type="button" class="btn" id="queryBtn" value="查询" />
					   </c:if>
					        
					    <span style="float: right;">     
					    	<c:if test="${objectMap['XMSB_DX_DXMB_EDIT']}">
			            	 <input type="button" class="btn" id="addBtn" value="新增"/>
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
				<!-- <td width="20%">数据查看等级</td> -->
				<td width="30%">短信模板名称</td>
				<td width="45%">短信模板内容</td>
				<td width="15%">操作</td>
			</tr>
		</thead>
		<tbody>
	  	  <c:forEach items="${page.list}" var="obj" varStatus="status"> 
			<tr>
				<td>
					<input type="checkbox" value="${obj.dxmbId}" class="subcheck"/>
				</td>
				<td>${page.size*page.current+status.index+1}</td>
				<td>${obj.mbmc}</td>
				<td>${obj.mb}</td>
				<td>
					<c:if test="${objectMap['XMSB_DX_DXMB_EDIT']}">
					<a href="${ctx}/sys/dxmb/edit.ac?id=${obj.dxmbId}">编辑</a>
					&nbsp;&nbsp;|&nbsp;&nbsp;
					<a href="javascript:deleteAction('${ctx}/sys/dxmb/delete.ac?id=${obj.dxmbId}')">删除</a>
					</c:if>
				</td>
			</tr>
		</c:forEach>
		<tr>
			<td colspan="5" class="line2" style="text-align: right;">
				<%@include file="../../changePage.jsp" %>
			</td>
		</tr>
		</tbody>
	</table>
 </form>
</body>
</html>
