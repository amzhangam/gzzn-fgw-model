<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<html>
<head>
<title>角色信息</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<script src="${ctx}/resources/js/sys/sys.js" type="text/javascript"></script>
</head>
<body>
<script type="text/javascript">
//<!--
	$(document).ready(function() {
		showMenu(6,65);
		
	    if("${not empty message}" == "true"){
			mac.alert(Url.decode("${message}"));
			//alert(Url.decode("${message}"))
		}

		$("#queryBtn").click(function(){
			$("#currentPage").val("0");
			$("form")[0].submit();
		});
		
		$("#addBtn").click(function(){
			window.location = "${ctx}/sys/role/edit.ac";
		});
		
		//勾选复选框，进行删除操作
		checkAllBox();
		delBtnClick("${ctx}/sys/role/delete.ac?id=");
	});
//-->
</script>

	<form action="${ctx}/sys/role/list.ac" method="post">
		<table style="width:100%">
			<tr>
				<td class="tbHeadLeft"></td>
				<td class="tbHeadTitle" id="headTitle">角色信息</td>
				<td class="tbHeadBg" style="width: 5%;"><div class="tbHeadMid"></div></td>
				<td class="tbHeadBg" style="width: 79%;">
					 <div>
					 
					        角色名称：
					   <input type="text" class="text" name="sysParams.roleName" value="${sysParams.roleName}" />  
					   <c:if test="${objectMap['XMSB_XT_JSGL_VIEW']}">
					   	<input type="button" class="btn" id="queryBtn" value="查询" />
					   </c:if>
					        
					    <span style="float: right;">     
					    	<c:if test="${objectMap['XMSB_XT_JSGL_ADD']}">
			            	 <input type="button" class="btn" id="addBtn" value="新增"/>
			            	</c:if>
			            	<c:if test="${objectMap['XMSB_XT_JSGL_DEL']}">
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
				<td width="30%">角色名称</td>
				<td width="45%">角色描述</td>
				<td width="15%">操作</td>
			</tr>
		</thead>
		<tbody>
	  	  <c:forEach items="${page.list}" var="obj" varStatus="status"> 
			<tr>
				<td>
					<input type="checkbox" value="${obj.roleId}" class="subcheck"/>
				</td>
				<td>${page.size*page.current+status.index+1}</td>
				<td>${obj.roleName}</td>
				<td>${obj.roleDesc}</td>
				<td>
					<c:if test="${objectMap['XMSB_XT_JSGL_MOD']}">
					<a href="${ctx}/sys/role/edit.ac?id=${obj.roleId}">编辑</a>
					</c:if>
					&nbsp;&nbsp;|&nbsp;&nbsp;
					<c:if test="${objectMap['XMSB_XT_JSGL_DEL']}">
					<a href="javascript:deleteAction('${ctx}/sys/role/delete.ac?id=${obj.roleId}')">删除</a>
					</c:if>
					&nbsp;&nbsp;|&nbsp;&nbsp;
					<c:if test="${objectMap['XMSB_XT_JSGL_ACCREDIT']}">
					<a href="${ctx}/sys/role/rolePermission.ac?id=${obj.roleId}">授权</a>
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
